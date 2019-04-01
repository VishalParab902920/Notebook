package com.example.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    static final private String DATABASE_NAME = "todos.db";
    static final private int DATABASE_VERSION = 1;
    static final private String LOG = "DBHelper";
    Context ctx;

    //TASK TABLE
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK_TITLE = "task_title";
    private static final String COLUMN_TODOS = "todos";
    private static final String COLUMN_STATUS = "status";

    // create task table sql query
    private String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_TASKS + "("
            + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TASK_TITLE + " TEXT,"
            + COLUMN_TODOS + " TEXT," + COLUMN_STATUS + " INTEGER" + ")";

    public DBHelper(Context ct)
    {
        super(ct,DATABASE_NAME,null,DATABASE_VERSION);
        ctx=ct;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists "+ TABLE_TASKS);
        onCreate(sqLiteDatabase);
    }

    public long createTodo(ToDos todo){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK_TITLE, todo.getTask_title());
        contentValues.put(COLUMN_TODOS, todo.getTodos());
        contentValues.put(COLUMN_STATUS, todo.getStatus());

        //insert row
        long task_id = db.insert(TABLE_TASKS, null, contentValues);

        return task_id;
    }

    /**
     *  Get all tasks for recycler view
     */
    public List<TaskCard> getAllTaskCards(){
        List<TaskCard> tasks = new ArrayList<TaskCard>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS +";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c!=null && c.moveToFirst()){
            do{
                TaskCard taskCard = new TaskCard();
                taskCard.setTaskCardId(c.getInt(c.getColumnIndex(COLUMN_TASK_ID)));
                taskCard.setTaskCardTitle(c.getString(c.getColumnIndex(COLUMN_TASK_TITLE)));
                taskCard.setTaskCardTodos(c.getString(c.getColumnIndex(COLUMN_TODOS)));
                taskCard.setTaskCardStatus(c.getString(c.getColumnIndex(COLUMN_STATUS)));
                tasks.add(taskCard);
            }while(c.moveToNext());
        }
        return tasks;
    }

    public int updateTodo(ToDos todo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK_TITLE, todo.getTask_title());
        contentValues.put(COLUMN_TODOS, todo.getTodos());
        contentValues.put(COLUMN_STATUS, todo.getStatus());
        //updating row
        return db.update(TABLE_TASKS, contentValues,COLUMN_TASK_ID + "=?", new String[] { String.valueOf(todo.getTask_id())});
    }

    /**
     * Deleting a Task
     */
    public void deleteTodo(int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM "+ TABLE_TASKS + " WHERE "+ COLUMN_TASK_ID + " = " + id + ";";
        db.execSQL(query);
    }
}

