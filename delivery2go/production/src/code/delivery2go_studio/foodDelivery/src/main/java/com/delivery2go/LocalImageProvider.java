package com.delivery2go;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.models.Image;
import com.enterlib.app.UIUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ansel on 21/09/2016.
 */
public class LocalImageProvider implements IImageProvider {
    Context resContext;
    IImageRepository imageRepository;

    public LocalImageProvider(Context resContext) {
        this.resContext = resContext;
        imageRepository = RepositoryManager.getInstance().getIImageRepository();
    }

    @Override
    public Bitmap getImage(int imageId, int width, int height) {
        AssetManager asset = resContext.getAssets();
        Image image = imageRepository.get(imageId);
        if (image == null)
            return null;
        try {
            InputStream is = asset.open(image.Location);
            if (is != null) {
                //Bitmap bmp = BitmapFactory.decodeStream(is); //UIUtils.decodeSampledBitmapFromStream(is, imageView.getWidth(), imageView.getHeight());
                Bitmap bmp = UIUtils.decodeSampledBitmapFromStream(is, width, height);
                return bmp;
            }
            return null;
        } catch (IOException e) {
            try {
                FileInputStream stream = DeliveryApp.getInstance().openFileInput(String.valueOf(imageId));

                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(stream, null, options);

                // Calculate inSampleSize
                options.inSampleSize = UIUtils.calculateInSampleSize(options.outWidth, options.outHeight, width, height);

                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;

                stream.close();

                stream = DeliveryApp.getInstance().openFileInput(String.valueOf(imageId));

                Bitmap bmp = BitmapFactory.decodeStream(stream, null, options);
                stream.close();

                return bmp;

            } catch (FileNotFoundException er) {
                return null;
            } catch (IOException e1) {
                return null;
            }
        }
    }

    @Override
    public boolean saveImage(int imageId, Bitmap image) {

        //send binary
        FileOutputStream stream = null;
        try {
            stream = DeliveryApp.getInstance().openFileOutput(String.valueOf(imageId), Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            return  true;
        } catch (FileNotFoundException e) {
            return  false;
        } catch (IOException e) {
            return  false;
        }
    }
}
