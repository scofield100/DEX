package com.delivery2go.base.entity;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.RantingConverter;
import com.delivery2go.ThumbailDrawableConverter;
import com.enterlib.converters.IValueConverter;
import com.enterlib.exceptions.ConversionFailException;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.databinding.BindingResources;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.entity.ViewModelEntityDetails;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Entity})
public class FragmentEntityDetails extends BindableFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_details, menu);
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Entity}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Entity}, Access.WRITE)){
				menu.findItem(R.id.edit).setEnabled(false);
				menu.findItem(R.id.edit).setVisible(false);
			}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_entity_fragment_details, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.edit:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityEntityEdit.class).putExtra(ActivityEntityDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID)), FragmentEntityEdit.ENTITY_EDITED);
				return true;
			case R.id.delete:
				com.enterlib.DialogUtil.showAlertDialog(getActivity(), getString(R.string.dialog_delete_title), new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog, int which) {
						getViewModel().delete(getString(R.string.deleting), FragmentEntityDetails.this);
					}
				},null);
				return true;
			case R.id.attach:
				startActivity(new  Intent(getActivity().getApplicationContext(), ActivityEntityList.class).putExtra(ActivityEntityDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID)));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return new BindingResources()
		.put("TimeConverter", new com.enterlib.converters.DateToStringConverter("HH:mm"))
		.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
		.put("ImageConverter", new ThumbailDrawableConverter())
		.put("RankingConverter", new RantingConverter(getActivity()))
		.put("AdressConverter", new IValueConverter() {
			@Override
			public Object convertBack(Object value) throws ConversionFailException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object convert(Object value) throws ConversionFailException {
				Entity e = (Entity) value;
				return String.format("%s ,%s, %s", e.Adress, e.CityName, e.StateName);
			}
		})
		;
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		return new ViewModelEntityDetails(this, RepositoryManager.getInstance().getIEntityRepository(), (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID));
	}

	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		switch (requestCode) {
			case ViewModelEntityDetails.IMAGE:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.image.ActivityImageDetails.class).putExtra(com.delivery2go.base.image.ActivityImageDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelEntityDetails.CITY:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.city.ActivityCityDetails.class).putExtra(com.delivery2go.base.city.ActivityCityDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelEntityDetails.STATE:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.state.ActivityStateDetails.class).putExtra(com.delivery2go.base.state.ActivityStateDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelEntityDetails.UPDATEUSER:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.user.ActivityUserDetails.class).putExtra(com.delivery2go.base.user.ActivityUserDetails.ID, new int[]{(Integer)data}));
				break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentEntityEdit.ENTITY_EDITED){
			load();
			getActivity().setResult(FragmentEntityEdit.ENTITY_EDITED);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentEntityEdit.ENTITY_EDITED);
		super.onDeleted(data);
	}
}
