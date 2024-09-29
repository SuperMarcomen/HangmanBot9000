package it.marcodemartino.hangmanbot.entities.stats;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMatchContributionId implements Serializable {

  private long userId;

  private long chatId;

  private long messageId;
}
