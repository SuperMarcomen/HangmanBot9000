package it.marcodemartino.hangmanbot.entities.match;

/**
 * States what the result of a letter guess is.
 */
public enum GuessResult {

  LETTER_ALREADY_GUESSED,
  LETTER_RIGHT,
  LETTER_WRONG,
  MATCH_PERFECTLY_WON,
  MATCH_WON,
  MATCH_LOST,
}
