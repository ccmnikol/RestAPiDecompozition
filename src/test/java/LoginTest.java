import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

@Tag("demowebshop")
public class LoginTest {

    static String login = "y45erer@list.ru";
    static String password = "1111aa";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
        Configuration.baseUrl = RestAssured.baseURI; // Устанавливаем baseUrl для Selenide
    }

    @Test
    void loginWithCookieTest() {
        step("Get cookie by API and set it to browser", () -> {
            String authorizationCookie =
                    given()
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", login)
                            .formParam("Password", password)
                            .when()
                            .post("/login")
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");

            if (authorizationCookie == null) {
                throw new RuntimeException("Failed to retrieve authorization cookie");
            }

            step("Open minimal content to set the cookie", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Set cookie to the browser", () ->
                    getWebDriver().manage().addCookie(
                            new Cookie("NOPCOMMERCE.AUTH", authorizationCookie)));
        });

        step("Open main page", () ->
                open("/"));

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }
}
