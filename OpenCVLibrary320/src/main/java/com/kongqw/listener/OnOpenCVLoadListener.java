package com.kongqw.listener;

/**
 * Created by kongqingwei on 2017/5/15.
 * OnOpenCVLoadListener
 */

public interface OnOpenCVLoadListener {

    // OpenCV Load successfully
    void onOpenCVLoadSuccess();

    // OpenCV Failed to load

    void onOpenCVLoadFail();

    //Not installed OpenCVManager
    void onNotInstallOpenCVManager();
}
