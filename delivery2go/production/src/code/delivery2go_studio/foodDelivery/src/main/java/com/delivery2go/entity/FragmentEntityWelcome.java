package com.delivery2go.entity;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ListAdapter;

import com.delivery2go.R;
import com.delivery2go.RantingConverter;
import com.delivery2go.ThumbailDrawableConverter;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.EntityCategory;
import com.delivery2go.base.dish.ActivityDishDetails;
import com.delivery2go.base.dish.ActivityDishEdit;
import com.delivery2go.base.dish.ActivityDishList;
import com.delivery2go.base.dish.FragmentDishEdit;
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.enterlib.StringUtils;
import com.enterlib.databinding.BindingResources;
import com.enterlib.exceptions.ConversionFailException;
import com.enterlib.fields.ListField;
import com.enterlib.filtering.DoubleFilterCondition;
import com.enterlib.filtering.FilterOp;
import com.enterlib.filtering.IntegerFilterCondition;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.filtering.SwitchCondition;
import com.enterlib.mvvm.ActionBarFilterableFragment;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IDataView;
import com.enterlib.widgets.ProgressLayout;

import java.util.Observable;
import java.util.Observer;

public class FragmentEntityWelcome extends ActionBarFilterableFragment implements Observer {

	private View menuPanel;

    ActivityEntityWelcome activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (ActivityEntityWelcome) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.activity = null;
    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		setSearchItemId(R.id.action_search);
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity.getViewModel().addObserver(this);
    }

    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.entity_menu_fragment_welcome, menu);	
		onInitSearch(menu);		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;		
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.entity_fragment_welcome, container,false);
		ProgressLayout progress = (ProgressLayout)view.findViewById(R.id.container);
		setProgressLayout(progress);

		menuPanel = view.findViewById(R.id.menuPanel);
		ImageButton btnShowMenuPanel = (ImageButton) view.findViewById(R.id.btnShowMenus);
		btnShowMenuPanel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(menuPanel.getVisibility() == View.INVISIBLE){
					Animation enter = AnimationUtils.loadAnimation(getActivity(), R.anim.dock_right_enter);
					menuPanel.startAnimation(enter);
					menuPanel.setVisibility(View.VISIBLE);
				}else if(menuPanel.getVisibility() == View.VISIBLE){
					Animation exit = AnimationUtils.loadAnimation(getActivity(), R.anim.dock_right_exit);
					menuPanel.startAnimation(exit);

					exit.setAnimationListener(new AnimationListener() {

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
							menuPanel.setVisibility(View.INVISIBLE);						
						}
					});

				}			
			}			 
		});

		ImageButton btnHideMenu = (ImageButton) view.findViewById(R.id.btnHideMenu);
		btnHideMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				closeMenuPanel();
				
			}			
		});
		return view;
	}
	
	public void closeMenuPanel() {
		Animation exit = AnimationUtils.loadAnimation(getActivity(), R.anim.dock_right_exit);
		menuPanel.startAnimation(exit);

		exit.setAnimationListener(new AnimationListener() {

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
				menuPanel.setVisibility(View.INVISIBLE);						
			}
		});
	}

	@Override
	protected BindingResources getBindingResources() {
		// TODO Auto-generated method stub
		return super.getBindingResources()
				.put("CurrencyConverter",  new com.enterlib.converters.CurrencyConverter(getString(R.string.currency)))
				.put("ImageConverter", new ThumbailDrawableConverter((int)getResources().getDimension(R.dimen.adapter_image),(int)getResources().getDimension(R.dimen.adapter_image)){
					@Override
					public Object convert(Object value)
							throws ConversionFailException {
						if(value instanceof Integer && (Integer)value == 0){
							return getResources().getDrawable(R.drawable.ic_action_like);
						}
						return super.convert(value);
					}
				})
				.put("RankingConverter", new RantingConverter(getActivity()))
				.put("TimeConverter",new com.enterlib.converters.DateToStringConverter("hh:mm a"));
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		int[] ids = (int[]) getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID);

		return new ViewModelEntityWelcome(this, RepositoryManager.getInstance().getIDishRepository(),ids[0], activity.getViewModel());
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.dishListView)
			,new StringFilterCondition<Dish>("Name", getActivity().getString(R.string.name)){
				protected boolean eval(String prefix, Dish item){
					return StringUtils.startsWordWith(prefix, item.Name);
				}
			}			
			,new IntegerFilterCondition<Dish>(getActivity(),"Ranking", getActivity().getString(R.string.ranking_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Integer value, Dish item){
					return value == null || (item.Ranking != null && item.Ranking >= value);
				}
			}
			,new IntegerFilterCondition<Dish>(getActivity(),"Ranking", getActivity().getString(R.string.ranking_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Integer value, Dish item){
					return value == null || (item.Ranking != null && item.Ranking <= value);
				}
			}
			,new IntegerFilterCondition<Dish>(getActivity(),"ReviewCount", getActivity().getString(R.string.reviewcount_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Integer value, Dish item){
					return value == null || (item.ReviewCount != null && item.ReviewCount >= value);
				}
			}
			,new IntegerFilterCondition<Dish>(getActivity(),"ReviewCount", getActivity().getString(R.string.reviewcount_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Integer value, Dish item){
					return value == null || (item.ReviewCount != null && item.ReviewCount <= value);
				}
			}
			,new DoubleFilterCondition<Dish>(getActivity(),"Price", getActivity().getString(R.string.price_from),FilterOp.GREATHER_EQUALS){
				protected boolean eval(Double value, Dish item){
					return value == null || item.Price >= value;
				}
			}
			,new DoubleFilterCondition<Dish>(getActivity(),"Price", getActivity().getString(R.string.price_to), FilterOp.LESS_EQUALS){
				protected boolean eval(Double value, Dish item){
					return value == null || item.Price <= value;
				}
			}
		);
	}


    @Override
    public void update(Observable observable, Object data) {
         getViewModel().onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FragmentDishEdit.DISH_EDITED && resultCode == Activity.RESULT_OK){
            activity.onOrderChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
