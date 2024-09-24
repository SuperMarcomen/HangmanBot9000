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

@Entity
@IdClass(RunningGameId.class)
@Accessors(fluent = true)
@Getter
@Setter
public class RunningGame {

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

}
