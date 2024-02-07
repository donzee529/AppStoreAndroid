package com.webview.myapplication;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class DownloadBroadcastReceiver extends BroadcastReceiver {
    private WebView mWebView;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            //install app
            try {
                Process su = Runtime.getRuntime().exec("su");
                DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

                outputStream.writeBytes("su -c mv /sdcard/download/"+ MainActivity.appName     +".bin /data/local/tmp/file.apk && su -c pm install /data/local/tmp/file.apk");
                outputStream.flush();

                outputStream.writeBytes("exit\n");
                outputStream.flush();
                su.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}