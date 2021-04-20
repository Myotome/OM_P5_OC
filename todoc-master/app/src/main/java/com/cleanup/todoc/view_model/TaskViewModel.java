package com.cleanup.todoc.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private final TaskRepository mRepository;
    private final LiveData<List<Task>> mAllTask;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTask = mRepository.getAllTask();

    }

    public void insert(Task task){
        mRepository.insert(task);
    }

    public void delete (Task task){
        mRepository.delete(task);
    }

    public LiveData<List<Task>> getAllTask(){
        return mAllTask;
    }

    public void filterMethod(int filterChoice, List<Task> tasks) {
        switch (filterChoice) {
            case 0:
                break;
            case 1:
                Collections.sort(tasks, new TaskAZComparator());
                break;
            case 2:
                Collections.sort(tasks,new TaskZAComparator());
                break;
            case 3:
                Collections.sort(tasks,new TaskOldComparator());
                break;
            case 4:
                Collections.sort(tasks,new TaskRecentComparator());
                break;
        }

    }

    /**
     * Comparator to sort task from A to Z
     */
    private static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.getName().toLowerCase().compareTo(right.getName().toLowerCase());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    private static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.getName().toLowerCase().compareTo(left.getName().toLowerCase());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    private static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.getCreationTimestamp() - left.getCreationTimestamp());
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    private static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.getCreationTimestamp() - right.getCreationTimestamp());
        }
    }
}

