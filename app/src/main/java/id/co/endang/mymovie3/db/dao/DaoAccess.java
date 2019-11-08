package id.co.endang.mymovie3.db.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import id.co.endang.mymovie3.db.model.MovieFavorite;

@Dao
public interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertFavorite(MovieFavorite movie);

    @Query("SELECT * from movie_favorite where movieType=:type")
    LiveData<List<MovieFavorite>> getMovieByType(int type);

    @Query("DELETE FROM movie_favorite WHERE id_tmb=:id")
    int deleteFavorite(int id);

    @Query("select * from movie_favorite where id_tmb=:id")
    MovieFavorite getMovieById(int id);

    @Query("SELECT * from movie_favorite")
    Cursor selectAll();

    @Query("SELECT * from movie_favorite where id=:id")
    Cursor selectById(long id);
}
