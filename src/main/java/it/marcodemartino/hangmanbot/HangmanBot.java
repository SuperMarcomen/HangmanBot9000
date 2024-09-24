package it.marcodemartino.hangmanbot;

import io.github.cdimascio.dotenv.Dotenv;
import it.marcodemartino.hangmanbot.entities.RunningGame;
import it.marcodemartino.hangmanbot.entities.UserIdentity;
import it.marcodemartino.hangmanbot.entities.UserLanguage;
import it.marcodemartino.hangmanbot.repositories.RunningGameRepository;
import it.marcodemartino.hangmanbot.repositories.UserIdentityRepository;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final UserIdentityRepository userIdentityRepository;
  private final RunningGameRepository runningGameRepository;

  @Autowired
  public HangmanBot(UserIdentityRepository userIdentityRepository, RunningGameRepository runningGameRepository) {
    this.userIdentityRepository = userIdentityRepository;
    this.runningGameRepository = runningGameRepository;
    telegramClient = new OkHttpTelegramClient(getBotToken());

    UserIdentity userIdentity = new UserIdentity()
        .userId(123L)
        .firstName("123");
    UserLanguage userLanguage = new UserLanguage()
        .language(Locale.ENGLISH)
        .user(userIdentity);
    userIdentityRepository.save(userIdentity);

    System.out.println(123);
    RunningGame runningGame = new RunningGame()
        .messageId(123)
        .chatId(456)
        .category("test")
        .lives(5)
        .guessedLetters(new char[] {'a'})
        .locale(Locale.ITALIAN)
        .word("ciao");
    runningGameRepository.save(runningGame);
    System.out.println(456);
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
