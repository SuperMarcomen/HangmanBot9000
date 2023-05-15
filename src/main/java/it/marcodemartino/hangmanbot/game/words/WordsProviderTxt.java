package it.marcodemartino.hangmanbot.game.words;

import it.marcodemartino.hangmanbot.game.collections.RandomArrayList;
import it.marcodemartino.hangmanbot.game.collections.RandomHashMap;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordsProviderTxt extends WordsProvider {

    private static final String ALPHABET_FOLDER = "alphabet";
    private static final String DIR_SEPARATOR = System.getProperty("file.separator");;

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
            boolean alphabet = arguments[0].equals(ALPHABET_FOLDER);
            Locale locale = Locale.forLanguageTag(arguments[1]);
            String category = "";

            if (!alphabet) {
                category = arguments[2].replace(".txt", "");
            }

            loadDataForLocale(txtFile, locale, category, alphabet);
        }
    }

    private void loadDataForLocale(Path txtFile, Locale locale, String category, boolean alphabet) {
        Words words = wordsMap.get(locale);
        if (words == null) {
            words = new Words();
        }

        try {
            readAndAddLines(txtFile, words, category, alphabet);
        } catch (IOException e) {
            System.err.println("An error occurred while reading the words!");
            return;
        }

        wordsMap.put(locale, words);
    }

    private void readAndAddLines(Path txtFile, Words words, String category, boolean alphabet) throws IOException {
        if (alphabet) {
            List<String> lines = new ArrayList<>(Files.readAllLines(txtFile, Charset.defaultCharset()));
            words.addAlphabetCharacters(lines);
        } else {
            RandomArrayList<String> lines = new RandomArrayList<>(Files.readAllLines(txtFile, Charset.defaultCharset()));
            words.addWords(category, lines);
        }
    }

    private List<Path> getAllTxtFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(""))) {
            return paths
                    .filter(file -> !Files.isDirectory(file))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Character> getAlphabetFromLocale(Locale locale) {
        return wordsMap.get(locale).getAlphabet();
    }

    public Set<String> getCategoriesFromLocale(Locale locale) {
        return wordsMap.get(locale).getCategories();
    }

    // here only for testing purposes
    public Map<Locale, Words> getWordsMap() {
        return wordsMap;
    }
}
