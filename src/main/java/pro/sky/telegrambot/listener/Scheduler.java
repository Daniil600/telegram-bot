package pro.sky.telegrambot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pro.sky.telegrambot.model.Notification;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegrambot.repository.NotificationRepository;

/**
 * В этом классе реализованна проверка на необходимость уведомления пользователей, которые отправленны в формате: <01.01.2022 20:00 Сделать домашнюю работу>
 *
 * @@Scheduled - Делает проверку возможной рассылки сообщений пользователям каждую минуту
 */

@Configuration
public class Scheduler {

    private Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    TelegramBotUpdatesListener telegramBotUpdatesListener;


    @Scheduled(cron = "0 0/1 * * * *")
    private void run() {
        List<Notification> notifications = notificationRepository.findNotification(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        if (!notifications.isEmpty()) {
            notifications.forEach(notification -> {
                logger.info("Processing update: {}", notification);
                telegramBotUpdatesListener.sendNotification(notification);
            });
        }
    }
}