import io.qameta.allure.junit4.DisplayName;
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

public class CreateUserTest {

    UserAuthData userAuthData;
    UserClient respUser;

    @Before
    @Step("Предусловия для создания тестов")
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
        userAuthData = UserAuthData.usersRandomCreate();
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Тест на успешное создание пользователя с кодом состояния 200 ОК")
    public void userCanBeCreatedWithSuccess() {
        Response response = createUser(userAuthData);
        assertEquals("Правильный код ответа", 200, response.statusCode());
        respUser = response.body().as(UserClient.class);
        assertTrue("В ответе сообщение об успехе", respUser.isSuccess());
    }

    @Test
    @DisplayName("Cоздание пользователя с существующими данными")
    @Description("Проверка создания нового пользователя с существующим логином")
    public void userCanNotBeCreatedWithTheSameData() {
        Response response = createUser(userAuthData);
        assertEquals("Правильный код ответа", 200, response.statusCode());
        respUser = response.body().as(UserClient.class);
        response = createUser(userAuthData);
        UserClient failRespUser = response.body().as(UserClient.class);
        assertEquals("Неверный код ответа", 403, response.statusCode());
        assertFalse("Такой пользователь уже существует", failRespUser.isSuccess());
    }
    @Test
    @DisplayName("Создать пользователя без ввода имени")
    @Description("Проверка создания нового пользователя без заполнения поля имя")
    public void userCanNotBeCreatedWithoutNameField() {
        userAuthData.setName(null);
        creationUserFailedField();
    }
    @Test
    @DisplayName("Создать пользователя без ввода пароля")
    @Description("Проверка создания нового пользователя без заполнения поля пароль")
    public void userCanNotBeCreatedWithoutPasswordField() {
        userAuthData.setPassword(null);
        creationUserFailedField();
    }
    @Test
    @DisplayName("Создать пользователя без поля электронной почты")
    @Description("Проверка создания нового пользователя без поля электронной почты")
    public void userCanNotBeCreatedWithoutEmailField() {
        userAuthData.setEmail(null);
        creationUserFailedField();
    }
    public void creationUserFailedField() {
        Response response = createUser(userAuthData);
        respUser = response.body().as(UserClient.class);
        assertEquals("Неверный код ответа", 403, response.statusCode());
        assertFalse("Не заполнено обязательное поле", respUser.isSuccess());
    }
    @Step("Отправка данных для создания нового пользователя")
    public Response createUser(UserAuthData userAuthData) {
        return RequestCreateUser.createUser(userAuthData);
    }
    @After
    @Step("Удаление созданного пользователя")
    public void tearDown() {
        if (respUser.getAccessToken() == null) {
            return;
        }
        RequestCreateUser.deleteUser(respUser.getAccessToken());
    }
}