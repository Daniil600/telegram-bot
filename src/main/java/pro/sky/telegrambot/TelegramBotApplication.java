package pro.sky.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
public class TelegramBotApplication {
    @Autowired
    NotificationRepository notificationRepository;
    private Logger logger = LoggerFactory.getLogger(TelegramBotApplication.class);

    @Autowired
    TelegramBotUpdatesListener listener;

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        ArrayList<Notification> notifications = (ArrayList<Notification>) notificationRepository.findNotification(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        if (notifications != null) {
            notifications.forEach(notification -> {
                logger.info("Processing update: {}", notification);
                if(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).isAfter(notification.getLocalDateTime())){
                    listener.sendNotification(notification);
                }
            });
        }
    }
}
