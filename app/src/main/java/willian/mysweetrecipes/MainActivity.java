package willian.mysweetrecipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private static RecipesAdapter mRecipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecipesAdapter = new RecipesAdapter();

        getApiData();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecipesAdapter);
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
                if(!response.isSuccessful() ){
                    showErrorMessage();
                    return;
                }
                List<Recipe> recipes = response.body();
                mRecipesAdapter.setRecipeList(recipes);
                mRecipesAdapter.notifyDataSetChanged();
                showRecipes();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                showErrorMessage();

                if (t.getMessage().equals("Unable to resolve host \"go.udacity.com\": No address associated with hostname")) {
                    Log.e(TAG, "There is no Internet connection or the API is down");
                } else {
                    Log.e(TAG, t.getMessage());
                }
            }
        });
    }

    private void showErrorMessage() {
        mLoadingIndicator.setVisibility(View.GONE);
        Toast.makeText(this,"Unable to sync with the server. Please check your connection and try again",Toast.LENGTH_LONG).show();
    }

    private void showRecipes() {
        mLoadingIndicator.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

}
