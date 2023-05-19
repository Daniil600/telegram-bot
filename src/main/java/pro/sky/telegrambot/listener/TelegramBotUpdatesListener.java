package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    @Autowired
    private TelegramBot telegramBot;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            logger.info("Processing update: {}", update);
            if (update.message().text().equals("/start")) {
                sendMethodOfStart(update);
            }
            else {
                defaultMethod(update);
            }

        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    private void sendMethodOfStart(Update update) {
        logger.info("start of sendMethodOfStart : {}", update);
        TelegramBot bot = new TelegramBot(telegramBot.getToken());
        long chatId = update.message().chat().id();


        String messageText = "Hi, " + "@" + update.message().chat().username() + " nice to meet you";

        SendMessage message = new SendMessage(chatId, messageText);
        SendResponse response = bot.execute(message);
    }

    private void defaultMethod(Update update) {
        logger.info("start of defaultMethod : {}", update);
        TelegramBot bot = new TelegramBot(telegramBot.getToken());
        long chatId = update.message().chat().id();


        String messageText = "Sorry, command is not working now \uD83D\uDE14";

        SendMessage message = new SendMessage(chatId, messageText);
        SendResponse response = bot.execute(message);
    }
}

