package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    DBHelper database;
    RecyclerView recyclerView;
    TaskCardAdapter taskCardAdapter;
    List<TaskCard> taskCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //for recycler view
        recyclerView = (RecyclerView) findViewById(R.id.rv_tasks);
        taskCards = new ArrayList<TaskCard>();
        database = new DBHelper(this);
        taskCards = database.getAllTaskCards();
        taskCards = reverse(taskCards);
        taskCardAdapter = new TaskCardAdapter(TaskActivity.this, taskCards);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(taskCardAdapter);
        recyclerView.setVisibility(View.VISIBLE);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, ToDoActivity.class);
                startActivity(intent);
            }
        });
    }
    public List<TaskCard> reverse(List<TaskCard> list) {
        for(int i = 0, j = list.size() - 1; i < j; i++) {
            list.add(i, list.remove(j));
        }
        return list;
    }

}
