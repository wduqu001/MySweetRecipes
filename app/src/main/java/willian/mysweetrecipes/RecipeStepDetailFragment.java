package willian.mysweetrecipes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecipeStepDetailFragment extends Fragment {
    String VIDEO_URL;
    String DESCRIPTION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeStepDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        activity.setTitle(DESCRIPTION);
        VIDEO_URL = getArguments().getString("video");
        DESCRIPTION = getArguments().getString("description");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail, container, false);

        ((TextView) rootView.findViewById(R.id.tv_description)).setText(DESCRIPTION);

        return rootView;
    }
}
