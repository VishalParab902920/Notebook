package com.example.notebook;

import java.io.Serializable;

public class ToDos implements Serializable {
    private int task_id;
    private String task_title;
    private String todos;
    private String status;

    public ToDos(){
    }

    public ToDos(int task_id, String task_title, String todos, String status)
    {
        this.task_id = task_id;
        this.task_title = task_title;
        this.todos = todos;
        this.status = status;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTodos() {
        return todos;
    }

    public void setTodos(String todos) {
        this.todos = todos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
