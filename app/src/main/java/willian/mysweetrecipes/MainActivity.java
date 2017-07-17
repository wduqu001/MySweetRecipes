package willian.mysweetrecipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_recipes)
    RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecipesAdapter = new RecipesAdapter();
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecipesAdapter);

    }
}
