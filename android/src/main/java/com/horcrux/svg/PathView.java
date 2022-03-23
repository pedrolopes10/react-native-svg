/*
 * Copyright (c) 2015-present, Horcrux.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */


package com.horcrux.svg;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

@SuppressLint("ViewConstructor")
class PathView extends RenderableView {
    private Path mPath;
    private ReactContext reactContext;

    public PathView(ReactContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        PathParser.mScale = mScale;
        mPath = new Path();
    }

    @ReactProp(name = "d")
    public void setD(String d) {
        try {
             mPath = PathParser.parse(d);
             elements = PathParser.elements;
             invalidate();
        } catch (Error e) {
           WritableMap params = Arguments.createMap();
           params.putString("error", e.getMessage());
           this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("svgError", params);
        }
    }

    @Override
    Path getPath(Canvas canvas, Paint paint) {
        return mPath;
    }
}
