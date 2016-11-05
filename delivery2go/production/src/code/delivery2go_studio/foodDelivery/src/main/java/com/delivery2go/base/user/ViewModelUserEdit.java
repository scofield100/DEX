package com.delivery2go.base.user;

import java.util.List;
import java.util.ArrayList;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;
import com.delivery2go.base.data.repository.IPermissionRepository;
import com.delivery2go.base.models.Permission;
import com.enterlib.data.BaseModel;
import com.enterlib.data.IClosable;
import com.enterlib.exceptions.ValidationException;
import com.enterlib.mvvm.EditViewModel;
import com.enterlib.mvvm.IEditView;
import com.enterlib.exceptions.InvalidOperationException;
import com.delivery2go.base.data.repository.IUserRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.User;

public class ViewModelUserEdit extends EditViewModel implements IClosable {

	private final IPermissionRepository permissionRepository;
	protected IUserRepository userRepository;

	protected User _item;
	protected int[] _itemId;
	public User getItem(){ return this._item;}

	public String PasswordRepeat;

	@Override
	public Object getData(){
		return this._item;
	}

	public ViewModelUserEdit(IEditView view, IUserRepository userRepository, int[] id){
		super(view);

		this.userRepository=userRepository;
		this._itemId=id;

		permissionRepository = RepositoryManager.getInstance().getIPermissionRepository();

	}

    public boolean getCanActivate(){
        User user = DeliveryApp.getUser();
        if(user == null)
            return false;

        return Access.hasAccess(new String[]{Rolls.Administrator, Rolls.User}, Access.WRITE);
    }

    public boolean getCanGrandSuperadmin(){
        User user = DeliveryApp.getUser();
        if(user == null)
            return false;

        return user.Superadmin;
    }

	@Override
	protected boolean loadAsync() throws Exception{

		if(_itemId!=null && _itemId.length > 0){
			this._item = userRepository.get(_itemId);
		}
		else{
			this._item = userRepository.getInstance();
		}

        PasswordRepeat = _item.Password;

		return true;
	}



	@Override
	public void close() {
		this.userRepository.close();
        this.permissionRepository.close();
	}

	@Override
	protected boolean saveAsync() throws Exception{
		boolean succes = false;
        if(!PasswordRepeat.equals(_item.Password))
            throw new ValidationException("PasswordRepeat", "Must match Password");

        User other = userRepository.getFirst(String.format("Username='%s' and Id!=%d",_item.Username, _item.Id));
        if(other!=null)
            throw new ValidationException("Username",getView().getString(R.string.error_unique_username_));

		if(_itemId != null &&  _itemId.length > 0){
			succes=userRepository.update(this._item);
		}else{
			succes=userRepository.create(this._item) > 0;

            if(succes) {
                //add roles
                permissionRepository.create(new Permission(Rolls.Entity_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.EntityMenu_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.Dish_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.DishCategories_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.EntityImages_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.DishImages_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.Image_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.Addons_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.DishSizePrice_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.Order_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.OrderDish_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.OrderDishAddons_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.UserEntities_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.EntityReviews_ID, _item.Id, true, true, true, true));
                permissionRepository.create(new Permission(Rolls.DishReviews_ID, _item.Id, true, true, true, true));
				permissionRepository.create(new Permission(Rolls.DishCategory_ID, _item.Id, true, false, false, false));
				permissionRepository.create(new Permission(Rolls.DishSize_ID, _item.Id, true, false, false, false));
            }

		}

		if(!succes)throw new InvalidOperationException("Error");

		return true;
	}


}