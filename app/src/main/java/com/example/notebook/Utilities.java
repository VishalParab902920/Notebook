package com.example.notebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Utilities
{
    public static final String FILE_EXTENSION=".NOTE";

    public static boolean saveNoteObject(Context context, Note note)
    {
        String fileName=String.valueOf(note.getDateTime())+FILE_EXTENSION;

        FileOutputStream fos;
        ObjectOutputStream oos;

        try
        {
            fos=context.openFileOutput(fileName,context.MODE_PRIVATE);
            oos=new ObjectOutputStream(fos);
            oos.writeObject(note);
            oos.close();
            fos.close();
        }
        catch (IOException e)
        {
            Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static ArrayList<Note> getAllSavedNotes(Context context)
    {
        ArrayList<Note> notes=new ArrayList<>();
        File fileDir=context.getFilesDir();
        ArrayList<String> noteFiles=new ArrayList<>();

        for(String file:fileDir.list())
        {
            if(file.endsWith(FILE_EXTENSION))
            {
                noteFiles.add(file);
            }
        }

        FileInputStream fis;
        ObjectInputStream ois;

        for(int i=0;i<noteFiles.size();i++)
        {
            try
            {
                fis=context.openFileInput(noteFiles.get(i));
                ois=new ObjectInputStream(fis);

                notes.add((Note)ois.readObject());
                ois.close();
                fis.close();
            }
            catch (IOException | ClassNotFoundException e)
            {
                Toast.makeText(context, "Unable to fetch Files", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return notes;
    }

    public static Note getNoteByName(Context context,String fileName)
    {
        File file=new File(context.getFilesDir(),fileName);

        if(file.exists())
        {
            FileInputStream fis;
            ObjectInputStream ois;
            Note note;

            try
            {
                fis=context.openFileInput(fileName);
                ois=new ObjectInputStream(fis);

                note=(Note)ois.readObject();

                ois.close();
                fis.close();
            }
            catch (IOException | ClassNotFoundException e)
            {
                Toast.makeText(context, "Unable to fetch Files", Toast.LENGTH_SHORT).show();
                return null;
            }
            return note;
        }
        return null;
    }

    public static void deleteNoteObject(Context context, String fileName)
    {
        File dir = context.getFilesDir();
        File file=new File(dir,fileName);
        if(file.exists())
        {
            file.delete();
        }
    }
}

