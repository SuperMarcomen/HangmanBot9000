package it.marcodemartino.hangmanbot.services;

import it.marcodemartino.hangmanbot.repositories.FileRepository;
import org.junit.jupiter.api.Test;

class WordsServiceTest {

  private final WordsService wordsService = new WordsService(new FileRepository());

  @Test
  void loadWords() {
    // TODO improve this test
    wordsService.loadWords();
  }
}