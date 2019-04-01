package com.example.notebook;

public class TaskCard {

    private String taskCardTodos;
    private String taskCardTitle;
    private String taskCardStatus;
    private int taskCardId;

    public TaskCard(){

    }
    public TaskCard(int taskCardId, String taskCardTodos, String taskCardStatus){
        this.taskCardId = taskCardId;
        this.taskCardTodos = taskCardTodos;
        this.taskCardStatus = taskCardStatus;
    }

    public String getTaskCardTitle() {
        return taskCardTitle;
    }

    public void setTaskCardTitle(String taskCardTitle) {
        this.taskCardTitle = taskCardTitle;
    }

    public int getTaskCardId() {
        return taskCardId;
    }

    public void setTaskCardId(int taskCardId) {
        this.taskCardId = taskCardId;
    }

    public String getTaskCardTodos() {
        return taskCardTodos;
    }

    public void setTaskCardTodos(String taskCardTodos) {
        this.taskCardTodos = taskCardTodos;
    }

    public String getTaskCardStatus() {
        return taskCardStatus;
    }

    public void setTaskCardStatus(String taskCardStatus) {
        this.taskCardStatus = taskCardStatus;
    }

}