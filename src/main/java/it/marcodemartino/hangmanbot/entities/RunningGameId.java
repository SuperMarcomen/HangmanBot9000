package it.marcodemartino.hangmanbot.entities;

import jakarta.validation.constraints.NotNull;
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
public class RunningGameId implements Serializable {

  @NotNull
  private long chatId;
  @NotNull
  private long messageId;

}
