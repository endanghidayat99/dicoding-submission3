package id.co.endang.mymovie3.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

public class MovieFavFragment extends BaseFragment {

    private MovieAdapter movieAdapter;
    private ShimmerFrameLayout shimerContainer;
    private MovieRepository movieRepository;
    private LinearLayout noContent;
    private RecyclerView rvMovies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieRepository = new MovieRepository(getContext());
        movieAdapter = new MovieAdapter();

        rvMovies = view.findViewById(R.id.rvMovies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(movieAdapter);
        noContent = view.findViewById(R.id.noContent);
        noContent.setVisibility(View.VISIBLE);
        if (savedInstanceState == null) {
            shimerContainer = view.findViewById(R.id.shimmer_container);
//            shimerContainer.setVisibility(View.VISIBLE);
//            shimerContainer.setDuration(1000);
//            shimerContainer.startShimmerAnimation();
            loadDataMovie();
        } else {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            movieAdapter.setListMovie(movies);
        }

        movieAdapter.setOnItemClickCallBack(new MovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data,int position) {
                showDetailMovieForResult(data,getContext(),0,true,position);
            }
        });
    }

    private void loadDataMovie() {
        movieRepository.getMovieByType(0).observe(this, new Observer<List<MovieFavorite>>() {
            @Override
            public void onChanged(List<MovieFavorite> movieFavorites) {
                if (movieFavorites.size()>0){
                    ArrayList<Movie> movies = getListMovies(movieFavorites);
                    movieAdapter.setListMovie(movies);
                    noContent.setVisibility(View.GONE);
//                    shimerContainer.stopShimmerAnimation();
//                    shimerContainer.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            if (requestCode == DetailMovieActivity.REQUEST_UPDATE){
                if (resultCode == DetailMovieActivity.RESPONSE_REMOVE){
                    int position = data.getIntExtra(DetailMovieActivity.EXTRA_POSITION,0);
                    movieAdapter.removeItem(position);
                    rvMovies.smoothScrollToPosition(position);
                    if (movieAdapter.getListMovie().size()==0)noContent.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
