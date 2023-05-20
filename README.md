# telegram-bot
## Этого бота можно использовать для функционирования Телеграм-бота с рассылкой уведомлений

Для начала его работы заполните в application.properties следующие поля:<br>
<b>spring.datasource.url      - URL к БД(Базе данных)<br>
spring.datasource.username - Имя владельца БД <br>
spring.datasource.password - Пароль владельца БД<br>
telegram.bot.token         - Telegram token полученный от https://t.me/BotFather <br>
telegram.bot.name          - Имя Telegram бота (пример: wither_for_you_bot) <br></b>

В src/main/java/pro/sky/telegrambot/listener/TelegramBotUpdatesListener.java Вы можете реализовать дополнительную логику вашего бота.
Все методы маленькими комментариями описывают его предназначение
