package it.marcodemartino.hangmanbot.entities.stats;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import it.marcodemartino.hangmanbot.entities.match.RunningMatch;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class UserMatchContribution {

  @Id
  private long userId;

  @Id
  private long chatId;

  @Id
  private long messageId;

  @Positive
  private int rightLetters;

  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "chatId"),
      @JoinColumn(name = "messageId")
  })
  private RunningMatch runningMatch;

  @ManyToOne
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  @NotNull
  private UserIdentity user;

}
