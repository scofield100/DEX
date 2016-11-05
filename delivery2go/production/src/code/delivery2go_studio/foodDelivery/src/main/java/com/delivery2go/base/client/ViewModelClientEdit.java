package com.delivery2go.base.client;

import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.enterlib.StringUtils;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.exceptions.ValidationException;
import com.enterlib.mvvm.Command;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Client;
import com.enterlib.validations.ErrorInfo;

public class ViewModelClientEdit extends EditViewModel implements IClosable {

	protected IClientRepository clientRepository;

	protected Client _item;
	protected int[] _itemId;
	public Client getItem(){ return this._item;}

	@Override
	public Object getData(){
		return this._item;
	}

    public boolean LoginEnable = false;

    public String PasswordRepeat;

    public int getTextColor(){
        if(LoginEnable){
            return 0xff515151;
        }
        return DeliveryApp.getInstance().getResources().getColor(R.color.text_secondary);
    }

	public Command EnableLogIn = new Command() {
		@Override
		public void invoke(Object invocator, Object args) {
            LoginEnable =! LoginEnable;
            onPropertyChange("LoginEnable");
            onPropertyChange("TextColor");
		}
	};

	public ViewModelClientEdit(IEditView view, IClientRepository clientRepository, int[] id){
		super(view);

		this.clientRepository=clientRepository;
		this._itemId=id;
	}

	@Override
	protected boolean loadAsync() throws Exception {
		if(_itemId!=null && _itemId.length > 0){
			this._item = clientRepository.get(_itemId);
		}
		else{
			this._item = DeliveryApp.getServerClient();
		}

        PasswordRepeat = _item.Password;

		return true;
	}



	@Override
	public void close() {
		this.clientRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;

        if(LoginEnable){
            ErrorInfo ei = new ErrorInfo();
            if(StringUtils.isNullOrWhitespace(_item.Username))
                ei.add("Username", getView().getString(R.string.required));
            if(StringUtils.isNullOrWhitespace(_item.Password))
                ei.add("Password", getView().getString(R.string.required));
            if(StringUtils.isNullOrWhitespace(PasswordRepeat))
                ei.add("PasswordRepeat", getView().getString(R.string.required));

            if(!StringUtils.isNullOrWhitespace(_item.Password) && !StringUtils.isNullOrWhitespace(PasswordRepeat) && !_item.Password.equals(PasswordRepeat)){
                ei.add("PasswordRepeat", "Must match Password");
            }


            if(!StringUtils.isNullOrWhitespace(_item.Username)) {
                Client other = clientRepository.getFirst(String.format("Username='%s' and Id!=%d", _item.Username, _item.Id));
                if (other != null) {
                    ei.add("Username", getView().getString(R.string.error_unique_username_));
                }
            }

            if(ei.containsErrors()){
                throw new ValidationException(ei);
            }

            _item.IsRegistered = true;
        }

        _item.UpdateDate = new Date();
		_item.Lat = null;
		_item.Lng = null;

		succes = clientRepository.update(this._item);

		if(!succes)
			throw new InvalidOperationException("Error");

        DeliveryApp.setClient(_item);

		return true;
	}


}