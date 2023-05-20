package pro.sky.telegrambot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Этот класс необходим для записи в БД
 * Когда в классе TelegramBotUpdatesListener.java проходят необходимые условия,
 * мы создаём экземпляп Notification и сохраняем в БД
 *
 * @id - Это id чата
 *
 * @notification - Это текст напоминания
 *
 * @localDateTime - Это время в которое необходимо выполнить напоминание
 * */
@Entity
@Table(name = "notification_task")
public class Notification {
    @Id
    private Long id;
    @Column(name = "notification")
    String notification;
    @Column(name = "datetime")
    LocalDateTime localDateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) && Objects.equals(notification, that.notification) && Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, notification, localDateTime);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", notification='" + notification + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
