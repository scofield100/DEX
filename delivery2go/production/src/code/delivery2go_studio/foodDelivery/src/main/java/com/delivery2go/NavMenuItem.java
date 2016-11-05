package com.delivery2go;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class NavMenuItem {
	public Drawable Image;
	public String Name;
	
	public NavMenuItem(Context context, int imageId ,int nameId){
		Resources res = context.getResources();
		Name = res.getString(nameId);
		Image = res.getDrawable(imageId);
	}
	public NavMenuItem(Drawable image, String name) {
		super();
		Image = image;
		Name = name;
	}
}
