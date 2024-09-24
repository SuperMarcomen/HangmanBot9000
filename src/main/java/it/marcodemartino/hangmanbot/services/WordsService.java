package it.marcodemartino.hangmanbot.services;

import it.marcodemartino.hangmanbot.repositories.FileRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordsService {

  private final Map<Locale, Map<String, List<String>>> localeToCategoriesToWords;
  private final Map<String, List<String>> sharedCategoriesToWords;
  private final Random random;
  private final FileRepository fileRepository;

  @Autowired
  public WordsService(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
    localeToCategoriesToWords = new HashMap<>();
    sharedCategoriesToWords = new HashMap<>();
    random = new Random();
  }

  public void loadWords() {

  }

  public void reloadWords() {
    localeToCategoriesToWords.clear();
    sharedCategoriesToWords.clear();
    loadWords();
  }

  public String getWordFrom(String category) {
    return getRandomWord(
        sharedCategoriesToWords
            .getOrDefault(category, Collections.emptyList())
    );
  }

  public String getWordFrom(Locale locale, String category) {
    return getRandomWord(
        localeToCategoriesToWords
            .getOrDefault(locale, Collections.emptyMap())
            .getOrDefault(category, Collections.emptyList())
    );
  }

  private String getRandomWord(List<String> words) {
    return words.get(random.nextInt(words.size()));
  }
}
