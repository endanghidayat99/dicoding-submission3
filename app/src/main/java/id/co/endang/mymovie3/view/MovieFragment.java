package id.co.endang.mymovie3.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.adapter.MovieAdapter;
import id.co.endang.mymovie3.common.BaseFragment;
import id.co.endang.mymovie3.model.Movie;
import id.co.endang.mymovie3.model.MovieResponse;
import id.co.endang.mymovie3.rest.RESTMovie;
import id.co.endang.mymovie3.rest.RESTMovieCallback;

public class MovieFragment extends BaseFragment implements RESTMovieCallback {

    private MovieAdapter movieAdapter;
    private ShimmerFrameLayout shimerContainer;

    public MovieFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieAdapter = new MovieAdapter();

        RecyclerView rvMovies = view.findViewById(R.id.rvMovies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            shimerContainer = view.findViewById(R.id.shimmer_container);
            shimerContainer.setVisibility(View.VISIBLE);
            shimerContainer.setDuration(1000);
            shimerContainer.startShimmerAnimation();

            getRESTMovies().getMovies(RESTMovie.MOVIE_TOP_RATED_API, getLanguage(), this);
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            movieAdapter.setListMovie(list);
        }

        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data, int position) {
                showDetailMovie(data, getContext(), 0, false);
            }
        });
    }

    @Override
    public void onSuccess(MovieResponse response) {
        ArrayList<Movie> movies = response.getResults();
        movieAdapter.setListMovie(movies);
        shimerContainer.stopShimmerAnimation();
        shimerContainer.setVisibility(View.GONE);
    }

    @Override
    public void onFailed(String error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STATE_MOVIES, movieAdapter.getListMovie());
        super.onSaveInstanceState(outState);
    }
}
