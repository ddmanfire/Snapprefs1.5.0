package com.marz.snapprefs.FilterStoreUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SendfeedbackJob extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String[] params) {
        String filepath = null;
        try {
            URL wallpaperURL = new URL(params[0]);
            URLConnection connection = wallpaperURL.openConnection();
            InputStream inputStream = new BufferedInputStream(wallpaperURL.openStream(), 10240);
            File filterDir = new File(Environment.getExternalStorageDirectory().toString() + "/Snapprefs/Filters");
            File saveFile = new File(filterDir, params[1]);
            FileOutputStream outputStream = new FileOutputStream(saveFile);

            byte buffer[] = new byte[1024];
            int dataSize;
            int loadedSize = 0;
            while ((dataSize = inputStream.read(buffer)) != -1) {
                loadedSize += dataSize;
                //publishProgress(loadedSize);
                outputStream.write(buffer, 0, dataSize);
            }
            filepath = saveFile.getAbsolutePath();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return filepath;
    }

    @Override
    protected void onPostExecute(String message) {
        //process message
        Toast toast = Toast.makeText(Tab2Fragment.context1, message, Toast.LENGTH_LONG);
        toast.show();
        //if(message != null)
    }
}