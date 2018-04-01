package com.pkapps.dotainfo.Tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.widget.Toast;

import com.pkapps.dotainfo.Activities.MainActivity;
import com.pkapps.dotainfo.Activities.VanityLogin;
import com.pkapps.dotainfo.DataTypes.Match;
import com.pkapps.dotainfo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
        return Integer.toString(hours)+":"+minutesTime+":"+secondsTime;

    }
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    public static void saveImage(Context ctx, Bitmap bitmap) {
        File filename;
        Activity act = (Activity) ctx;
        if(ctx.getClass().getSimpleName().equals("VanityLogin")){
            act = (VanityLogin)ctx;
        }else if(ctx.getClass().getSimpleName().equals("MainActivity")){
            act = (MainActivity)ctx;
        }

        int result = 1;
        boolean flag = true;
        try {
            if (ContextCompat.checkSelfPermission(ctx,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(act,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},result);
            }

            while(flag) {
                if(result == PackageManager.PERMISSION_GRANTED) {
                    flag = false;
                    File path = Environment.getExternalStorageDirectory();
                    filename = new File(path, "image.jpg");
                    FileOutputStream out = new FileOutputStream(filename);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                }
                if(result == PackageManager.PERMISSION_DENIED){
                    flag = false;
                    File path = Environment.getExternalStorageDirectory();
                    filename = new File(path, "image.jpg");
                    FileOutputStream out = new FileOutputStream(filename);
                    bitmap = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.dota_logo);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static Bitmap getBitmap(Activity ctx){
        int result = 0;
        if (ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ctx,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},result);
        }
        String path = Environment.getExternalStorageDirectory().toString();
        File f = new File(path + "/image.jpg");
        Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
        return bmp;
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
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            //Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(timestamp);
        }catch (Exception e) {
        }
        return "";
    }
}
