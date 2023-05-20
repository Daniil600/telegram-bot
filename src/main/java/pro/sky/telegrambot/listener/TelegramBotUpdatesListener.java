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
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.repository.NotificationRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Этот класс отвечает за принятие собщений от пользователей и сохранение их в БД
 * */


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private NotificationRepository notificationRepository;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            //проверяем, что отправил пользователь
            if (update.message().text().equals("/start")) {
                sendMethodOfStart(update);
            } else {
                saveMethodOfNotification(update);
            }

        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //Метод приветствует пользователя при отправке "/start и рассказывает о возможностях"
    private void sendMethodOfStart(Update update) {
        logger.info("start of sendMethodOfStart : {}", update);
        TelegramBot bot = new TelegramBot(telegramBot.getToken());
        long chatId = update.message().chat().id();


        String messageText = "Привет, " + "@" + update.message().chat().username() + " рад тебя видеть! " + "\n" +
                "Я напоминалка, запиши в таком формате сообщение <01.01.2022 20:00 Сделать домашнюю работу> и я напомню тебе о ней";

        SendMessage message = new SendMessage(chatId, messageText);
        SendResponse response = bot.execute(message);
    }

    //Метод выполняет сохраниние в БД
    private void saveMethodOfNotification(Update update) {
        logger.info("start of sendMethodOfStart : {}", update);
        TelegramBot bot = new TelegramBot(telegramBot.getToken());
        long chatId = update.message().chat().id();
        String getMessage = update.message().text();
        String sendMessage;


        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
        LocalDateTime localDateTime;

        //проверить, удовлетворяет ли строка паттерну
        Matcher matcher = pattern.matcher(getMessage);
        if (matcher.matches()) {
            //date для оповещения
            String date = matcher.group(1);
            //item это текст для оповещения
            String item = matcher.group(3);

            localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            //Поверяем что дата оповещения идёт после сегоднешней даты
            if (LocalDateTime.now().isAfter(localDateTime)) {
                String messageText = "Ты наверное ошибся с датой, сейчас: " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                        ". А ты хочешь записать в " + localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                SendMessage message = new SendMessage(chatId, messageText);
                SendResponse response = bot.execute(message);
                return;
            }
            //Создаём уведомления для сохранения в БД
            Notification notification = new Notification();
            notification.setId(chatId);
            notification.setNotification(item);
            notification.setLocalDateTime(localDateTime);

            notificationRepository.save(notification);

            //Текст ответ пользоватею что всё ОК
            String messageText = "Всё супер! Я записал. Чуть позже напомню о твоей задаче";
            SendMessage message = new SendMessage(chatId, messageText);
            SendResponse response = bot.execute(message);
        } else {
            //Если что то пшло не так, то сообщим об эом пользователю
            defaultMethod(update);
        }
    }

    public void sendNotification(Notification notification) {
        logger.info("start of defaultMethod : {}", notification);
        TelegramBot bot = new TelegramBot(telegramBot.getToken());

        SendMessage message = new SendMessage(notification.getId(), notification.getNotification());
        SendResponse response = bot.execute(message);
        notificationRepository.delete(notification);
    }

    //Метод для отправки сообщения если что то не так
    private void defaultMethod(Update update) {
        logger.info("start of defaultMethod : {}", update);
        TelegramBot bot = new TelegramBot(telegramBot.getToken());
        long chatId = update.message().chat().id();

        String messageText = "Прости, но что то записано не так \uD83D\uDE14";
        SendMessage message = new SendMessage(chatId, messageText);
        SendResponse response = bot.execute(message);
    }
}

