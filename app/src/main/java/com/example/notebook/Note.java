package com.example.notebook;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Note implements Serializable
{
    private long mDateTime;
    private String mTitle;
    private String mContent;

    public Note(long dateTime,String title,String content)
    {
        mDateTime=dateTime;
        mTitle=title;
        mContent=content;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public void setDateTime(long mDateTime) {
        this.mDateTime = mDateTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getDateTimeFormatted(Context context)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy HH:mm:ss",context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(mDateTime));
    }

}
