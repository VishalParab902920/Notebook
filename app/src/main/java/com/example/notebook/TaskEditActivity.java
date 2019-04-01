package com.example.notebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TaskEditActivity extends AppCompatActivity {

    LinearLayout parentLinearLayout;
    EditText todos_title;
    ArrayList<String> todos_list;
    ArrayList<String> todo_status;
    DBHelper db;
    FloatingActionButton fab;
    LayoutInflater inflater;
    ToDos todo;
    int task_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        todo = new ToDos();
        db = new DBHelper(getApplicationContext());
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        todos_title = (EditText) findViewById(R.id.title);
        todos_list = new ArrayList<String>();
        todo_status = new ArrayList<String>();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.to_do_list_item, null);
                // Add the new row before the add field button.
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
            }
        });
        setViewsFromIntent();
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_to_do, menu);
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
            updateTodos();
            return true;
        }
        if(id == R.id.action_delete){
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            AlertDialog show = builder.setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this Task?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteTodo(task_id);
                            Toast.makeText(getApplicationContext(), "Task deleted successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(TaskEditActivity.this, TaskActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * sets views as per previous values
     */
    private void setViewsFromIntent() {

        Intent intent = getIntent();
        task_id = intent.getIntExtra("task_id",0);
        String task_title = intent.getStringExtra("task_title");
        String task_todos = intent.getStringExtra("task_todos");
        String task_status = intent.getStringExtra("task_status");


        //setting views
        todos_title.setText(task_title);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> final_task_todos = gson.fromJson(task_todos, type);
        ArrayList<String> final_task_status = gson.fromJson(task_status, type);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        EditText first = (EditText) findViewById(R.id.edit_text_item);
        first.setText(final_task_todos.get(0));
        CheckBox firstC = (CheckBox) findViewById(R.id.item_check);
        String stateFirstC = final_task_status.get(0);
        Log.e("statefirst",stateFirstC);
        if(stateFirstC.equals("done"))
            firstC.setChecked(true);
        else if(stateFirstC.equals("notDone"))
            firstC.setChecked(false);

        Log.e("arraylist_todos",final_task_todos.toString());
        Log.e("arraylist_status",final_task_status.toString());
        for(int i=1;i<final_task_todos.size();i++){
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.to_do_list_item, null);
            EditText todo = (EditText) rowView.findViewById(R.id.edit_text_item);
            todo.setText(final_task_todos.get(i));
            CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.item_check);
            String state = final_task_status.get(i);
            Log.e("state:",i+" "+state);
            if(state.equals("done"))
                checkBox.setChecked(true);
            else if(state.equals("notDone"))
                checkBox.setChecked(false);
            // Add the new row before the add field button.
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
        }
    }

    public void updateTodos(){
        todo.setTask_title(todos_title.getText().toString().trim());
        int count = parentLinearLayout.getChildCount();
        for(int i = 1; i<count; i++){
            final View row = parentLinearLayout.getChildAt(i);
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
        todo.setTask_id(task_id);
        todo.setStatus(todos_status_string);
        db.updateTodo(todo);
        Toast.makeText(this,"Task added successfully",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(TaskEditActivity.this, TaskActivity.class);
        startActivity(intent);
    }

}
