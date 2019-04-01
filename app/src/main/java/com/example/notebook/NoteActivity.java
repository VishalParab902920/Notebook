package com.example.notebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mContent;
    private String mNoteFileName;
    private Note mLoadedNote;
    public static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mTitle=findViewById(R.id.note_et_title);
        mContent=findViewById(R.id.note_et_content);

        mNoteFileName=getIntent().getStringExtra("NOTE_FILE");
        if(mNoteFileName!=null && !mNoteFileName.isEmpty())
        {
            mLoadedNote=Utilities.getNoteByName(this,mNoteFileName);
            if(mLoadedNote!=null)
            {
                mTitle.setText(mLoadedNote.getTitle());
                mContent.setText(mLoadedNote.getContent());
            }
        }
    }

    public void saveNote(View view) {
        Note note;
        String title;

        if(mContent.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, "Empty note cannot be saved!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mTitle.getText().toString().trim().isEmpty())
        {
            if(mContent.getText().toString().length()>15)
                title=mContent.getText().toString().substring(0,14);
            else
                title=mContent.getText().toString();
        }
        else {
            title = mTitle.getText().toString();
        }

        if(mLoadedNote==null)
        {
            note=new Note(System.currentTimeMillis(),title,mContent.getText().toString());
        }
        else
        {
            note=new Note(mLoadedNote.getDateTime(),title,mContent.getText().toString());
        }


        if(Utilities.saveNoteObject(this,note))
        {
            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Make sure device has enough storage to save a Note", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void deleteNote(View view) {
        if(mLoadedNote==null)
        {
            finish();
        }
        else
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete Note")
                    .setMessage(mTitle.getText().toString()+" will be deleted!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.deleteNoteObject(getApplicationContext(),mLoadedNote.getDateTime()+Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(), "Note deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .setCancelable(false);
            dialog.show();
        }
    }

    public void back(View view) {
        finish();
    }

    public void speechToText(View view)
    {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak in to te mic clearly");

        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_SPEECH_INPUT)
        {
            if(resultCode==RESULT_OK && null!=data)
            {
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String temp=mContent.getText().toString() +
                        " ";
                String content=temp + result.get(0);
                mContent.setText(content);
            }
        }
    }
}

