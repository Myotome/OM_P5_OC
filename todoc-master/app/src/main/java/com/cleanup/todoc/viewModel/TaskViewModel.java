package com.cleanup.todoc.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private final TaskRepository mRepository;
    private final LiveData<List<Task>> mAscTaskName;
    private final LiveData<List<Task>> mDescTaskName;
    private final LiveData<List<Task>> mAscTaskDate;
    private final LiveData<List<Task>> mDescTaskDate;
    private final LiveData<List<Task>> mAllTask;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAscTaskName = mRepository.ascendingFilterName();
        mDescTaskName = mRepository.descendingFilterName();
        mAscTaskDate = mRepository.ascendingFilterDate();
        mDescTaskDate = mRepository.descendingFilterDate();
        mAllTask = mRepository.getAllTask();
    }

    public void insert(Task task){
        mRepository.insert(task);
    }

    public void update (Task task){
        mRepository.update(task);
    }

    public void delete (Task task){
        mRepository.delete(task);
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

