package com.example.notebook;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskerActivity extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView listTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasker);

        dbHelper = new DbHelper(this);
        listTask = findViewById(R.id.main_listView_taskList);
        loadTaskList();
    }

    private void loadTaskList()
    {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if(mAdapter==null)
        {
            mAdapter =  new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            listTask.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void addTask(View view)
    {
        final EditText taskTextView =  new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add new Task")
                .setView(taskTextView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = String.valueOf(taskTextView.getText());
                        dbHelper.insertNewTask(task);
                        loadTaskList();
                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        dialog.show();
    }


    public void deleteTask(View view)
    {
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }

    public void backToMain(View view) {
        finish();
    }
}

