package com.delivery2go;

import android.graphics.Bitmap;

import com.enterlib.conetivity.RestClient;
import com.enterlib.conetivity.WCFServiceClient;
import com.enterlib.exceptions.InvalidOperationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ansel on 21/09/2016.
 */
public class WebImageProvider implements IImageProvider {
    private final String sessionId;
    String baseUrl;

    public WebImageProvider(String baseUrl, String sessionId) {
        this.baseUrl = baseUrl;
        this.sessionId = sessionId;
    }

    @Override
    public Bitmap getImage(int imageId, int width, int height) {
        String url =String.format("%simage/getimage/?id=%d&width=%d&height=%d",baseUrl,imageId, width, height);
        Bitmap bmp = RestClient.downloadImage(url, new WCFServiceClient.RequestParameter[]{
                new WCFServiceClient.RequestParameter("SessionId", sessionId)
        });
        return bmp;
    }

    @Override
    public boolean saveImage(int imageId, Bitmap image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[]data = null;
        try{
            if(!image.compress(Bitmap.CompressFormat.JPEG, 100, bos))
                throw new InvalidOperationException("Invalid Bitmap");
            data = bos.toByteArray();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        String url =String.format("%simage/saveimage/",baseUrl);

        return RestClient.UploadData(url,
                boolean.class,
                data,
                new WCFServiceClient.RequestParameter("imageId", imageId),
                new WCFServiceClient.RequestParameter("SessionId", sessionId));
    }
}
