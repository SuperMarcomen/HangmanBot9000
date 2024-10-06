package it.marcodemartino.hangmanbot.services;

import it.marcodemartino.hangmanbot.repositories.FileRepository;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Offers the capability to load words from files and pick random words
 * from different categories.
 */
@Service
public class WordsService {

  private static final String WORDS_FOLDER = "words";
  private final Map<Locale, Map<String, List<String>>> localeToCategoriesToWords;
  private final Map<String, List<String>> sharedCategoriesToWords;
  private final FileRepository fileRepository;
  private final Random random;
  private final Logger logger = LogManager.getLogger(WordsService.class);

  /**
   * Initializes the needed constants.
   *
   * @param fileRepository the repository to read files
   */
  @Autowired
  public WordsService(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
    localeToCategoriesToWords = new HashMap<>();
    sharedCategoriesToWords = new HashMap<>();
    random = new Random();
  }

  /**
   * Loads the words from disk and puts them in the maps by their categories.
   */
  @PostConstruct
  public void loadWords() {
    Path parent = Path.of(WORDS_FOLDER);
    try (Stream<Path> elementsInDir = Files.list(parent)) {
      elementsInDir.forEach(child -> {
        if (child.equals(parent)) {
          return;
        }
        if (Files.isRegularFile(child)) {
          readWords(child, sharedCategoriesToWords);
        } else if (Files.isDirectory(child)) {
          exploreLanguageFolder(child);
        }
      });
    } catch (IOException e) {
      logger.error("Could not explore the folder: {}", WORDS_FOLDER, e);
    }
  }

  /**
   * Deletes the already loaded words and loads them again.
   */
  public void reloadWords() {
    localeToCategoriesToWords.clear();
    sharedCategoriesToWords.clear();
    loadWords();
  }

  /**
   * Gets a random word from this category.
   *
   * @param category the category from which the random word will come from
   * @return the random word
   */
  public String getWordFrom(String category) {
    return getRandomWord(
        sharedCategoriesToWords
            .getOrDefault(category, Collections.emptyList())
    );
  }

  /**
   * Gets a random word from the category of this language.
   *
   * @param locale   the language from which the category comes from
   * @param category the category from which the random word will come from
   * @return the random word
   */
  public String getWordFrom(Locale locale, String category) {
    return getRandomWord(
        localeToCategoriesToWords
            .getOrDefault(locale, Collections.emptyMap())
            .getOrDefault(category, Collections.emptyList())
    );
  }

  private void exploreLanguageFolder(Path folder) {
    Locale locale = Locale.forLanguageTag(folder.getFileName().toString());
    try (Stream<Path> files = Files.list(folder)) {
      files.forEach(wordFile -> {
        if (wordFile.equals(folder)) {
          return;
        }
        if (!Files.isRegularFile(wordFile)) {
          return;
        }
        Map<String, List<String>> wordsMap =
            localeToCategoriesToWords.computeIfAbsent(locale, k -> new HashMap<>());
        readWords(wordFile, wordsMap);
      });
    } catch (IOException e) {
      logger.error("Could not explore the folder: {}", folder.getFileName(), e);
    }
  }

  private void readWords(Path filePath, Map<String, List<String>> wordsMap) {
    String categoryName = filePath.getFileName().toString().split("\\.")[0];
    String[] words = fileRepository
        .readFile(filePath)
        .split(System.lineSeparator());

    List<String> trimmedWords = Arrays
        .stream(words)
        .map(String::trim)
        .toList();

    wordsMap.put(categoryName, trimmedWords);
  }

  private String getRandomWord(List<String> words) {
    int randomIndex = words.size() > 1
        ? random.nextInt(words.size())
        : 0;
    return words.get(randomIndex);
  }
}
