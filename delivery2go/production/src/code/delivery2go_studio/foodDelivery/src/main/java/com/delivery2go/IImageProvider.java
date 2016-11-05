package com.delivery2go;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by ansel on 21/09/2016.
 */
public interface IImageProvider {
    Bitmap getImage(int imageId, int width, int height);

    boolean saveImage(int imageId ,Bitmap image);
}
