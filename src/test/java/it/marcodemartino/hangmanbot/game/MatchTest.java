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
        match.guessLetter('a');
        match.guessLetter('c');
        match.guessLetter('d');
        match.guessLetter('f');
        match.guessLetter('g');
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

    @Test
    void testAccents() {
        Match match = new Match("Äpfel", "fruit");
        match.guessLetter('ä');
        assertEquals("Ä----", match.getCurrentStatusWord());
    }

    @Test
    void testSpaces() {
        Match match = new Match("balena grigia", "animal");
        match.guessLetter('a');
        assertEquals("-a---a -----a", match.getCurrentStatusWord());
        match.guessLetter('b');
        match.guessLetter('a');
        match.guessLetter('l');
        match.guessLetter('e');
        match.guessLetter('n');
        match.guessLetter('g');
        match.guessLetter('r');
        match.guessLetter('i');
        assertEquals("balena grigia", match.getCurrentStatusWord());
        assertTrue(match.isMatchEnded());
    }

    @Test
    void testNonAlphabeticChar() {
        Match match = new Match("HQ9+", "esolang");
        match.guessLetter('h');
        assertEquals("H-9+", match.getCurrentStatusWord());
    }
}