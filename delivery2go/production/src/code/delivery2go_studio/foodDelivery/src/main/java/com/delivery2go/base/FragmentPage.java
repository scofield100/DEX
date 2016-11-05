package com.delivery2go.base;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.delivery2go.base.Access;

public class FragmentPage  {
	Bundle args;
	Class<?> cls;
	String label;
	Drawable icon;
	Context context;

	public FragmentPage(Context context, Class<?> cls, String label, Drawable icon,  Bundle args) {		this.context = context;
		this.cls = cls;
		this.label = label;
		this.icon = icon;
		this.args = args;
	}

	public boolean hasAccess(int flags){
		return Access.hasAccess(cls, flags);
	}

	public Fragment newInstance(){
		 return Fragment.instantiate(context, cls.getName(), args);
	}

	public String getLabel() {
		return label;
	}

	public Drawable getIcon() {
		return icon;
	}

}

