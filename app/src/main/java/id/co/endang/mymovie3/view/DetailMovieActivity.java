package id.co.endang.mymovie3.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.StringUtils;

import id.co.endang.mymovie3.BuildConfig;
import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.db.model.MovieFavorite;
import id.co.endang.mymovie3.db.repository.MovieRepository;
import id.co.endang.mymovie3.model.Movie;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_STATUS = "extra_status";
    public static final String EXTRA_TYPE = "extra_type";
    private ProgressBar progressBar;
    private Movie movie;
    private TextView overview, title, releaseDate, rating;
    private ImageView poster, backdrop;
    private View constraintLayout;
    private boolean isFavorite;
    private MovieRepository movieRepository;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        backdrop = findViewById(R.id.backdrop);
        poster = findViewById(R.id.poster);
        title = findViewById(R.id.title);
        rating = findViewById(R.id.rating);
        releaseDate = findViewById(R.id.release_date);
        overview = findViewById(R.id.overview);
        progressBar = findViewById(R.id.progressBar);
        constraintLayout = findViewById(R.id.detail_activity);
        movieRepository = new MovieRepository(getApplicationContext());
        showLoading(true);
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        type = getIntent().getIntExtra(EXTRA_TYPE,0);
        isFavorite = getIntent().getBooleanExtra(EXTRA_STATUS,false);
        if (movie != null) {
            initView();
            if (getSupportActionBar()!=null)
                getSupportActionBar().setTitle(StringUtils.isNotBlank(movie.getTitle()) ? movie.getTitle() : movie.getName());
        } else {
            Snackbar.make(constraintLayout, "Failed Load Data", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        String poster_url = BuildConfig.IMAGE_URL_BASE_PATH + movie.getPosterPath();
        String backdrop_url = BuildConfig.BACKDROP_URL_BASE_PATH + movie.getBackdropPath();
        Glide.with(this)
                .load(backdrop_url)
                .apply(new RequestOptions().override(200, 200))
                .into(backdrop);

        Glide.with(this)
                .load(poster_url)
                .apply(new RequestOptions().override(120, 120))
                .into(poster);
        title.setText(StringUtils.isNotBlank(movie.getTitle()) ? movie.getTitle() : movie.getName());
        backdrop.setContentDescription(title.getText().toString());
        poster.setContentDescription(title.getText().toString());
        rating.setText(movie.getVoteAverage().toString());
        releaseDate.setText(StringUtils.isNotBlank(movie.getReleaseDate()) ? movie.getReleaseDate() : movie.getFirstAirDate());
        overview.setText(movie.getOverview());
        showLoading(false);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_favorite){
            if (isFavorite) {
                deleteFromFavorite();
            }else{
                addToFavorite();
            }
        }else if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void deleteFromFavorite() {
        MovieFavorite fav = getMovieFavorite();
        movieRepository.deleteFavorite(fav.getId_tmb());
        Snackbar.make(constraintLayout, getString(R.string.success_remove), Snackbar.LENGTH_SHORT).show();
    }

    private void addToFavorite() {
        MovieFavorite fav = getMovieFavorite();
        movieRepository.insertFavorite(fav);
        Snackbar.make(constraintLayout, getString(R.string.success_add), Snackbar.LENGTH_SHORT).show();
    }

    private MovieFavorite getMovieFavorite() {
        MovieFavorite fav = new MovieFavorite();
        fav.setId_tmb(movie.getId());
        fav.setTitle(StringUtils.isBlank(movie.getTitle())?movie.getName():movie.getTitle());
        fav.setOverview(movie.getOverview());
        fav.setBackdropPath(movie.getBackdropPath());
        fav.setPosterPath(movie.getPosterPath());
        fav.setReleaseDate(StringUtils.isBlank(movie.getReleaseDate())?movie.getFirstAirDate():movie.getReleaseDate());
        fav.setVoteAverage(movie.getVoteAverage());
        fav.setMovieType(type);
        return fav;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}

