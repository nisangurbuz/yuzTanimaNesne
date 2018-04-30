package com.kongqw.listener;

import org.opencv.core.Mat;

/**
 * Created by kongqingwei on 2017/5/16.
 * OnCalcBackProjectListener
 */

public interface OnCalcBackProjectListener {

    // Back-projection histogram callback
    void onCalcBackProject(Mat backProject);
}
