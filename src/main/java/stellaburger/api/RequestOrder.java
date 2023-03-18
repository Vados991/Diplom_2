package stellaburger.api;

import constant.Constants;
import io.restassured.response.Response;
import org.example.Order;

import static io.restassured.RestAssured.given;

public class RequestOrder {
    public static Response getUserOrders(String authToken) {
        return given()
                .header("Authorization", authToken)
                .get(Constants.ORDERS_BUR);
    }

    public static Response createOrder(Order order, String authToken) {
        return given()
                .header("Authorization", authToken)
                .header("Content-Type", "application/json")
                .body(order)
                .post(Constants.ORDERS_BUR);
    }

}
