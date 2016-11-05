package com.delivery2go.entity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.delivery2go.DeliveryApp;
import com.delivery2go.R;
import com.delivery2go.RantingConverter;
import com.delivery2go.ThumbailDrawableConverter;
import com.delivery2go.base.data.repository.IClientEntityFavoritesRepository;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.data.repository.IEntityRepository;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.dish.FragmentDishEdit;
import com.delivery2go.base.dishreview.FragmentDishReviewEdit;
import com.delivery2go.base.entity.FragmentEntityEdit;
import com.delivery2go.base.entityreview.ActivityEntityReviewEdit;
import com.delivery2go.base.entityreview.FragmentEntityReviewEdit;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.ClientEntityFavorites;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.enterlib.StringUtils;
import com.enterlib.app.UIUtils;
import com.enterlib.converters.IValueConverter;
import com.enterlib.databinding.BindingResources;
import com.enterlib.exceptions.ConversionFailException;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.mvvm.Command;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.FragmentView;
import com.enterlib.mvvm.IAsyncLoadOperation;
import com.enterlib.mvvm.IDataView;

import java.util.Observable;
import java.util.Observer;

public class FragmentEntityDetails extends BindableFragment implements Observer {

    private View commentsPanel;
    private View contentPanel;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.entity_fragment_details, container, false);
        commentsPanel = view.findViewById(R.id.commentsPanel);
        contentPanel = view.findViewById(R.id.contentPanel);

        ImageView btnLike = (ImageView) view.findViewById(R.id.btnLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ActivityEntityReviewEdit.class);

                int[]entity_id = (int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);
                i.putExtra(ActivityEntityDetails.ID, entity_id[0]);
                startActivityForResult(i, FragmentEntityReviewEdit.ENTITYREVIEW_EDITED);
            }
        });

		return view;
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_entity_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.showComments:
                if(commentsPanel.getVisibility() == View.GONE){
                    Animation enter = AnimationUtils.loadAnimation(getActivity(), R.anim.dock_right_enter);
                    commentsPanel.startAnimation(enter);
                    commentsPanel.setVisibility(View.VISIBLE);
                }else {
                    Animation exit = AnimationUtils.loadAnimation(getActivity(), R.anim.dock_right_exit);
                    commentsPanel.startAnimation(exit);
                    exit.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            commentsPanel.setVisibility(View.GONE);
                        }
                    });
                }
                return true;

            case R.id.refresh:
                load();
                return true;
        }
        return false;
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
		return new ViewModelEntityDetails((ActivityEntityWelcome)getActivity(), this,
				RepositoryManager.getInstance().getIEntityRepository(), 
				(int[])getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID));
	}

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        ActivityEntityWelcome activity = (ActivityEntityWelcome) getActivity();
//        activity.getViewModel().addObserver(this);
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        ActivityEntityWelcome activity = (ActivityEntityWelcome) getActivity();
        activity.getViewModel().deleteObserver(this);
        super.onDestroy();
    }

    @Override
    public void update(Observable observable, Object data) {
        //getViewModel().onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FragmentEntityReviewEdit.ENTITYREVIEW_EDITED && resultCode ==  FragmentEntityReviewEdit.ENTITYREVIEW_EDITED){
            load();
            getActivity().setResult(FragmentEntityEdit.ENTITY_EDITED);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class ViewModelEntityDetails extends com.delivery2go.base.entity.ViewModelEntityDetails{

        private final ViewModelEntityHome parentViewModel;
        private IClientEntityFavoritesRepository favRep;

		private IClientRepository clientRep;
		
		private ClientEntityFavorites fav;

		private Client client;

		private Context context; 

		public ViewModelEntityDetails(ActivityEntityWelcome context, FragmentView view,
				IEntityRepository entityRepository, int[] id) {
			super(view, entityRepository, id);
			
			favRep = RepositoryManager.getInstance().getIClientEntityFavoritesRepository();
			clientRep= RepositoryManager.getInstance().getIClientRepository();
			this.context = context;
            this.parentViewModel = context.getViewModel();

		}
		
		public boolean isFavorite(){
			return fav != null;
		}
		
		public Drawable getFavoriteImage(){
			if(isFavorite())
				return getResources().getDrawable(R.drawable.ic_action_star_10);
			return getResources().getDrawable(R.drawable.ic_action_star_disable);
		}
		
		@Override
		protected boolean loadAsync() throws Exception {
            super.loadAsync();

			client = DeliveryApp.getServerClient();
						
			if(_item!=null){
				fav = favRep.getFirst(String.format("ClientId = %d and EntityId = %d", client.Id, _item.Id));
				_item.getEntityReviews().load();
			}
			
			return true;
		}

		public Command UpdateFavorite = new Command() {

			@Override
			public void invoke(Object invocator, Object args) {
				doLoadOperationAsync(new IAsyncLoadOperation() {

					@Override
					public void onDataLoaded() {
						onPropertyChange("FavoriteImage");
					}

					@Override
					public boolean loadAsync() throws Exception {
						if(fav == null ){
							fav = new ClientEntityFavorites();
							fav.ClientId = client.Id;
							fav.EntityId = _item.Id;
							favRep.create(fav);
						}else if(favRep.delete(fav)){
							fav = null;
						}

						return true;
					}
				});
			}
		};
				
		
	}

}
