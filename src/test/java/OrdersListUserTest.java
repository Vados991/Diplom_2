import org.example.OrderClient;
import org.example.UserAuthData;
import org.example.UserClient;
import stellaburger.api.RequestCreateUser;
import stellaburger.api.RequestOrder;
import constant.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class OrdersListUserTest {
    UserClient createList;
    UserAuthData userAuthData;
    @Before
    @Step("Предусловия для создания тестов")
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
        userAuthData = UserAuthData.usersRandomCreate();
        createList = RequestCreateUser.createUser(userAuthData).body().as(UserClient.class);
    }
    @Test
    @DisplayName("Получить заказы авторизованным пользователем")
    @Description("Проверка на получение списка заказов с кодом статуса 200")
    public void getUserOrdersUsersWithoutOrders() {
        Response response = RequestOrder.getUserOrders(createList.getAccessToken());
        OrderClient orderResponse = response.as(OrderClient.class);
        assertTrue("Правильный код ответа - 200 ОК", response.path("success"));
        assertNotNull("В ответе присутствует список заказов", orderResponse.getOrders());
    }
    @Test
    @DisplayName("Получить список заказов неавторизованным пользователем")
    @Description("Проверка ошибки получения списка заказов с кодом состояния 401")
    public void getUserOrdersWithoutAuthUser() {
        Response response = given().get(Constants.ORDERS_BUR);
        assertFalse("Список заказов не получен", response.path("success"));
        assertEquals("Ошибка авторизации. Код 401", 401, response.statusCode());
    }
    @After
    @Step("Удалить тестового пользователя")
    public void tearDown() {
        RequestCreateUser.deleteUser(createList.getAccessToken());
    }
}