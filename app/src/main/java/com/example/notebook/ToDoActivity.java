package com.example.notebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {
    LinearLayout parentLinearLayout;
    ToDos todo;
    EditText title;
    ArrayList<String> todos_list;
    ArrayList<String> todo_status;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        todo = new ToDos();
        db = new DBHelper(getApplicationContext());
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        title = (EditText) findViewById(R.id.title);
        todos_list = new ArrayList<String>();
        todo_status = new ArrayList<String>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.to_do_list_item, null);
                // Add the new row before the add field button.
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
            }
        });
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            insertTodosIntoDb();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertTodosIntoDb(){
        todo.setTask_title(title.getText().toString().trim());
        int count = parentLinearLayout.getChildCount();
        for(int i = 1; i<count; i++){
            View row = parentLinearLayout.getChildAt(i);
            String item = ((EditText)row.findViewById(R.id.edit_text_item)).getText().toString();
            boolean isChecked = ((CheckBox)row.findViewById(R.id.item_check)).isChecked();
            todos_list.add(item);
            if(isChecked)
                todo_status.add("done");
            else
                todo_status.add("notDone");
        }
        Gson gson = new Gson();
        String todos_list_string= gson.toJson(todos_list);
        String todos_status_string = gson.toJson(todo_status);
        todo.setTodos(todos_list_string);
        todo.setStatus(todos_status_string);
        db.createTodo(todo);
        Toast.makeText(this,"Task added successfully",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ToDoActivity.this, TaskActivity.class);
        startActivity(intent);
    }
}
