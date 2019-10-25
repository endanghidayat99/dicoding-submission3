package id.co.endang.mymovie3.db.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

import id.co.endang.mymovie3.db.MovieDatabase;
import id.co.endang.mymovie3.db.model.MovieFavorite;

public class MovieRepository {

    private String DB_NAME = "db_movie";
    private MovieDatabase movieDatabase;

    public MovieRepository(Context context){
        movieDatabase = Room.databaseBuilder(context,MovieDatabase.class,DB_NAME).build();
    }

    public void insertFavorite(final MovieFavorite movie){
        new AsyncTask<Void,Void,Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                MovieFavorite movieFavorite = movieDatabase.daoAccess().getMovieById(movie.getId_tmb());
                Long status = 0L;
                if (movieFavorite==null)
                    status = movieDatabase.daoAccess().insertFavorite(movie);
                return status;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                Log.d("REPO","INSERTED "+aLong+" ROW");
            }
        }.execute();
    }

    public LiveData<List<MovieFavorite>> getMovieByType(int type){
        return movieDatabase.daoAccess().getMovieByType(type);
    }

    public void deleteFavorite(final int id){
        new AsyncTask<Void,Void,Integer>(){

            @Override
            protected Integer doInBackground(Void... voids) {
                int status = movieDatabase.daoAccess().deleteFavorite(id);
                return status;
            }

            @Override
            protected void onPostExecute(Integer aLong) {
                super.onPostExecute(aLong);
                Log.d("REPO","Berhasil hapus "+aLong+" row");
            }
        }.execute();
    }
}
