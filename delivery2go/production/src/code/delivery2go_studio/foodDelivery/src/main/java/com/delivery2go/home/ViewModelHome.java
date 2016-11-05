package com.delivery2go.home;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.View;

import com.delivery2go.DeliveryApp;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.ICityRepository;
import com.delivery2go.base.models.City;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.Entity;
import com.enterlib.app.UIUtils;
import com.enterlib.data.IEntityCursor;
import com.enterlib.exceptions.InvalidOperationException;
import com.enterlib.mvvm.Command;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.enterlib.threading.AsyncManager;
import com.enterlib.threading.IWorkPost;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ViewModelHome extends EntityCursorViewModel<Entity> {

	public static final String LONGITUDE = "LONGITUDE";
	public static final String LATITUDE = "LATITUDE";
	public static final String ADRESS = "ADRESS";

	private FragmentHome fragment;
	List<Address> addresses;

	public ViewModelHome(FragmentHome view) {
		super(view);

		this.fragment = view;
	}

	@Override
	protected boolean loadAsync() throws Exception {

		return super.loadAsync();
	}

	@Override
	protected IEntityCursor<Entity> createCursor()
			throws InvalidOperationException {

		Client client = DeliveryApp.getServerClient();
		return client.getEntities().where("IsAvailable = true").load();
	}

	public Command OnFavoriteSelected = new Command() {

		@Override
		public void invoke(Object invocator, Object args) {
			getNavigator().navigateTo(ENTITY_DETAILS, null, args);

		}
	};

	public int getShowFavorites(){
		IEntityCursor<Entity> cursor = getCursor();
		if(cursor == null || cursor.getCount() == 0)
			return  View.GONE;
		return View.VISIBLE;
	}

	public int getShowFavoritesText(){
		IEntityCursor<Entity> cursor = getCursor();
		if(cursor == null || cursor.getCount() == 0)
			return  View.VISIBLE;
		return View.GONE;
	}


	public void findAddressLocation(final String address){
		AsyncManager.postAsync(new IWorkPost() {

			@Override
			public boolean runWork() throws Exception {
				Geocoder geocoder = fragment.getGeocoder();
				addresses = null;
				if(geocoder.isPresent()) {
					try {
						addresses = geocoder.getFromLocationName(address, 1);
					} catch (Exception e) {

					}
				}
				return true;
			}

			@Override
			public void onWorkFinish(Exception workException) {
				if(workException != null){
					return;
				}

                LatLng location = null;
				if(addresses != null && addresses.size() > 0){
					Address addr = addresses.get(0);
                    location = new LatLng(addr.getLatitude(),addr.getLongitude());
				}else{
					UIUtils.showMessage(fragment.getActivity(), "Unknown address location.");
				}

                fragment.onAddressLocationResolve(location);
			}
		});
	}
}
