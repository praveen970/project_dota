package com.pkapps.dotainfo.Tools;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;

import com.pkapps.dotainfo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by PraveenKumar on 3/29/2018.
 */

public class Util {
    public static String getDuration(int duration){
        String secondsTime, minutesTime;
        int hours = duration/3600;
        int minutes = (duration/60) - (hours*60) ;
        int seconds = duration%60;
        if(seconds<10){
            secondsTime = "0"+Integer.toString(seconds);
        }else{
            secondsTime = Integer.toString(seconds);
        }
        if(minutes<10){
            minutesTime = "0"+Integer.toString(minutes);
        }else{
            minutesTime = Integer.toString(minutes);
        }
        if(hours>0) {
            return Integer.toString(hours) + ":" + minutesTime + ":" + secondsTime;
        }else{
            return minutesTime + ":" + secondsTime;
        }

    }

    public static int getWinRate(int win, int lose){
        try {
            return (int) Math.round(((double) win / (win + lose))*100);
        }catch (ArithmeticException e){
            return 0;
        }
    }
    public static float getKDR(int kills, int deaths){
        try {
            return Float.parseFloat(new DecimalFormat(".##").format((double)kills/deaths));
        }catch (ArithmeticException e){
            return 0f;
        }
    }
    public static String checkIDInString(String url){

        String u = url.substring(url.indexOf('?'));
        String[] arr = u.split("openid.");
        String id = arr[7].substring(7,arr[7].length()-1);
        if(id.startsWith("765")&&id.length() == 17){
            return id;
        }else{
            return null;
        }

    }
    public  static String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(timestamp * 1000L);
            return DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        }catch (Exception e) {
        }
        return "";
    }
    public static void saveBitmapToInternalMemory(Context ctx, Bitmap bitmap){
        ContextWrapper cw = new ContextWrapper(ctx.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("ProfileDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Bitmap loadImageFromStorage(Context ctx)
    {
        ContextWrapper cw = new ContextWrapper(ctx.getApplicationContext());
        File path = cw.getDir("ProfileDir",Context.MODE_PRIVATE);
        try {
            File f=new File(path, "profile.jpg");
            return BitmapFactory.decodeStream(new FileInputStream(f));

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return BitmapFactory.decodeResource(ctx.getResources(),R.drawable.dota_logo);
    }

    public static int getMedal(Context ctx, int rank){
        switch (rank/10){
            case 1:
                return ctx.getResources().obtainTypedArray(R.array.medals1).getResourceId(rank%10,0);

            case 2:
                return ctx.getResources().obtainTypedArray(R.array.medals2).getResourceId(rank%10,0);

            case 3:
                return ctx.getResources().obtainTypedArray(R.array.medals3).getResourceId(rank%10,0);

            case 4:
                return ctx.getResources().obtainTypedArray(R.array.medals4).getResourceId(rank%10,0);

            case 5:
                return ctx.getResources().obtainTypedArray(R.array.medals5).getResourceId(rank%10,0);

            case 6:
                return ctx.getResources().obtainTypedArray(R.array.medals6).getResourceId(rank%10,0);

            case 7:
                return ctx.getResources().obtainTypedArray(R.array.medals7).getResourceId(rank%10,0);

            default:
                return ctx.getResources().obtainTypedArray(R.array.medals0).getResourceId(0,0);
        }
    }
}
