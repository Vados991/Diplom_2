package stellaburger.api;
import constant.Constants;
import io.restassured.response.Response;
import org.example.UserAuthData;

import static io.restassured.RestAssured.given;

public class RequestCreateUser {

    public static Response createUser(UserAuthData userAuthData) {
        return given()
                .header("Content-Type", "application/json")
                .body(userAuthData)
                .post(Constants.CREATE_NEW_USER);
    }

    public static Response authUser(UserAuthData userAuthData) {
        return given()
                .header("Content-Type", "application/json")
                .body(userAuthData)
                .post(Constants.LOGIN_USER);
    }

    public static Response updateUserData(UserAuthData userAuthData, String authToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", authToken)
                .body(userAuthData)
                .patch(Constants.USER_AUTH);
    }

    public static Response deleteUser(String authToken) {
        String AuthRoute = Constants.USER_AUTH;
        return given()
                .header("Authorization", authToken)
                .delete(AuthRoute);
    }
}
