import constant.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Ingredients;
import org.example.Order;
import org.example.UserAuthData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellaburger.api.RequestCreateUser;
import stellaburger.api.RequestOrder;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static stellaburger.api.RequestIngredient.getFirstIngredientOnTheList;

public class CreateOrderTest {
    Ingredients actualIngredient;
    String authUserToken;

    @Before
    @Step("Предусловия для создания тестов")
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
        authUserToken = RequestCreateUser.createUser(UserAuthData.usersRandomCreate()).path("accessToken");
        actualIngredient = getFirstIngredientOnTheList();
    }
    @Test
    @DisplayName("Создать новый заказ авторизованным пользователем")
    @Description("Проверка на успешное создание нового заказа с кодом статуса 200 ОК")
    public void createNewOrderByAuth() {
        Order order = new Order(actualIngredient);
        Response response = RequestOrder.createOrder(order, authUserToken);
        assertTrue("Ответ успешный со статутом 200 ОК", response.path("success"));
    }
    @Test
    @DisplayName("Создать новый заказ неавторизованным пользователем")
    @Description("Проверка на успешное создание заказа неавторизованным пользователем с кодом статуса 200 OK")
    public void createNewOrderWithoutAuth() {
        Order order = new Order(actualIngredient);
        Response response = given()
                .header("Content-Type", "application/json")
                .body(order)
                .post(Constants.ORDERS_BUR);
        assertTrue("Ответ успешный со статутом 200 ОК", response.path("success"));
    }
    @Test
    @DisplayName("Создать новый заказ c авторизацией без ингредиентов")
    @Description("Проверка на успешное создание заказа с авторизацией, но без ингредиентов")
    public void createNewOrderAuthUserNoIngredients() {
        Order order = new Order();
        Response response = RequestOrder.createOrder(order, authUserToken);
        assertFalse("Заказ не создан", response.path("success"));
        assertEquals("Ответ со статусом Bad Request 400", 400, response.statusCode());
    }
    @Test
    @DisplayName("Создать новый заказ неавторизованным пользователем без ингредиентов")
    @Description("Проверка на неудачное создание заказа с кодом состояния 400")
    public void createNewOrderWithoutAuthUserIngredientsInvalidHash() {
        actualIngredient.set_id(actualIngredient.get_id().replace("a", "0"));
        Order order = new Order(actualIngredient);
        Response response = RequestOrder.createOrder(order, authUserToken);
        assertFalse("Заказ не создан", response.path("success"));
        assertEquals("Ответ со статусом Bad Request 400", 400, response.statusCode());
    }
    @Test
    @DisplayName("Создать новый заказ авторизованным пользователем с неправильными ингредиентами")
    @Description("Проверка на неудачное создание заказа с кодом состояния 500")
    public void createNewOrderAuthUserWithWrongIngredients() {
        actualIngredient.set_id("Неправильный ингредиент");
        Order order = new Order(actualIngredient);
        Response response = RequestOrder.createOrder(order, authUserToken);
        assertEquals("Ответ Internal Error со статусом 500", 500, response.statusCode());
    }
    @After
    @Step("Удалить тестового пользователя")
    public void tearDown() {
        RequestCreateUser.deleteUser(authUserToken);
    }
}