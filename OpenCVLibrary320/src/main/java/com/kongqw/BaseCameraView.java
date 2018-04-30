package com.kongqw;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;

import com.kongqw.listener.OnOpenCVLoadListener;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

/**
 * Created by kongqingwei on 2017/5/17.
 * BaseRobotCameraView
 */

public abstract class BaseCameraView extends JavaCameraView implements LoaderCallbackInterface, CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "BaseRobotCameraView";

    public abstract void onOpenCVLoadSuccess();

    public abstract void onOpenCVLoadFail();

    // Mark the current OpenCV loading status
    private boolean isLoadSuccess;
    protected Mat mRgba;
    protected Mat mGray;

    // Control to switch the camera
    private int mCameraIndexCount = 0;

    public BaseCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Load OpenCV
        // boolean loadOpenCV = loadOpenCV(context);
        // Log.i(TAG, "BaseRobotCameraView [Load OpenCV] : " + loadOpenCV);

        setCvCameraViewListener(this);
    }

    /**
     *Load OpenCV
     *
     * @param context Context
     * @return Whether to load successfully
     */
    public boolean loadOpenCV(Context context) {
        // Initialize OpenCV
        return OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, context, this);
    }

    @Override
    public void onManagerConnected(int status) {
        switch (status) {
            case LoaderCallbackInterface.SUCCESS:
                Log.i(TAG, "onManagerConnected: Load successfully");
                isLoadSuccess = true;

                // Load successfully
                onOpenCVLoadSuccess();

                enableView();

                if (null != mOnOpenCVLoadListener) {
                    mOnOpenCVLoadListener.onOpenCVLoadSuccess();
                }
                break;
            default:
                isLoadSuccess = false;
                // Failed to load
                // super.onManagerConnected(status);
                onOpenCVLoadFail();
                Log.i(TAG, "onManagerConnected: Failed to load");
                if (null != mOnOpenCVLoadListener) {
                    mOnOpenCVLoadListener.onOpenCVLoadFail();
                }
                break;
        }
    }

    @Override
    public void onPackageInstall(int operation, InstallCallbackInterface callback) {
        // OpenCV Manager Not installed
        Log.i(TAG, "onPackageInstall: ");

        // Toast.makeText(getContext(), "OpenCV Manager Not installed", Toast.LENGTH_SHORT).show();
        if (null != mOnOpenCVLoadListener) {
            mOnOpenCVLoadListener.onNotInstallOpenCVManager();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mGray = new Mat();
        mRgba = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Log.i(TAG, "onWindowVisibilityChanged: " + visibility);
        switch (visibility) {
            case VISIBLE:
                Log.i(TAG, "onWindowVisibilityChanged: VISIBLE");
                enableView();
                break;
            case INVISIBLE:
                // Log.i(TAG, "onWindowVisibilityChanged: INVISIBLE");
                // disableView();
                // break;
            case GONE:
                // Log.i(TAG, "onWindowVisibilityChanged: GONE");
                // disableView();
                // break;
            default:
                // Log.i(TAG, "onWindowVisibilityChanged: default");
                disableView();
                break;
        }
    }

    @Override
    public void enableView() {
        // OpenCV It has been loaded successfully and the current camera is off
        if (isLoadSuccess && !mEnabled) {
            super.enableView();
        }
    }

    @Override
    public void disableView() {
        if (mEnabled) {
            super.disableView();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow: ");
        disableView();
    }


    /**
     * Switch the camera
     */
    public void swapCamera() {
        disableView();
        setCameraIndex(++mCameraIndexCount % getCameraCount());
        enableView();
    }

    /**
     * Get the number of cameras
     *
     * @return The number of cameras
     */
    private int getCameraCount() {
//        CameraManager manager = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            manager = (CameraManager) getContext().getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
//            try {
//                String[] cameraIdList = manager.getCameraIdList();
//                return cameraIdList.length;
//            } catch (CameraAccessException e) {
//                e.printStackTrace();
//                return 0;
//            }
//        } else {
//            return Camera.getNumberOfCameras();
//        }
        return Camera.getNumberOfCameras();
    }

    private OnOpenCVLoadListener mOnOpenCVLoadListener;

    /**
     * Add OpenCV loaded listener
     *
     * @param listener monitor
     */
    public void setOnOpenCVLoadListener(OnOpenCVLoadListener listener) {
        mOnOpenCVLoadListener = listener;
    }
}
