package com.delivery2go.base.entity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.delivery2go.R;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;
import com.enterlib.StringUtils;
import com.enterlib.app.UIUtils;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.FragmentView;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.Entity;

public class ViewModelEntityDetails extends DataViewModel implements IClosable {

	public static final int IMAGE = 1;
	public static final int CITY = 2;
	public static final int STATE = 3;
	public static final int UPDATEUSER = 4;

	protected IEntityRepository entityRepository;
	protected Entity _item;
	int[] _itemId;
	public Entity getItem(){ return this._item;}

	FragmentView fragmentView;

	public ViewModelEntityDetails(FragmentView view, IEntityRepository entityRepository, int[] id){
		super(view);

		this.entityRepository=entityRepository;
		this._itemId=id;
		this.fragmentView = view;



	}

	@Override
	protected boolean loadAsync() throws Exception{
		this._item = entityRepository.get(_itemId);
		return true;
	}

	@Override
	public void close() {
		this.entityRepository.close();
	}

	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=entityRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}

	public Command Call = new Command() {

		@Override
		public void invoke(Object invocator, Object args) {
			Entity entity = getItem();
			if(StringUtils.isNullOrWhitespace(entity.Phone))
				return;

			String uriAddress = "tel:" + entity.Phone;
			uriAddress = uriAddress.replace("(", "")
					.replace(")", "");
			try{
				Intent i = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse(uriAddress));
				fragmentView.startActivity(i);
			}catch(Exception e){
				UIUtils.showMessage(fragmentView.getActivity(), fragmentView.getString(R.string.error_calling_phone_number));
				Log.d(getClass().getName(), e.getMessage(), e);
			}

		}
	};

	public Command SendEmail = new Command() {

		@Override
		public void invoke(Object invocator, Object args) {
			Entity entity = getItem();
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("message/rfc822");
			intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{entity.Email});
			try {
				fragmentView.startActivity(Intent.createChooser(intent, fragmentView.getString(R.string.send_email)));

			}
			catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(fragmentView.getActivity(), fragmentView.getString(R.string.error_sending_email),
						Toast.LENGTH_SHORT).show();

			}

		}
	};



}