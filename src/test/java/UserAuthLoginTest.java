import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.UserAuthData;
import org.example.UserClient;
import stellaburger.api.RequestCreateUser;
import constant.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserAuthLoginTest {
    UserAuthData userAuthData;
    UserClient logUser;

    @Before
    @Step("Предусловия для создания тестов")
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
        userAuthData = UserAuthData.usersRandomCreate();
        logUser = RequestCreateUser.createUser(userAuthData).body().as(UserClient.class);
    }
    @Test
    @DisplayName("Войти под пользователем с действительными данными авторизации")
    @Description("Проверка успешной авторизации под пользователем")
    public void userCanSuccessfullyLogin() {
        Response response = RequestCreateUser.authUser(userAuthData);
        assertTrue("Успешная авторизация", response.path("success"));
    }
    @Test
    @DisplayName("Войти под пользователем не заполняя пароль")
    @Description("Проверка авторизации пользователя без заполнения пароля")
    public void userLoginUnsuccessWithoutPasswordField() {
        userAuthData.setPassword("qazwsxedc");
        Response response = RequestCreateUser.authUser(userAuthData);
        assertFalse("Авторизоваться не удалось", response.path("success"));
    }
    @Test
    @DisplayName("Войти под пользователем не заполняя логин")
    @Description("Проверка авторизации пользователя без заполнения логина")
    public void userLoginUnsuccessWithoutLoginField() {
        userAuthData.setEmail(RandomStringUtils.randomAlphabetic(12) + "@yandex.ru");
        Response response = RequestCreateUser.authUser(userAuthData);
        assertFalse("Авторизоваться не удалось", response.path("success"));
    }
    @After
    public void tearDown() {
        RequestCreateUser.deleteUser(logUser.getAccessToken());
    }
}