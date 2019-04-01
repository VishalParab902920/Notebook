package com.example.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note>
{
    public NoteAdapter(Context context, int resource, ArrayList<Note> notes)
    {
        super(context,resource,notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_notes,null);
        }

        Note note=getItem(position);
        if(note!=null)
        {
            TextView title=convertView.findViewById(R.id.main_list_item_notes_title);
            TextView date=convertView.findViewById(R.id.main_list_item_notes_date);
            TextView content=convertView.findViewById(R.id.main_list_item_notes_content);

            title.setText(note.getTitle());
            date.setText(note.getDateTimeFormatted(getContext()));
            if(note.getContent().length()>50)
            {
                content.setText(note.getContent().substring(0,50));
            }
            else
            {
                content.setText(note.getContent());
            }
        }

        return convertView;
    }
}
