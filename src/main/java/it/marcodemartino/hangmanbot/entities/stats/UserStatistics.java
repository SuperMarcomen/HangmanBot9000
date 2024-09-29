package it.marcodemartino.hangmanbot.entities.stats;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Stores user statistics to show them on the leaderboard.
 */
@Entity
@NoArgsConstructor
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

  public UserStatistics(UserIdentity user) {
    this.user = user;
    this.userId = user.userId();
  }

  public UserStatistics increaseStartedMatches() {
    startedMatches++;
    return this;
  }

  public UserStatistics increaseWonMatches() {
    wonMatches++;
    return this;
  }

  public UserStatistics increasePerfectMatches() {
    perfectMatches++;
    return this;
  }

  public UserStatistics increaseRightLetters() {
    rightLetters++;
    return this;
  }

  public UserStatistics increaseWrongLetters() {
    wrongLetters++;
    return this;
  }
}
