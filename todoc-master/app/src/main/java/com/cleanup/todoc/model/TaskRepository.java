package com.cleanup.todoc.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private final TaskDao mTaskDao;

    public TaskRepository(Application application){
        TaskDataBase dataBase = TaskDataBase.getInstance(application);
        mTaskDao = dataBase.taskDao();
    }

    public void insert(final Task task){
        TaskDataBase.databaseWriteExecutor.execute(() -> mTaskDao.insert(task));
    }

    public void delete(final Task task){
        TaskDataBase.databaseWriteExecutor.execute(() -> mTaskDao.delete(task));
    }

    public LiveData<List<Task>> getAllTask(){
        return mTaskDao.getAllTask();
    }


}
