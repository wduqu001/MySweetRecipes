package willian.mysweetrecipes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipesAdapter
        extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    // Create new views (invoked by the layout manager)
    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipes_list_item;
        final boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(context)
                .inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /* not yet implemented*/
    }

    @Override
    public int getItemCount() {
        /* not yet implemented*/
        return 3;
    }

    // Provide a reference to the views for each data item
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_recipe)
        TextView mRecipeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Toast.makeText(itemView.getContext(), "adapterPOsition  " + adapterPosition, Toast.LENGTH_SHORT).show();

        }
    }
}
