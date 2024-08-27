package it.marcodemartino.hangmanbot.telegram.commands;

import io.github.ageofwar.telejam.Bot;
import io.github.ageofwar.telejam.commands.Command;
import io.github.ageofwar.telejam.commands.CommandHandler;
import io.github.ageofwar.telejam.messages.TextMessage;
import io.github.ageofwar.telejam.methods.SendMessage;
import io.github.ageofwar.telejam.text.Text;

public class StartCommand implements CommandHandler {

  private final Bot bot;

  public StartCommand(Bot bot) {
    this.bot = bot;
  }

  @Override
  public void onCommand(Command command, TextMessage textMessage) throws Throwable {
    SendMessage sendMessage = new SendMessage()
        .text(new Text("🇮🇹 Scrivi @HangmanBot nella chat per giocare!" + System.lineSeparator() +
            "🇺🇸 Write @HangmanBot in the chat to start playing!"))
        .chat(textMessage.getChat());
    bot.execute(sendMessage);
  }
}
