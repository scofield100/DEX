package com.delivery2go.base.dish;

import java.util.Date;
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
import com.delivery2go.base.entity.ActivityEntityDetails;
import com.enterlib.StringUtils;
import com.enterlib.exceptions.ConversionFailException;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.mvvm.ActionBarFilterableFragment;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.fields.ListField;
import com.enterlib.databinding.BindingResources;
import com.enterlib.filtering.FilterOp;
import com.enterlib.filtering.SearchViewFilterController;
import com.enterlib.filtering.SpinnerFilterCondition;
import com.enterlib.filtering.StringFilterCondition;
import com.enterlib.filtering.SwitchCondition;
import com.enterlib.filtering.DateFilterCondition;
import com.enterlib.filtering.TimeFilterCondition;
import com.enterlib.filtering.DoubleFilterCondition;
import com.enterlib.filtering.IntegerFilterCondition;
import com.enterlib.mvvm.EntityCursorViewModel;
import com.delivery2go.base.data.RepositoryManager;
import com.enterlib.widgets.ProgressLayout;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.dish.ViewModelDishList;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Dish})
public class FragmentDishList extends ActionBarFilterableFragment {
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.model_list, menu);
		if(getActivity().getIntent().getBooleanExtra(com.enterlib.mvvm.ActionBarFilterableFragment.ATTACH_MODE, false)){
			menu.findItem(R.id.delete).setTitle(R.string.select);
			menu.findItem(R.id.delete).setIcon(R.drawable.ic_action_list_2);

			menu.findItem(R.id.create).setEnabled(false);
			menu.findItem(R.id.create).setVisible(false);

			onInitDelete(menu);
		} else {

			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Dish}, Access.DELETE)){
				menu.findItem(R.id.delete).setEnabled(false);
				menu.findItem(R.id.delete).setVisible(false);
			}else{
				onInitDelete(menu);
			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Dish}, Access.CREATE)){
				menu.findItem(R.id.create).setEnabled(false);
				menu.findItem(R.id.create).setVisible(false);
			}
		}
		onInitSearch(menu);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSearchItemId(R.id.action_search);
		setDeleteItemId(R.id.delete);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_dish_fragment_list, container, false);
		ProgressLayout progress = (ProgressLayout)view.findViewById(R.id.container);
		setProgressLayout(progress);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.attach:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityDishList.class)
					.putExtra(ActivityDishDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityDishDetails.ID))
					.putExtra("ATTACH_MODE",true), FragmentDishEdit.DISH_EDITED);
				return true;
			case R.id.create:
				startActivityForResult(new Intent(getActivity().getApplicationContext(), ActivityDishEdit.class), FragmentDishEdit.DISH_EDITED);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
				return new BindingResources()
				.put("CurrencyConverter", new com.enterlib.converters.CurrencyConverter(getString(R.string.currency)))
				.put("DateConverter", new com.enterlib.converters.DateToStringConverter())
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
				.put("TimeConverter", new com.enterlib.converters.DateToStringConverter("hh:mm a"));
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		Class<?> factoryCls = (Class<?>)getActivity().getIntent().getSerializableExtra("REPOSITORY_DISH");
		IDishRepository repository;
		if(factoryCls == null && getArguments()!=null){
			factoryCls = (Class<?>)getArguments().getSerializable("REPOSITORY_DISH");
		}

		if(factoryCls!=null){
			try {
				repository=(IDishRepository)((com.enterlib.data.IRepositoryFactory)factoryCls.newInstance()).getInstance(getActivity(), this);
			} catch (java.lang.InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else{
				repository=RepositoryManager.getInstance().getIDishRepository();
		}
		return new ViewModelDishList(this, repository);
	}

	@Override
	protected SearchViewFilterController createFilterController(Bundle savedInstanceState) {
		return new SearchViewFilterController(getActivity(), this, (ListField)getForm().getFieldById(R.id.listView)
			,new StringFilterCondition<Dish>("Name", getActivity().getString(R.string.name)){
				protected boolean eval(String prefix, Dish item){
					return StringUtils.startsWordWith(prefix, item.Name);
				}
			}
			,new SwitchCondition<Dish>(getActivity(),"IsAvailable", getActivity().getString(R.string.isavailable), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Dish item){
					return value == null || item.IsAvailable == value;
				}
			}
			/*,new SpinnerFilterCondition<Dish,Image>(getActivity(),"ImageId", getActivity().getString(R.string.imageid)){
				protected boolean eval(Image value, Dish item){
					return value.Id==0 || value.Id==item.ImageId;
				}
				public void update(){
					ViewModelDishList viewModel = ViewModelDishListgetViewModel();
					setItems(viewModel.getImagesOptional());
				}
			}*/
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
			/*,new SpinnerFilterCondition<Dish,Entity>(getActivity(),"EntityId", getActivity().getString(R.string.entityid)){
				protected boolean eval(Entity value, Dish item){
					return value.Id==0 || value.Id==item.EntityId;
				}
				public void update(){
					ViewModelDishList viewModel = ViewModelDishListgetViewModel();
					setItems(viewModel.getEntitiesOptional());
				}
			}*/
			,new SwitchCondition<Dish>(getActivity(),"InWhatsGood", getActivity().getString(R.string.inwhatsgood), getActivity().getString(R.string.bool_true), getActivity().getString(R.string.bool_false), true ){
				protected boolean eval(Boolean value, Dish item){
					return value == null || item.InWhatsGood == value;
				}
			}
			,new StringFilterCondition<Dish>("Tags", getActivity().getString(R.string.tags)){
				protected boolean eval(String prefix, Dish item){
					return StringUtils.startsWordWith(prefix, item.Tags);
				}
			}
			/*,new SpinnerFilterCondition<Dish,EntityMenu>(getActivity(),"MenuId", getActivity().getString(R.string.menuid)){
				protected boolean eval(EntityMenu value, Dish item){
					return value.Id==0 || value.Id==item.MenuId;
				}
				public void update(){
					ViewModelDishList viewModel = ViewModelDishListgetViewModel();
					setItems(viewModel.getEntityMenusOptional());
				}
			}*/
			/*,new SpinnerFilterCondition<Dish,User>(getActivity(),"UpdateUserId", getActivity().getString(R.string.updateuserid)){
				protected boolean eval(User value, Dish item){
					return value.Id==0 || value.Id==item.UpdateUserId;
				}
				public void update(){
					ViewModelDishList viewModel = ViewModelDishListgetViewModel();
					setItems(viewModel.getUsersOptional());
				}
			}*/
		);
	}
	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		if(requestCode == EntityCursorViewModel.ENTITY_DETAILS){
			if(getActivity().getIntent().getBooleanExtra("ATTACH_MODE", false)){
				attach(data);
				return;
			}
			Intent i =new  Intent(getActivity().getApplicationContext(), ActivityDishDetails.class);
			i.putExtra(ActivityDishDetails.ID, new int[]{((Dish)data).Id});
			i.putExtra(ActivityEntityDetails.ID, (int[]) getActivity().getIntent().getSerializableExtra(ActivityEntityDetails.ID));
			startActivityForResult(i,FragmentDishEdit.DISH_EDITED);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentDishEdit.DISH_EDITED){
			load();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onAttached(Object item, Exception exception) {
		if(exception !=null)
			return;
		getActivity().setResult(FragmentDishEdit.DISH_EDITED);
		getActivity().finish();
	}

	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentDishEdit.DISH_EDITED);
		load();
	}

}
