package com.delivery2go;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.RepositoryManager;
import com.enterlib.app.UIUtils;
import com.enterlib.converters.IValueConverter;
import com.enterlib.data.IEntityContext;
import com.enterlib.exceptions.ConversionFailException;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.fields.Field;
import com.enterlib.fields.FieldValueProvider;
import com.enterlib.threading.LoaderHandler;
import com.enterlib.widgets.ProgressLayout;

public class ThumbailDrawableConverter implements IValueConverter {

	private ProgressLayout progress;
	LoaderHandler loader;
	private IEntityContext context;
	private RepositoryManager rpManager;
	Context resContext;
	private IImageProvider imageProvider;

	int width;
	int height;
	
	public ThumbailDrawableConverter(int width, int height) {
		
		loader = new LoaderHandler();	
		loader.setAutofinish(false);
		
		rpManager = RepositoryManager.getInstance();
		context = rpManager.getEntityContext();
		resContext = rpManager.getContext();
		imageProvider = DeliveryApp.getInstance().createImageProvider();

		this.width = width;
		this.height = height;
	}

	public ThumbailDrawableConverter(int width, int height, ProgressLayout progress) {
		this(width, height);

		this.progress = progress;
	}

    public ThumbailDrawableConverter(){
        this(0,0);
    }

    public ThumbailDrawableConverter(ProgressLayout progress){
        this(0,0, progress);
    }

	@Override
	public Object convert(Object value) throws ConversionFailException {
		if(value == null){
//			 Resources res = resContext.getResources();
//			 return res.getDrawable(R.drawable.sample_logo);
			return null;
		}else if( value instanceof Drawable){
			return value;
		}else{
            int id = (Integer)value;

            if(id <= 0)
                return null;

			Log.d("ThumbailDrawableProvider","Converting value "+String.valueOf(id));
			return new ThumbailDrawableProvider(id, loader);
		}
	}

	@Override
	public Object convertBack(Object value) throws ConversionFailException {	
		return value;
	}

	public class ThumbailDrawableProvider extends FieldValueProvider<Drawable>{

		int imageId;
		
				
		public ThumbailDrawableProvider(int imageId, LoaderHandler loader) {
			super();
			
			this.imageId = imageId;
			setLoaderHandler(loader);
		}

        @Override
        public void getValueAsync(final Field field) {
            if(width == 0 && height == 0){
                field.getView().post(new Runnable() {
                    @Override
                    public void run() {
                        ThumbailDrawableProvider.super.getValueAsync(field);
                    }
                });
            }else {
                super.getValueAsync(field);
            }
        }

        @Override
		protected Drawable loadValue(Field field) throws Exception {			 
			 Resources res = resContext.getResources();
			 int width = ThumbailDrawableConverter.this.width;
             int height =ThumbailDrawableConverter.this.height;

            if(width ==0 && height == 0) {
                View view = field.getView();
                width = view.getWidth();
                height = view.getHeight();
            }

			try {
				Bitmap bmp = imageProvider.getImage(imageId, width, height);
				if (bmp == null) {
					return null;
				}
				return new BitmapDrawable(resContext.getResources(), bmp);

			}catch (InvalidOperationException e){
				return null;
			}
		}

		@Override
		protected void onLoadStart(Field field) {
            if(progress!=null){
                progress.setMessage(R.string.loading);
                progress.showProgress();
            }

            super.onLoadStart(field);
		}

        @Override
        protected void onLoadCompleted(Field field, Object value, Exception e) {
            if(progress!=null){
                progress.closeProgress();
            }

            super.onLoadCompleted(field, value, e);
        }
    }
}
