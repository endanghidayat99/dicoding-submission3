package id.co.endang.moviexixfavorite;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.co.endang.moviexixfavorite.adapter.MovieFavoriteAdapter;
import id.co.endang.moviexixfavorite.consumer.MovieFavoriteUri;
import id.co.endang.moviexixfavorite.helper.MappingHelper;
import id.co.endang.moviexixfavorite.model.MovieFavorite;

interface LoadMovieCallback {
    void preExecute();

    void postExecute(ArrayList<MovieFavorite> movies);
}

public class MainActivity extends AppCompatActivity implements LoadMovieCallback {

    private final static String EXTRA_STATE = "extra_state";
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout noContent;
    private RecyclerView rvMovies;
    private MovieFavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shimmerFrameLayout = findViewById(R.id.shimmer_container);
        noContent = findViewById(R.id.noContent);
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieFavoriteAdapter();
        adapter.notifyDataSetChanged();
        rvMovies.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(MovieFavoriteUri.MOVIE_URI, true, myObserver);

        if (savedInstanceState == null)
            new LoadMoviesAsync(this, this).execute();
        else {
            ArrayList<MovieFavorite> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) adapter.setData(list);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovie());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmerAnimation();
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MovieFavorite> movies) {
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
        if (movies != null && movies.size() > 0) {
            adapter.setData(movies);
        } else {
            noContent.setVisibility(View.VISIBLE);
            adapter.setData(new ArrayList<MovieFavorite>());
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMoviesAsync(context, (LoadMovieCallback) context).execute();
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<MovieFavorite>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        public LoadMoviesAsync(Context context, LoadMovieCallback weakCallback) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(weakCallback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieFavorite> doInBackground(Void... voids) {
            try {
                Context context = weakContext.get();
                Cursor dataCursor = context.getContentResolver().query(MovieFavoriteUri.MOVIE_URI, null, null, null, null);
                return MappingHelper.mapCursorToArrayList(dataCursor);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieFavorite> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }
}
