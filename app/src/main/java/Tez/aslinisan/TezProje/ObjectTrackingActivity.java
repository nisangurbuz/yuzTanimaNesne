package Tez.aslinisan.TezProje;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.kongqw.ObjectTrackingView;
import com.kongqw.listener.OnCalcBackProjectListener;
import com.kongqw.listener.OnObjectTrackingListener;
import com.kongqw.listener.OnOpenCVLoadListener;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;

public class ObjectTrackingActivity extends BaseActivity {

    private static final String TAG = "RobotTrackingActivity";
    private ObjectTrackingView objectTrackingView;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_object_tracking);

        imageView = (ImageView) findViewById(R.id.image_view);

        objectTrackingView = (ObjectTrackingView) findViewById(R.id.tracking_view);

        objectTrackingView.setOnOpenCVLoadListener(new OnOpenCVLoadListener() {
            @Override
            public void onOpenCVLoadSuccess() {
                Toast.makeText(getApplicationContext(), "OpenCV \n" +
                        "Load successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenCVLoadFail() {
                Toast.makeText(getApplicationContext(), "OpenCV \n" +
                        "Failed to load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotInstallOpenCVManager() {
                showInstallDialog();
            }
        });
        //Display anti-projection image debugging
        objectTrackingView.setOnCalcBackProjectListener(new OnCalcBackProjectListener() {
            @Override
            public void onCalcBackProject(final Mat backProject) {
                Log.i(TAG, "onCalcBackProject: " + backProject);
                ObjectTrackingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null == bitmap) {
                            bitmap = Bitmap.createBitmap(backProject.width(), backProject.height(), Bitmap.Config.ARGB_8888);
                        }
                        Utils.matToBitmap(backProject, bitmap);
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
        //Target detection callback
        objectTrackingView.setOnObjectTrackingListener(new OnObjectTrackingListener() {
            @Override
            public void onObjectLocation(Point center) {
                Log.i(TAG, "onObjectLocation: target location [" + center.x + ", " + center.y + "]");
            }

            @Override
            public void onObjectLost() {
                ObjectTrackingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "The goal is lost", Toast.LENGTH_SHORT).show();
                        imageView.setImageBitmap(null);
                    }
                });
            }
        });

        objectTrackingView.loadOpenCV(getApplicationContext());
    }

    /**
     *Switch the camera
     *
     * @param view view
     */
    public void swapCamera(View view) {
        objectTrackingView.swapCamera();
    }
}
