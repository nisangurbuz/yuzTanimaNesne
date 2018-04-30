package Tez.aslinisan.TezProje;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kongqw.permissionslibrary.PermissionsManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 显示缺少权限的对话框
     */
    protected void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Request permission");
        builder.setMessage("Android 6.0+ Dynamically request camera permissions");
        builder.setPositiveButton("To set permissions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PermissionsManager.startAppSettings(getApplicationContext());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 显示没有安装OpenCV Manager的对话框
     */
    protected void showInstallDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("You are not installed yet OpenCV Manager");
        builder.setMessage("Whether to download and install?");
        builder.setPositiveButton("go download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "go download", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/kongqw/FaceDetectLibrary/tree/opencv3.2.0/OpenCVManager")));
            }
        });
        builder.setNegativeButton("drop out\n", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }
}
