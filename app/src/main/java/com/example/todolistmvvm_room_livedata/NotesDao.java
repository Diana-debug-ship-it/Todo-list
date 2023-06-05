package com.example.todolistmvvm_room_livedata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(Note note);

    @Query("DELETE FROM notes WHERE id=:id")
    Completable removeAt(int id);

//    @Query("SELECT * FROM notes")
//    List<Note> getNotes();
//
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void add(Note note);
//
//    @Query("DELETE FROM notes WHERE id=:id")
//    void removeAt(int id);
}
