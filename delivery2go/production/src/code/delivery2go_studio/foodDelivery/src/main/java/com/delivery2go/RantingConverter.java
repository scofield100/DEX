package com.delivery2go;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.delivery2go.base.data.RepositoryManager;
import com.enterlib.converters.IValueConverter;
import com.enterlib.exceptions.ConversionFailException;

public class RantingConverter implements IValueConverter {

	public static class RatingValue{
		public Drawable Image;

		public RatingValue(Drawable image) {
			super();
			Image = image;
		}
		
	}
	
	private Resources res;
	
	public RantingConverter(Context context) {		
		this.res = context.getResources();
	}
	
	@Override
	public Object convert(Object value) throws ConversionFailException {
		Integer Ranking = (Integer) value;		
			
		RatingValue[]images  = new RatingValue[5];		
		for (int i = 0; i < images.length; i++) {			
			if(Ranking!=null && i<=(Ranking - 1)){
				images[i]=new RatingValue(res.getDrawable(R.drawable.rating_on));
			}else{
				images[i]=new RatingValue(res.getDrawable(R.drawable.rating_off));
			}				
		}
		return images;
	}

	@Override
	public Object convertBack(Object value) throws ConversionFailException {
		// TODO Auto-generated method stub
		return value;
	}

}
