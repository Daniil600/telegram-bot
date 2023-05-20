# telegram-bot
Этого бота можно использовать для функционирования Телеграм-бота с рассылкой уведомлений

Для начала его работы заполните в application.properties следующие поля:
@spring.datasource.url      - URL к БД(Базе данных)
@spring.datasource.username - Имя владельца БД 
@spring.datasource.password - Пароль владельца БД
@telegram.bot.token         - Telegram token полученный от https://t.me/BotFather
@telegram.bot.name          - Имя Telegram бота (пример: wither_for_you_bot)

В src/main/java/pro/sky/telegrambot/listener/TelegramBotUpdatesListener.java Вы можете реализовать дополнительную логику вашего бота.
Все методы маленькими комментариями описывают его предназначение
