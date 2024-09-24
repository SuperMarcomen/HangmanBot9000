package it.marcodemartino.hangmanbot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
  private int rightLetters;

  @PositiveOrZero
  private int wrongLetters;

  @OneToOne
  @MapsId
  @JoinColumn(name = "userId")
  private UserIdentity user;
}
