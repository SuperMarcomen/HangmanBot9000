package it.marcodemartino.hangmanbot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Starts the long pooling bot.
 */
@Component
public class HangmanBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
  private final TelegramClient telegramClient;

  /**
   * Initializes the hangman bot and everything needed for it.
   */
  public HangmanBot() {
    telegramClient = new OkHttpTelegramClient(getBotToken());
  }

  @Override
  public String getBotToken() {
    return Dotenv.load().get("BOT_TOKEN");
  }

  @Override
  public LongPollingUpdateConsumer getUpdatesConsumer() {
    return this;
  }

  @Override
  public void consume(Update update) {
    // We check if the update has a message and the message has text
    if (update.hasMessage() && update.getMessage().hasText()) {
      // Set variables
      String messageText = update.getMessage().getText();
      long chatId = update.getMessage().getChatId();

      SendMessage message = SendMessage // Create a message object
          .builder()
          .chatId(chatId)
          .text(messageText)
          .build();
      try {
        telegramClient.execute(message); // Sending our message object to user
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }
}
