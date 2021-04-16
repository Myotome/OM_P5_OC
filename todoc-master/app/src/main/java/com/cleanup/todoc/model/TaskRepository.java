package com.cleanup.todoc.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private final TaskDao mTaskDao;
    private final LiveData<List<Task>> mAscTaskName;
    private final LiveData<List<Task>> mDescTaskName;
    private final LiveData<List<Task>> mAscTaskDate;
    private final LiveData<List<Task>> mDescTaskDate;
    private final LiveData<List<Task>> mAllTask;

    public TaskRepository(Application application){
        TaskDataBase dataBase = TaskDataBase.getInstance(application);
        mTaskDao = dataBase.taskDao();
        mAscTaskName = mTaskDao.ascendingFilterName();
        mDescTaskName = mTaskDao.descendingFilterName();
        mAscTaskDate = mTaskDao.ascendingFilterDate();
        mDescTaskDate = mTaskDao.descendingFilterDate();
        mAllTask = mTaskDao.getAllTask();
    }

    public void insert(final Task task){
        TaskDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDao.insert(task);
            }
        });
    }
    public void update(final Task task){
        TaskDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDao.update(task);
            }
        });
    }
    public void delete(final Task task){
        TaskDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDao.delete(task);
            }
        });
    }
    public LiveData<List<Task>> ascendingFilterName(){
        return mAscTaskName;
    }
    public LiveData<List<Task>> descendingFilterName(){
        return mDescTaskName;
    }
    public LiveData<List<Task>> ascendingFilterDate(){
        return mAscTaskDate;
    }
    public LiveData<List<Task>> descendingFilterDate(){
        return mDescTaskDate;
    }
    public LiveData<List<Task>> getAllTask(){
        return mAllTask;
    }


}
