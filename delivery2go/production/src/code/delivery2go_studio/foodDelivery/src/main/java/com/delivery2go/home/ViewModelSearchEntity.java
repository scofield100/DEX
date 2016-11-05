package com.delivery2go.home;

import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.delivery2go.DeliveryApp;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.models.Entity;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IDataView;

public class ViewModelSearchEntity extends DataViewModel {

	/**Max distance 10 milles*/


	ArrayList<Entity> entities;
	private IEntityRepository entityRep;
	private String address;
	private Double lat;
	private Double lng;
	
	public ViewModelSearchEntity(IDataView view, String adress, Double lat, Double lng) {
		super(view);	
				
		entityRep = RepositoryManager.getInstance().getIEntityRepository();
		this.address = adress;
		this.lat = lat;
		this.lng = lng;

        //TESTING Values
        if(lat == null && lng == null) {
            this.lat = 23.11099;
            this.lng = -84.4218;
        }
	}
	
	public ArrayList<Entity>getItems(){
		return entities;
	}

	@Override
	protected boolean loadAsync() throws Exception {		
		entities = new ArrayList<Entity>();
		float[]result =new float[1];

		for (Entity entity : entityRep.getCursor("IsAvailable = true", "Ranking DESC")) {
			//TODO compute distance here to user location
			//if entity distance is less than MAX_DISTANCE add it to entities
			if(entity.Lat != null && entity.Lng != null){
				 Location.distanceBetween(lat, lng, entity.Lat, entity.Lng, result);
				entity.Distance = Double.valueOf( result[0] / DeliveryApp.MILLE_METERS);
			}else{
                entity.Distance = null;
            }

			if(entity.Distance == null || entity.Distance <= DeliveryApp.MAX_DISTANCE)
				entities.add(entity);
		}

		Collections.sort(entities, new Comparator<Entity>() {
			@Override
			public int compare(Entity lhs, Entity rhs) {
				if(lhs.Distance == null && rhs.Distance == null)
					return  0;
				if(lhs.Distance == null)
					return 1;
				else if(rhs.Distance == null)
					return -1;

				return  lhs.Distance.compareTo(rhs.Distance);
			}
		});

		return true;
	}
	
	
	

}
