package it.marcodemartino.hangmanbot.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents basic identifying data for the users used to show their
 * names on the leaderboard.
 */
@Entity
@Accessors(fluent = true)
@Getter
@Setter
public class UserIdentity {

  @Id
  private long userId;

  private String username;

  @Size(min = 1)
  @Column(nullable = false)
  @NotBlank
  private String firstName;

  private String lastName;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @NotNull
  private UserLanguage language;
}
