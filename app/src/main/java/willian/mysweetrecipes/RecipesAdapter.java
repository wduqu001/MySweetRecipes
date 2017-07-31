package willian.mysweetrecipes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import willian.mysweetrecipes.model.Recipe;

public class RecipesAdapter
        extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private Context mContext;
    private List<Recipe> recipeList = new ArrayList<>();

    RecipesAdapter(Context context) {
        this.mContext = context;
    }

    void setRecipeList(List<Recipe> recipes) {
        this.recipeList = recipes;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipesAdapter.RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_linear_content;
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(context)
                .inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipesViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        holder.mRecipeTextView.setText(recipeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // Provide a reference to the views for each data item
    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_subtitle)
        TextView mRecipeTextView;

        RecipesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(mContext, RecipeStepsActivity.class);
            Recipe recipe = recipeList.get(position);

            String recipeJson = (new Gson().toJson(recipe));

            intent.putExtra("recipe", recipeJson);
            mContext.startActivity(intent);

        }
    }
}
