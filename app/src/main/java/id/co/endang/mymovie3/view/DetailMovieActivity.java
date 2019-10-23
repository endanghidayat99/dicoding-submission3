package id.co.endang.mymovie3.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.StringUtils;

import id.co.endang.mymovie3.BuildConfig;
import id.co.endang.mymovie3.R;
import id.co.endang.mymovie3.model.Movie;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private ProgressBar progressBar;
    private Movie movie;
    private TextView overview, title, releaseDate, rating;
    private ImageView poster, backdrop;

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
        View constraintLayout = findViewById(R.id.detail_activity);
        showLoading(true);
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie != null) {
            initView();
            if (getSupportActionBar()!=null)
                getSupportActionBar().setTitle(StringUtils.isNotBlank(movie.getTitle()) ? movie.getTitle() : movie.getName());
        } else {
            Snackbar.make(constraintLayout, "Data Gagal Di Muat", Snackbar.LENGTH_SHORT).show();
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
}

