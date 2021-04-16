package com.cleanup.todoc.model;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    LiveData<List<Task>> ascendingFilterName();

    @Query("SELECT * FROM task_table ORDER BY name DESC")
    LiveData<List<Task>> descendingFilterName();

    @Query("SELECT * FROM task_table ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> ascendingFilterDate();

    @Query("SELECT * FROM task_table ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> descendingFilterDate();

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTask();
}
