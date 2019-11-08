package id.co.endang.mymovie3.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.adapter.MovieAdapter;
import id.co.endang.mymovie3.common.BaseAppCompatActivity;
import id.co.endang.mymovie3.model.Movie;
import id.co.endang.mymovie3.model.MovieResponse;
import id.co.endang.mymovie3.rest.RESTMovie;
import id.co.endang.mymovie3.rest.RESTMovieCallback;

public class SearchActivity extends BaseAppCompatActivity implements RESTMovieCallback {

    public static String SEARCH_TEXT = "search_text";
    public static String SEARCH_TYPE = "search_type";
    private MovieAdapter movieAdapter;
    private ShimmerFrameLayout shimerContainer;
    private LinearLayout noContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        movieAdapter = new MovieAdapter();
        noContent = findViewById(R.id.noContent);


        final int type = getIntent().getIntExtra(SEARCH_TYPE, 0);
        String query = getIntent().getStringExtra(SEARCH_TEXT);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.search) + ": " + (query.length() > 20 ? (query.substring(0, 17) + "...") : query));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMovies.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            shimerContainer = findViewById(R.id.shimmer_container);
            shimerContainer.setVisibility(View.VISIBLE);
            shimerContainer.setDuration(1000);
            shimerContainer.startShimmerAnimation();
            searchMovie(getSharedPreferences(), type, query);
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            movieAdapter.setListMovie(list);
        }

        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data, int position) {
                showDetailMovie(data, getApplicationContext(), type, false);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STATE_MOVIES, movieAdapter.getListMovie());
        super.onSaveInstanceState(outState);
    }

    private void searchMovie(SharedPreferences sharedPreferences, int type, String query) {
        if (type == 0)
            getRESTMovies().searchMovies(RESTMovie.MOVIE_SEARCH, getLanguage(), query, this);
        else
            getRESTMovies().searchMovies(RESTMovie.TV_SEARCH, getLanguage(), query, this);
    }

    @Override
    public void onSuccess(MovieResponse response) {
        ArrayList<Movie> movies = response.getResults();
        movieAdapter.setListMovie(movies);
        shimerContainer.stopShimmerAnimation();
        shimerContainer.setVisibility(View.GONE);
        if (movies.size() == 0)
            noContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String error) {
        shimerContainer.stopShimmerAnimation();
        shimerContainer.setVisibility(View.GONE);
        noContent.setVisibility(View.VISIBLE);
        Snackbar.make(noContent, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
