package com.example.xyzreader.ui;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "ArticleDetailFragment";

    public static final String ARG_ITEM_ID = "item_id";
    private static final float PARALLAX_FACTOR = 1.25f;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.article_title)
    TextView articleTitle;
    @BindView(R.id.article_byline)
    TextView articleByline;
    @BindView(R.id.meta_bar)
    LinearLayout metaBar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.article_body)
    TextView articleBody;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.image)
    ImageView image;

    private Cursor mCursor;
    private long mItemId;
    private View mRootView;
    private int mMutedColor = 0xFF333333;
    private ColorDrawable mStatusBarColorDrawable;

    private int mTopInset;
    private View mPhotoContainerView;
    private int mScrollY;
    private boolean mIsCard = false;
    private int mStatusBarFullOpacityBottom;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(long itemId) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
        }

        mIsCard = getResources().getBoolean(R.bool.detail_is_card);
        mStatusBarFullOpacityBottom = getResources().getDimensionPixelSize(
                R.dimen.detail_card_top_margin);
        setHasOptionsMenu(true);


    }


    public ArticleDetailActivity getActivityCast() {
        return (ArticleDetailActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_article_detail2, container, false);




        ButterKnife.bind(this, mRootView);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bindViews();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText("Some sample text")
                        .getIntent(), getString(R.string.action_share)));
            }
        });
        return mRootView;
    }


    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        articleByline.setMovementMethod(new LinkMovementMethod());
        articleBody.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Rosario-Regular.ttf"));

        if (mCursor != null) {
            mRootView.setAlpha(0);
            mRootView.setVisibility(View.VISIBLE);
            mRootView.animate().alpha(1);
            articleTitle.setText(mCursor.getString(ArticleLoader.Query.TITLE));
            articleByline.setText(Html.fromHtml(
                    DateUtils.getRelativeTimeSpanString(
                            mCursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                            System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_ALL).toString()
                            + " by <font color='#ffffff'>"
                            + mCursor.getString(ArticleLoader.Query.AUTHOR)
                            + "</font>"));
            articleBody.setText(Html.fromHtml(mCursor.getString(ArticleLoader.Query.BODY)));
            final CollapsingToolbarLayout.LayoutParams params = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            final String imagePath = mCursor.getString(ArticleLoader.Query.PHOTO_URL);
            Picasso.with(getActivity()).load(imagePath).into(image,  new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    image.setLayoutParams(params);
                    PicassoPalette.with(imagePath , image)
                            .use(PicassoPalette.Profile.VIBRANT)
                            .intoBackground(metaBar, PicassoPalette.Swatch.RGB)
                            .intoTextColor(articleTitle, PicassoPalette.Swatch.TITLE_TEXT_COLOR)
                            .intoTextColor(articleByline , PicassoPalette.Swatch.TITLE_TEXT_COLOR);
                }

                @Override
                public void onError() {

                }
            });
        } else {
            mRootView.setVisibility(View.GONE);
            articleTitle.setText("N/A");
            articleByline.setText("N/A");
            articleBody.setText("N/A");
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (!isAdded()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        mCursor = cursor;
        if (mCursor != null && !mCursor.moveToFirst()) {
            Log.e(TAG, "Error reading item detail cursor");
            mCursor.close();
            mCursor = null;
        }

        bindViews();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
        bindViews();
    }


}
