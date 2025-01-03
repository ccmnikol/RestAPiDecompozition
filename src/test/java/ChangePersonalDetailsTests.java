import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Tag("demowebshop")

public class ChangePersonalDetailsTests {


    @Test
    void changeCustomerInfoTest() {

        // Значения токенов
        String cookieToken = "2to3JAFTltPcOs-W86GEO4rK_t2EmFJYBCw1Z3i7oZaZyiSapwbnb5hsIeM-YZAUMcVlePgPjMo7-e8sd2gEkcbtbJIRAOU-Z3TkUUqclU41;";
        String formToken = "DgcNRcuLzTM4EbDawQotP9VSPgk5TByoDhgWpkQkyvxUQvPdH_glnyPYO3TllQp7kHAQRooWY4YNHcL6lHJXNYogQNkdpbLp-dSFB7ZpZbUhVBwqJ7ZO8BQaNZw7Sj-T0";

        // Формируем запрос
        given()
                .header("Content-Type", "application/x-www-form-urlencoded") // Установка заголовка
                .cookie("__RequestVerificationToken=", cookieToken) // Установка куки с токеном
                .formParam("__RequestVerificationToken", formToken) // Параметр формы с другим токеном
                .formParam("Gender", "F") // Параметр формы
                .formParam("FirstName", "valeroni") // Параметр формы
                .formParam("LastName", "oliroli") // Параметр формы
                .formParam("Email", "y45erer@list.ru") // Параметр формы
                .when()
                .post("https://demowebshop.tricentis.com/customer/info") // Выполнение POST запроса
                .then()
                .log().all() // Логирование для отладки
                .statusCode(302); // Ожидаемый статус ответа
    }
}
