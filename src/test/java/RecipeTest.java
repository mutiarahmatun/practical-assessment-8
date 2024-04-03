import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RecipeTest extends BaseTest{

    String apiKey = "b876c037d68f4c49a0ee0df6d29968f6";
    String hash = "6f5cd3989e8e45d2a1ffb82010eb1e6b698f31d0";
    String username ="mutia";

    @Test(description = "Search Recipes")
    public void SearchRecipes(){
        given()
                .queryParam("apiKey",apiKey)
                .log().ifValidationFails()
                .when()
                .get("complexSearch")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("results.size()",equalTo(10))
                .body("totalResults", equalTo(5216));
    }

    @Test(description = "Search Recipes by Nutrients")
    public void findByNutrients(){
        given()
                .queryParam("apiKey",apiKey)
                .queryParam("minCarbs",10)
                .queryParam("maxCarbs","50")
                .queryParam("number","3")
                .log().ifValidationFails()
                .when()
                .get("findByNutrients")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("results.size()",equalTo(3));
    }

    @Test(description = "Search Recipes by Ingredients")
    public void findByIngredients(){
        given()
                .queryParam("apiKey",apiKey)
                .queryParam("ingredients","apples")
                .queryParam("number","2")
                .log().ifValidationFails()
                .when()
                .get("findByIngredients")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("results.size()",equalTo(2));
    }

    @Test(description = "Analyze Recipe")
    public void analyzeRecipe() {
        String requestBody = "{\n" +
                "    \"title\": \"Spaghetti Carbonara\",\n" +
                "    \"servings\": 2,\n" +
                "    \"ingredients\": [\n" +
                "        \"1 lb spaghetti\",\n" +
                "        \"3.5 oz pancetta\",\n" +
                "        \"2 Tbsps olive oil\",\n" +
                "        \"1  egg\",\n" +
                "        \"0.5 cup parmesan cheese\"\n" +
                "    ],\n" +
                "    \"instructions\": \"Bring a large pot of water to a boil and season generously with salt. Add the pasta to the water once boiling and cook until al dente. Reserve 2 cups of cooking water and drain the pasta. \"\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("apiKey",apiKey)
                .body(requestBody)
                .log().ifValidationFails()
                .when()
                .post("analyze")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("title",equalTo("Spaghetti Carbonara"));
    }

    @Test(description = "Classify Cuisine")
    public void classifyCuisine() {
        String requestBody = "{\n" +
                "    \"cuisine\": \"Mediterranean\",\n" +
                "    \"cuisines\": [\n" +
                "        \"Mediterranean\",\n" +
                "        \"European\",\n" +
                "        \"Italian\"\n" +
                "    ],\n" +
                "    \"confidence\": 0.0\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("apiKey",apiKey)
                .body(requestBody)
                .log().ifValidationFails()
                .when()
                .post("cuisine")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("cuisine",equalTo("Mediterranean"));
    }

}
