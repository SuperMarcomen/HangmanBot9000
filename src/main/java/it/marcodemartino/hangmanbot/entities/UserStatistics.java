package it.marcodemartino.hangmanbot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Stores user statistics to show them on the leaderboard.
 */
@Entity
@Accessors(fluent = true)
@Getter
@Setter
public class UserStatistics {

  @Id
  private long userId;

  @PositiveOrZero
  private int startedMatches;

  @PositiveOrZero
  private int wonMatches;

  @PositiveOrZero
  private int perfectMatches;

  @PositiveOrZero
  private int rightLetters;

  @PositiveOrZero
  private int wrongLetters;

  @OneToOne
  @MapsId
  @JoinColumn(name = "userId")
  @NotNull
  private UserIdentity user;

}
