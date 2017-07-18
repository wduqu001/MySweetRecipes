package willian.mysweetrecipes.rest;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import willian.mysweetrecipes.RecipesAdapter;
import willian.mysweetrecipes.model.Recipe;

public class RetroClient {

    private static final String ROOT_URL = "http://go.udacity.com/";
    private static final String TAG = RetroClient.class.getSimpleName();

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    private static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }

    /**
     * GET data from the API and load it on the Adapter
     *
     * @param adapter the adapter that will receive the data
     */
    public static void getApiData(final RecipesAdapter adapter) {
        //Creating an object of the apiService interface
        ApiService api = RetroClient.getApiService();

        Call<List<Recipe>> call = api.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                adapter.setRecipeList(recipes);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                if (t.getMessage().equals("Unable to resolve host \"go.udacity.com\": No address associated with hostname")) {
                    Log.e(TAG, "There is no Internet connection or the API is down");
                } else {
                    Log.e(TAG, t.getMessage());
                }
            }
        });
    }
}
