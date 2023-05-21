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
    private Logger logger = LoggerFactory.getLogger(TelegramBotApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }


}
