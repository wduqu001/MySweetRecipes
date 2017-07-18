package willian.mysweetrecipes.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import willian.mysweetrecipes.model.Recipe;

public interface ApiService {

    /*
    Retrofit get annotation with the URL
    And the method that will return the List of recipes
    */
    @GET("/android-baking-app-json")
    // "http://go.udacity.com/android-baking-app-json"
    Call<List<Recipe>> getRecipes();

}