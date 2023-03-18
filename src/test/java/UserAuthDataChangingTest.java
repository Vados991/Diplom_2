import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import constant.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.UserAuthData;
import org.example.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellaburger.api.RequestCreateUser;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class UserAuthDataChangingTest {
    UserAuthData userAuthData;
    UserClient userUpdate;

    @Before
    @Step("Предусловия для создания тестов")
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
        userAuthData = UserAuthData.usersRandomCreate();
        userUpdate = RequestCreateUser.createUser(userAuthData).body().as(UserClient.class);
    }

    @Test
    @DisplayName("Изменение почты и имени авторизованного пользователя")
    @Description("Проверка, что пользователь может успешно изменить свои данные для входа и авторизоваться")
    public void checkAuthNewUserData() {
        UserAuthData newUserDataAuthorizationData = UserAuthData.usersRandomCreate();
        newUserDataAuthorizationData.setPassword("qwerty991");
        Response response = RequestCreateUser.updateUserData(newUserDataAuthorizationData, userUpdate.getAccessToken());
        assertTrue("Ответ успешный со статутом 200 ОК", response.path("success"));
        assertTrue("С новыми данными можно залогиниться", RequestCreateUser.authUser(newUserDataAuthorizationData).path("success"));
    }

    @Test
    @DisplayName("Попытка изменения почты и имени неавторизованного пользователя")
    @Description("Проверить, что неавторизованный пользователь не может изменить свои данные")
    public void checkAuthUserCouldNotChangeOwnData() {
        UserAuthData newUserDataAuthorizationData = UserAuthData.usersRandomCreate();
        newUserDataAuthorizationData.setPassword("qwerty9911");
        Response response = given()
                .header("Content-Type", "application/json")
                .body(userAuthData).patch(Constants.USER_AUTH);
        assertFalse("В ответе сообщение о неудачной авторизации", response.path("success"));
        assertEquals("Ошибка авторизации. Код 401", 401, response.statusCode());
    }

    @After
    @Step("Удаление созданного пользователя")
    public void tearDown() {
        RequestCreateUser.deleteUser(userUpdate.getAccessToken());
    }
}
