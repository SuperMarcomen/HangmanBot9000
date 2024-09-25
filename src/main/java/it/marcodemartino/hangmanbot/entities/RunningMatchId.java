package it.marcodemartino.hangmanbot.entities;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The composite primary key of the running game, consisting of
 * chat id and message id, which uniquely identify a match.
 */
@Accessors(fluent = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RunningMatchId implements Serializable {

  @NotNull
  private long chatId;

  @NotNull
  private long messageId;

}
