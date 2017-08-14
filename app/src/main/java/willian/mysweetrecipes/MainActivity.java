package willian.mysweetrecipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import willian.mysweetrecipes.model.Recipe;
import willian.mysweetrecipes.rest.ApiService;
import willian.mysweetrecipes.rest.RetroClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recyclerview_recipes)
    RecyclerView mRecyclerView;
    @BindView(R.id.pbProgress)
    ProgressBar mLoadingIndicator;
    Type recipeListType = new TypeToken<ArrayList<Recipe>>() {
    }.getType();
    private RecipesAdapter mRecipesAdapter;
    private List<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: ADD WIDGET ( HOME SCREEN)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecipesAdapter = new RecipesAdapter(this);

        // recovering the instance state
        if (savedInstanceState != null) {
            String json = savedInstanceState.getString("recipes");
            mRecipes = new Gson().fromJson(json, recipeListType);
            showRecipes();
        } else {
            getApiData();
        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecipesAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String recipes = new Gson().toJson(mRecipes, recipeListType);
        outState.putString("recipes", recipes);
        super.onSaveInstanceState(outState);
    }

    /**
     * GET data from the API and load it on the Adapter
     */
    public void getApiData() {
        ApiService api = RetroClient.getApiService();
        Call<List<Recipe>> call = api.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    showErrorMessage();
                    return;
                }
                mRecipes = response.body();
                showRecipes();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        mLoadingIndicator.setVisibility(View.GONE);
        Toast.makeText(this, "Unable to sync with the server. Please check your connection and try again", Toast.LENGTH_LONG).show();
    }

    private void showRecipes() {
        mRecipesAdapter.setRecipeList(mRecipes);
        mRecipesAdapter.notifyDataSetChanged();
        mLoadingIndicator.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


}
