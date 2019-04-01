package com.example.notebook;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardAdapter.MyHolder> {
    List<TaskCard> taskCards;
    Context mContext;
    TaskCard taskCard;

    public TaskCardAdapter(Context context, List<TaskCard> taskCards){
        this.taskCards = taskCards;
        mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        taskCard = taskCards.get(position);
        holder.taskTitle.setText(taskCard.getTaskCardTitle());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TaskEditActivity.class);
                taskCard = taskCards.get(position);
                intent.putExtra("task_id",taskCard.getTaskCardId());
                intent.putExtra("task_title",taskCard.getTaskCardTitle());
                intent.putExtra("task_todos",taskCard.getTaskCardTodos());
                intent.putExtra("task_status",taskCard.getTaskCardStatus());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskCards.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView taskTitle;
        ConstraintLayout parentLayout;
        public MyHolder(View itemView){
            super(itemView);
            taskTitle = (TextView)itemView.findViewById(R.id.card_title);
            parentLayout = (ConstraintLayout)itemView.findViewById(R.id.task_card_parent_layout);
        }
    }
}