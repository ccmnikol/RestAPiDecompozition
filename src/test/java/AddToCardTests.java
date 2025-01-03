import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@Tag("demowebshop")
public class AddToCardTests {


    @BeforeAll
public static void setUp(){
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }

    @Test
    void addToCartTest1(){

        String cookieValue = "40BEB709DD6A2627DF57298EDA35019A7FF7E9592CB795F668B7CF5B02" +
                "BDC901794EEE95F6C92997A48F6A3B3D6BB21CE83A4C1C58DA2E1AAC36738A5B186391D3" +
                "D632A839AEAEF6A848F7077F375AA10AA913146551F584CB84B61E76D8CB552E715EA8933" +
                "36DCE14893AA7842640D82F36405694AC88D329C887F902D0377494C977ABC2798CA8900EBA3FCF693BBF;",
                body = "product_attribute_72_5_18=53" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&addtocart_72.EnteredQuantity=3";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH=", cookieValue)
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    @Test
    void addToCartAnonymTest(){

        String body = "product_attribute_72_5_18=53" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&addtocart_72.EnteredQuantity=3";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(3)"));
    }
}
