package stellaburger.api;
import constant.Constants;
import org.example.Ingredients;
import org.example.IngredientsDataList;

import static io.restassured.RestAssured.given;

public class RequestIngredient {
    public static Ingredients[] getIngredientsArray() {
        return getIngredientResponse().getIngredients();
    }
    public static IngredientsDataList getIngredientResponse() {
        return given().get(Constants.GET_INGREDIENTS).as(IngredientsDataList.class);
    }
    public static Ingredients getFirstIngredientOnTheList() {
        return getIngredientsArray()[0];
    }
}
