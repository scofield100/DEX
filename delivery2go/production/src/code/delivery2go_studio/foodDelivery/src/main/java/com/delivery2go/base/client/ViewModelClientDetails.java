package com.delivery2go.base.client;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.delivery2go.base.models.Entity;
import com.enterlib.StringUtils;
import com.enterlib.app.UIUtils;
import com.enterlib.data.IClosable;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.IDataView;
import com.enterlib.mvvm.Command;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.models.Client;

public class ViewModelClientDetails extends DataViewModel implements IClosable {

	private final BindableFragment fragmentView;
	protected IClientRepository clientRepository;
	protected Client _item;
	int[] _itemId;
	public Client getItem(){ return this._item;}

	public ViewModelClientDetails(BindableFragment view, IClientRepository clientRepository, int[] id){
		super(view);

		this.clientRepository=clientRepository;
		this._itemId=id;
		this.fragmentView = view;

	}

	@Override
	protected boolean loadAsync() throws Exception{
        if(_itemId == null){
            this._item = DeliveryApp.getServerClient();
            _itemId = new int[]{_item.Id};
        }else {
            this._item = clientRepository.get(_itemId);
        }
		return true;
	}



	@Override
	public void close() {
		this.clientRepository.close();
	}

	@Override
	protected boolean deleteAsync() throws Exception{
		if(this._item != null){
			boolean succes=clientRepository.delete(this._item);
			if(!succes)throw new InvalidOperationException("Error");
		}
		return true;
	}

	private void callPhoneNumber(String phone) {
		if(StringUtils.isNullOrWhitespace(phone))
			return;

		String uriAddress = "tel:" + phone;
		uriAddress = uriAddress.replace("(", "")
				.replace(")", "");
		try{
			Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(uriAddress));
			fragmentView.startActivity(i);
		}catch(Exception e){
			UIUtils.showMessage(fragmentView.getActivity(), fragmentView.getString(R.string.error_calling_phone_number));
			Log.d(getClass().getName(), e.getMessage(), e);
		}
	}

	public Command Call = new Command() {

		@Override
		public void invoke(Object invocator, Object args) {
			callPhoneNumber(_item.Phone);
		}
	};

    public Command CallMobile = new Command() {

        @Override
        public void invoke(Object invocator, Object args) {
            callPhoneNumber(_item.Mobile);
        }
    };

	public Command SendEmail = new Command() {

		@Override
		public void invoke(Object invocator, Object args) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("message/rfc822");
			intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{_item.Email});
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