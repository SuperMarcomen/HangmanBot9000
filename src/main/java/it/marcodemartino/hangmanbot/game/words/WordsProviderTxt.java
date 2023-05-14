package it.marcodemartino.hangmanbot.game.words;

import it.marcodemartino.hangmanbot.game.collections.RandomArrayList;
import it.marcodemartino.hangmanbot.game.collections.RandomHashMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordsProviderTxt extends WordsProvider {

    private static final String WORDS_FOLDER = "words";
    private static final String DIR_SEPARATOR = System.getProperty("file.separator");;
    private final Map<Locale, Words> wordsMap;

    public WordsProviderTxt() {
        wordsMap = new RandomHashMap<>();
        loadAllWords();
    }

    @Override
    public void loadAllWords() {
        List<Path> txtFiles = getAllTxtFiles();
        for (Path txtFile : txtFiles) {
            String pathString = txtFile.toString();
            String[] arguments = pathString.split(DIR_SEPARATOR + DIR_SEPARATOR);
            Locale locale = Locale.forLanguageTag(arguments[1]);
            String category = arguments[2].replace(".txt", "");

            loadWordsForLocale(txtFile, locale, category);
        }
    }

    private void loadWordsForLocale(Path txtFile, Locale locale, String category) {
        Words words = wordsMap.get(locale);
        if (words == null) {
            words = new Words();
        }

        RandomArrayList<String> lines;
        try {
            lines = new RandomArrayList<>(Files.readAllLines(txtFile, Charset.defaultCharset()));
        } catch (IOException e) {
            System.err.println("An error occurred while reading the words!");
            return;
        }

        words.addWords(category, lines);
        wordsMap.put(locale, words);
    }

    private List<Path> getAllTxtFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(WORDS_FOLDER))) {
            return paths
                    .filter(file -> !Files.isDirectory(file))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Set<String> getCategoriesFromLocale(Locale locale) {
        return wordsMap.get(locale).getCategories();
    }

    // here only for testing purposes
    public Map<Locale, Words> getWordsMap() {
        return wordsMap;
    }
}
