package com.example.android.todolist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntry;

import java.util.List;

// TODO (1) make this class extend AndroidViewModel and implement its default constructor
public class MainViewModel extends AndroidViewModel {

    private LiveData<List<TaskEntry>> tasks;
    private AppDatabase appDatabase;

    private static final String TAG = MainViewModel.class.getSimpleName();

    public MainViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Hello, Bye!");
        this.tasks = appDatabase.taskDao().loadAllTasks();
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }

    // TODO (2) Add a tasks member variable for a list of TaskEntry objects wrapped in a LiveData

    // TODO (4) In the constructor use the loadAllTasks of the taskDao to initialize the tasks variable

    // TODO (3) Create a getter for the tasks variable
}
