package it.marcodemartino.hangmanbot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Represents the instance of a running match to be stored in the db
 * to recover it in case of crash.
 */
@Entity
@IdClass(RunningMatchId.class)
@Accessors(fluent = true)
@Getter
@Setter
public class RunningMatch {

  @Id
  private long chatId;

  @Id
  private long messageId;

  @NotBlank
  private String word;

  @NotBlank
  private String category;

  @NotNull
  private char[] guessedLetters;

  @Positive
  private int lives;

  @NotNull
  private Locale locale;

  @ManyToOne
  @NotNull
  private UserIdentity owner;

  /**
   * Computes the current state of the word based on the
   * guessed letters.
   *
   * @return the current state of the word
   */
  public String getWordState() {
    char[] wordLetters = new char[word.length()];
    char[] wordArray = word.toCharArray();
    for (int i = 0; i < wordArray.length; i++) {
      if (isLetterGuessed(wordArray[i])) {
        wordLetters[i] = wordArray[i];
      } else {
        wordLetters[i] = '-';
      }
    }
    return new String(wordLetters);
  }

  /**
   * Adds a letter to the guessed one by expanding the array.
   *
   * @param letter the letter to be added
   */
  public void addGuessedLetter(char letter) {
    char[] newLetters = new char[guessedLetters.length + 1];
    System.arraycopy(guessedLetters, 0, newLetters, 0, guessedLetters.length);
    newLetters[newLetters.length - 1] = letter;
    guessedLetters = newLetters;
  }

  /**
   * Checks whether the match is won by checking if all letters of the word have been guessed.
   *
   * @return whether the match is won
   */
  public boolean isMatchWon() {
    for (char letter : word.toCharArray()) {
      if (!isLetterGuessed(letter)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks whether a letter is already guessed.
   *
   * @param letter the letter to be checked
   * @return whether the letter is already guessed
   */
  public boolean isLetterGuessed(char letter) {
    for (char guessedLetter : guessedLetters) {
      if (letter == guessedLetter) {
        return true;
      }
    }
    return false;
  }

}
