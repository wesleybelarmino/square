package com.app.square.data.local.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.app.square.common.pojo.Movie;
import io.reactivex.Maybe;
import java.util.List;

@Dao
public interface DaoMovie {

    @Insert
    void insert(Movie movie);

    @Query("SELECT * FROM movie")
    Maybe<List<Movie>> fetchAll();

    @Query("SELECT * FROM movie where id = :id")
    Maybe<Movie> findById(int id);

    @Delete
    void delete(Movie movie);
}
