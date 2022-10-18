package edu.naeemaziz.comcast.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import edu.naeemaziz.comcast.R;

public class Utility {

    public static void showExitDialogue(AppCompatActivity ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.CustomDialogTheme);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure to exit? ");
        builder.setNegativeButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ctx.finish();
                    }
                });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();

    }
    public static void showNetworkErrorDialogue(AppCompatActivity ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.CustomDialogTheme);
        builder.setTitle("No internet connection!");
        builder.setMessage("Check your internet connection or try again.");
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ctx.finish();
                    }
                });
        builder.show();

    }
    public static boolean isNetwork(AppCompatActivity ctx) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
