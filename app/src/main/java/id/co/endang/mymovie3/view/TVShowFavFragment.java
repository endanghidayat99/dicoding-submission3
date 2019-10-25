package id.co.endang.mymovie3.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.adapter.MovieAdapter;
import id.co.endang.mymovie3.common.BaseFragment;
import id.co.endang.mymovie3.db.model.MovieFavorite;
import id.co.endang.mymovie3.db.repository.MovieRepository;
import id.co.endang.mymovie3.model.Movie;

public class TVShowFavFragment extends BaseFragment {
    private MovieAdapter movieAdapter;
    private ShimmerFrameLayout shimerContainer;
    private MovieRepository movieRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieAdapter = new MovieAdapter();
        movieRepository = new MovieRepository(getContext());
        RecyclerView rvMovies = view.findViewById(R.id.rvMovies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            shimerContainer = view.findViewById(R.id.shimmer_container);
            shimerContainer.setVisibility(View.VISIBLE);
            shimerContainer.setDuration(1000);
            shimerContainer.startShimmerAnimation();
            loadTVShowData();
        } else {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            movieAdapter.setListMovie(movies);
        }

        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data) {
                showDetailMovie(data,getContext(),1,true);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        movieAdapter.setListMovie(new ArrayList<Movie>());
        shimerContainer = getView().findViewById(R.id.shimmer_container);
        shimerContainer.setVisibility(View.VISIBLE);
        shimerContainer.setDuration(1000);
        shimerContainer.startShimmerAnimation();
        loadTVShowData();
    }

    private void loadTVShowData() {
        movieRepository.getMovieByType(1).observe(this, new Observer<List<MovieFavorite>>() {
            @Override
            public void onChanged(List<MovieFavorite> movieFavorites) {
                if (movieFavorites.size()>0){
                    ArrayList<Movie> movies = getListMovies(movieFavorites);
                    movieAdapter.setListMovie(movies);
                    shimerContainer.stopShimmerAnimation();
                    shimerContainer.setVisibility(View.GONE);
                }
            }
        });
    }
}
