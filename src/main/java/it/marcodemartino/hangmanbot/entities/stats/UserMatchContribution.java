package it.marcodemartino.hangmanbot.entities.stats;

import it.marcodemartino.hangmanbot.entities.UserIdentity;
import it.marcodemartino.hangmanbot.entities.match.RunningMatch;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@IdClass(UserMatchContributionId.class)
@NoArgsConstructor
@Accessors(fluent = true)
@Getter
@Setter
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

  public UserMatchContribution(long chatId, long userId, long messageId) {
    this.messageId = messageId;
    this.chatId = chatId;
    this.userId = userId;
  }

  public UserMatchContribution increaseRightLetters() {
    rightLetters++;
    return this;
  }
}
