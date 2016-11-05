package com.delivery2go.base.entity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import com.enterlib.mvvm.BindableEditFragment;
import com.enterlib.mvvm.DataViewModel;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import com.enterlib.data.IRepositoryFactory;
import com.enterlib.filtering.FilterOp;
import android.view.MenuItem;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Image;
import com.delivery2go.base.data.repository.IImageRepository;
import com.delivery2go.base.image.ViewModelImageList;
import com.delivery2go.base.image.FragmentImageList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


public class FragmentEntityImagesImageList extends FragmentImageList{
	public static class Factory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return RepositoryManager.getInstance().getEntityImages_ImageRepository(ids[0], false);
		}
	}
	public static class AttachFactory implements IRepositoryFactory{
		public Object getInstance(Activity activity, Fragment fragment) {
			int[] ids =(int[])activity.getIntent().getSerializableExtra(ActivityEntityDetails.ID);
			return RepositoryManager.getInstance().getEntityImages_ImageRepository(ids[0], true);
		}
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids =(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);

		return new ViewModelImageList(this, RepositoryManager.getInstance().getEntityImages_ImageRepository(ids[0], false));
	}

}
