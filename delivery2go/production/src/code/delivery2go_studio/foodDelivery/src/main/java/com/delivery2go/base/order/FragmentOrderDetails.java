package com.delivery2go.base.order;

import android.content.res.Resources;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.delivery2go.OrderStateEnum;
import com.enterlib.converters.CurrencyConverter;
import com.enterlib.converters.DateToStringConverter;
import com.enterlib.converters.IValueConverter;
import com.enterlib.exceptions.ConversionFailException;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.BindableFragment;
import com.enterlib.databinding.BindingResources;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.order.ViewModelOrderDetails;
import com.delivery2go.R;
import com.delivery2go.base.AccessAnnotation;
import com.delivery2go.base.Access;
import com.delivery2go.base.Rolls;


@AccessAnnotation(allows={Rolls.Administrator, Rolls.Order})
public class FragmentOrderDetails extends BindableFragment {
	private Menu menu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_order, menu);
		this.menu = menu;
//			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Order}, Access.DELETE)){
//				menu.findItem(R.id.delete).setEnabled(false);
//				menu.findItem(R.id.delete).setVisible(false);
//			}
			if(!Access.hasAccess(new String[]{Rolls.Administrator, Rolls.Order}, Access.WRITE)){
				menu.findItem(R.id.edit).setEnabled(false);
				menu.findItem(R.id.edit).setVisible(false);
			}else{
                if(getViewModel() instanceof ViewModelOrderDetails ) {
                    ViewModelOrderDetails vm = (ViewModelOrderDetails) getViewModel();
                    Order order = vm.getItem();
                    if(order!=null) {
                        MenuItem menuItem = menu.findItem(R.id.edit);
                        if (menuItem.isEnabled()) {
                            if (order.OrderStateId == OrderStateEnum.Open) {
                                menuItem.setEnabled(false);
                                menuItem.setVisible(false);
                            } else {
                                menuItem.setEnabled(true);
                                menuItem.setVisible(true);
                            }
                        }
                    }
                }
            }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gen_order_fragment_details, container, false);
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.refresh:
				load();
				return true;
			case R.id.edit:
				startActivityForResult(new  Intent(getActivity().getApplicationContext(), ActivityOrderEdit.class).putExtra(ActivityOrderDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDetails.ID)), FragmentOrderEdit.ORDER_EDITED);
				return true;
			case R.id.delete:
				com.enterlib.DialogUtil.showAlertDialog(getActivity(), getString(R.string.dialog_delete_title), new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog, int which) {
						getViewModel().delete(getString(R.string.deleting), FragmentOrderDetails.this);
					}
				},null);
				return true;
			case R.id.attach:
				startActivity(new  Intent(getActivity().getApplicationContext(), ActivityOrderList.class).putExtra(ActivityOrderDetails.ID,(int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDetails.ID)));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected BindingResources getBindingResources() {
		return super.getBindingResources()
				.put("DateConverter", new DateToStringConverter("dd/MM/yyyy HH:mm"))
				.put("CurrencyConverter", new CurrencyConverter(getString(R.string.currency)))
				.put("MinutesConverter", new IValueConverter() {
					@Override
					public Object convert(Object value) throws ConversionFailException {
						Integer minutes = (Integer) value;
						if(minutes == null)
							return "unspecified";

						int h = minutes / 60;
						int m = minutes % 60;
						return String.format("%02d:%02d",h,m);
					}

					@Override
					public Object convertBack(Object value) throws ConversionFailException {
						return null;
					}
				})
				.put("StateToColorConverter", new IValueConverter() {
					@Override
					public Object convert(Object value) throws ConversionFailException {
						int stateId = (Integer) value;
						Resources res = getResources();
						int color;
						switch (stateId) {
							case OrderStateEnum.Open:
								color = res.getColor(R.color.order_open);
								break;
							case OrderStateEnum.Submited:
								color = res.getColor(R.color.order_sumbited);
								break;
							case OrderStateEnum.Ready:
								color = res.getColor(R.color.order_ready);
								break;
							case OrderStateEnum.OnTheWay:
								color = res.getColor(R.color.order_on_the_way);
								break;
							case OrderStateEnum.Delivered:
								color = res.getColor(R.color.order_delivered);
								break;
							case OrderStateEnum.Cancelled:
								color = res.getColor(R.color.order_cancelled);
								break;
							case OrderStateEnum.Unavailable:
								color = res.getColor(R.color.order_cancelled);
								break;
							case OrderStateEnum.Arrived:
								color = res.getColor(R.color.order_arrived);
								break;
							default:
								color = 0xFFFFFFFF;
						}

						return color;
					}

					@Override
					public Object convertBack(Object value) throws ConversionFailException {
						return null;
					}
				});
	}

	@Override
	protected DataViewModel createViewModel(Bundle savedInstanceState) {
		return new ViewModelOrderDetails(this, RepositoryManager.getInstance().getIOrderRepository(), (int[])getActivity().getIntent().getSerializableExtra(ActivityOrderDetails.ID));
	}

	@Override
	public void navigateTo(int requestCode, Bundle extras, Object data) {
		switch (requestCode) {
			case ViewModelOrderDetails.ENTITY:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.entity.ActivityEntityDetails.class).putExtra(com.delivery2go.base.entity.ActivityEntityDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDetails.CLIENT:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.client.ActivityClientDetails.class).putExtra(com.delivery2go.base.client.ActivityClientDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDetails.ORDERSTATE:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.orderstate.ActivityOrderStateDetails.class).putExtra(com.delivery2go.base.orderstate.ActivityOrderStateDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDetails.ORDERTYPE:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.ordertype.ActivityOrderTypeDetails.class).putExtra(com.delivery2go.base.ordertype.ActivityOrderTypeDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDetails.CLIENTSIGNATURE:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.image.ActivityImageDetails.class).putExtra(com.delivery2go.base.image.ActivityImageDetails.ID, new int[]{(Integer)data}));
				break;
			case ViewModelOrderDetails.UPDATEUSER:
				startActivity(new  Intent(getActivity(), com.delivery2go.base.user.ActivityUserDetails.class).putExtra(com.delivery2go.base.user.ActivityUserDetails.ID, new int[]{(Integer)data}));
				break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FragmentOrderEdit.ORDER_EDITED){
			load();
			getActivity().setResult(FragmentOrderEdit.ORDER_EDITED);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onDeleted(Object data) {
		getActivity().setResult(FragmentOrderEdit.ORDER_EDITED);
		super.onDeleted(data);
	}

	@Override
	public void onDataLoaded() {
		super.onDataLoaded();

        if(menu!=null && getViewModel() instanceof ViewModelOrderDetails ) {
            ViewModelOrderDetails vm = (ViewModelOrderDetails) getViewModel();
            Order order = vm.getItem();

            MenuItem menuItem = menu.findItem(R.id.edit);
            if (menuItem.isEnabled()) {
                if (order.OrderStateId == OrderStateEnum.Open) {
                    menuItem.setEnabled(false);
                    menuItem.setVisible(false);
                } else {
                    menuItem.setEnabled(true);
                    menuItem.setVisible(true);
                }
            }
        }

	}
}
