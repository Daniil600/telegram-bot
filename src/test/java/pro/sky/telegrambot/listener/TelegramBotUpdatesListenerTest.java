package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import io.restassured.RestAssured;
import org.apache.tomcat.jni.User;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegrambot.TelegramBotApplication;
import pro.sky.telegrambot.repository.NotificationRepository;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

@SpringBootTest
@PropertySource("application.properties")
public class TelegramBotUpdatesListenerTest {
    @Test
    void succesSendMessage() throws Exception {

        RestAssured.baseURI = "https://api.telegram.org/bot6093682784:AAEIwP6AW5RoW47VKjqBkk1gKHV8NlFhrdw";
        given()
                .param("text", "rest-assured_TEST")
                .param("chat_id", "362396673")
                .when()
                .get("/sendMessage")
                .then()
                .statusCode(200);
    }
}
