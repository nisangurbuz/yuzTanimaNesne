package com.kongqw.listener;

import org.opencv.core.Point;

/**
 * Created by kongqingwei on 2017/5/17.
 * OnObjectTrackingListener Target tracking listener
 */

public interface OnObjectTrackingListener {

    void onObjectLocation(Point center);

    void onObjectLost();
}
