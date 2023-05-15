package it.marcodemartino.hangmanbot.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchTest {

    @Test
    void isLetterAlreadyGuessed() {
        Match match = new Match("hello", "vocabulary");
        assertFalse(match.isLetterAlreadyGuessed('l'));
        match.guessLetter('h');
        assertTrue(match.isLetterAlreadyGuessed('h'));
    }

    @Test
    void guessLetter() {
        Match match = new Match("bye", "vocabulary");
        assertTrue(match.guessLetter('b'));
        assertFalse(match.guessLetter('a'));
    }

    @Test
    void isMatchEndedLives() {
        Match match = new Match("bye", "vocabulary");
        for (int i = 0; i < 5; i++) {
            match.decreaseLives();
        }
        assertTrue(match.isMatchEnded());
    }

    @Test
    void isMatchEndedRightLetters() {
        Match match = new Match("bye", "vocabulary");
        match.guessLetter('b');
        match.guessLetter('y');
        match.guessLetter('e');
        assertTrue(match.isMatchEnded());
    }

    @Test
    void getCurrentStatusWord() {
        Match match = new Match("cellphone", "technology");
        match.guessLetter('l');
        match.guessLetter('o');
        assertEquals("--ll--o--", match.getCurrentStatusWord());
    }
}