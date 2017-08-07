package willian.mysweetrecipes;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeStepDetailFragment extends Fragment {
    private String DESCRIPTION;
    private String VIDEO_URL;
    private String THUMBNAIL_URL;
    private SimpleExoPlayer mPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView mDescriptionView;

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

        DESCRIPTION = getArguments().getString("description");
        VIDEO_URL = getArguments().getString("videoURL");
        THUMBNAIL_URL = getArguments().getString("thumbnailURL");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail, container, false);

        mDescriptionView = (TextView) rootView.findViewById(R.id.tv_description);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        mDescriptionView.setText(String.valueOf(DESCRIPTION));

        if(VIDEO_URL.isEmpty() && THUMBNAIL_URL.isEmpty()){
            mPlayerView.setVisibility(View.GONE);
        }
        String mediaUrl = VIDEO_URL.isEmpty() ? THUMBNAIL_URL : VIDEO_URL;
        InitializePlayer(Uri.parse(mediaUrl));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void InitializePlayer(Uri mediaUri) {
        if (mPlayer == null) {
            // Create an instance of Exoplayer
            TrackSelector trackSelector =
                    new DefaultTrackSelector();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            // Bind the mPlayer to the view.
            mPlayerView.setPlayer(mPlayer);
            // Prepare the MediaSource
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

}
