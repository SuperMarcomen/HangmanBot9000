package it.marcodemartino.hangmanbot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Stores the preferred language of the user for internationalization purposes.
 */
@Entity
@Accessors(fluent = true)
@Getter
@Setter
public class UserLanguage {

  @Id
  private long userId;

  @NotNull
  private Locale language;

  @OneToOne
  @MapsId
  @JoinColumn(name = "userId")
  @NotNull
  private UserIdentity user;

}
