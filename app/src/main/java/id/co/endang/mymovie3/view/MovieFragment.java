package id.co.endang.mymovie3.view;

import android.content.Context;
import android.content.SharedPreferences;
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

    private ArrayList<Movie> movies = new ArrayList<>();
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        movieAdapter = new MovieAdapter(movies);

        RecyclerView rvMovies = view.findViewById(R.id.rvMovies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            shimerContainer = view.findViewById(R.id.shimmer_container);
            shimerContainer.setVisibility(View.VISIBLE);
            shimerContainer.setDuration(1000);
            shimerContainer.startShimmerAnimation();

            getRESTMovies().getMovies(RESTMovie.MOVIE_TOP_RATED_API, getLanguage(sharedPreferences), this);
        } else {
            movies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            movieAdapter.refresh(movies);
        }

        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data) {
                showDetailMovie(data,getContext());
            }
        });
    }

    @Override
    public void onSuccess(MovieResponse response) {
        movies = response.getResults();
        movieAdapter.refresh(movies);
        shimerContainer.stopShimmerAnimation();
        shimerContainer.setVisibility(View.GONE);
    }

    @Override
    public void onFailed(String error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STATE_MOVIES, movies);
        super.onSaveInstanceState(outState);
    }
}
