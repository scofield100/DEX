package com.delivery2go.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delivery2go.AutoCompleteResult;
import com.delivery2go.DeliveryApp;
import com.delivery2go.OrderStateEnum;
import com.delivery2go.R;
import com.delivery2go.base.dish.FragmentDishEdit;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.DeliveryTarif;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.order.ActivityOrderDetails;
import com.enterlib.DialogUtil;
import com.enterlib.StringUtils;
import com.enterlib.app.UIUtils;
import com.enterlib.converters.CurrencyConverter;
import com.enterlib.databinding.BindingResources;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.EditView;
import com.google.android.gms.maps.model.LatLng;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;

/**
 * Created by ansel on 17/08/2016.
 */
public class FragmentOrderDetails extends com.delivery2go.base.order.FragmentOrderDetails implements View.OnClickListener {

    OnOrderChangeListener orderChangeListener;
    private RelativeLayout payPalContainer;
    private boolean _paypalLibraryInit;
    private PayPal pp;
   // private CheckoutButton launchPayPalButton;
    Button payButton;
    private View findAddressPanel;
    private AutoCompleteTextView addressBox;
    private TextView status;
    AutoCompleteResult selectedResult;
    private AddressAutocompleteAdapter mAdapter;
    private DeliveryTarif tarif;
    private Button btnEnterAddress;

    public void initLibrary() {
        pp = PayPal.getInstance();

        if (pp == null) {  // Test to see if the library is already initialized

            // This main initialization call takes your Context, AppID, and target server
            pp = PayPal.initWithAppID(getActivity(), "APP-80W284485P519543T", DeliveryApp.AUTOCOMPLETE_OFFLINE ?
                    PayPal.ENV_NONE :
                    PayPal.ENV_SANDBOX);

            // Required settings:

            // Set the language for the library
            pp.setLanguage("en_US");

            // Some Optional settings:

            // Sets who pays any transaction fees. Possible values are:
            // FEEPAYER_SENDER, FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and FEEPAYER_SECONDARYONLY
            //pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);

            // true = transaction requires shipping
            //pp.setShippingEnabled(true);

            _paypalLibraryInit = true;
        }
    }

    private void addPayPalButton(){
//        launchPayPalButton = pp.getCheckoutButton(getActivity(), PayPal.BUTTON_278x43, CheckoutButton.TEXT_PAY);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//
//        launchPayPalButton.setLayoutParams(params);
//
//        launchPayPalButton.setOnClickListener(this);
//
//        payPalContainer.removeAllViews();
//        payPalContainer.addView(launchPayPalButton);
    }

    public void enableSubmit(boolean value){
        //launchPayPalButton.setEnabled(value);
        payButton.setEnabled(value);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        orderChangeListener  = (OnOrderChangeListener) activity;
    }

    @Override
    public void onDetach() {
        orderChangeListener = null;

        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment_details, container, false);
        payPalContainer = (RelativeLayout)view.findViewById(R.id.payPalContainer);
        payButton = (Button)view.findViewById(R.id.payButton);

        findAddressPanel = view.findViewById(R.id.findAddressPanel);
        addressBox = (AutoCompleteTextView) view.findViewById(R.id.addressBox);

        Button addressCancel = (Button) view.findViewById(R.id.addressCancel);
        Button addressAccept = (Button)view.findViewById(R.id.addressAccept);
        btnEnterAddress = (Button)view.findViewById(R.id.btnEnterAddress);
        status = (TextView) view.findViewById(R.id.status);

        btnEnterAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAddressPanel.setVisibility(View.VISIBLE);
            }
        });

        addressCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAddressPanel.setVisibility(View.GONE);
            }
        });

        addressAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedResult == null){
                    DialogUtil.showErrorDialog(getActivity(), getString(R.string.validation_select_street_location));
                    return;
                }
                else if(tarif == null){
                    DialogUtil.showErrorDialog(getActivity(), getString(R.string.status_delivery_charge_not_found));
                    return;
                }

                ViewModelOrderDetails vm = (ViewModelOrderDetails) getViewModel();
                vm.setDeliveryTarif(tarif, selectedResult);

                btnEnterAddress.setText(getString(R.string.street_address) + "\r\n" + selectedResult.getDescription());
                findAddressPanel.setVisibility(View.GONE);
            }
        });

        addressBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                selectedResult = null;
                status.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                selectedResult = null;
                status.setText("");
            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addressBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedResult = mAdapter.getItem(position);
                ViewModelOrderDetails vm = (ViewModelOrderDetails) getViewModel();
                tarif = vm.getDeliveryTarif(selectedResult.getLatLng());
                if(tarif != null){
                    status.setText(getString(R.string.status_delivery_price)+ StringUtils.parseCurrency(tarif.Price));
                }else{
                    status.setText(R.string.status_delivery_charge_not_found);
                }
            }
        });

        mAdapter = new AddressAutocompleteAdapter(getActivity());
        addressBox.setAdapter(mAdapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected BindingResources getBindingResources() {
        return super.getBindingResources()
                .put("CurrencyConverter", new CurrencyConverter(getActivity().getString(R.string.currency)));
    }


    @Override
    protected DataViewModel createViewModel(Bundle savedInstanceState) {
        int orderId = getArguments().getInt(ActivityOrderDetails.ID);

       // initLibrary();

        //addPayPalButton();

        return new ViewModelOrderDetails(this, this, orderChangeListener, orderId) ;
    }


    @Override
    public void onDataLoaded() {
        super.onDataLoaded();

        ViewModelOrderDetails vm = (ViewModelOrderDetails) getViewModel();
        Order o = vm.getOrder();
        if(o.OrderStateId != OrderStateEnum.Open){
            enableSubmit(false);
           // launchPayPalButton.setOnClickListener(null);
        }

        Client client = vm.getClient();
        if(!StringUtils.isNullOrWhitespace(client.AddressStreet) && client.Lat!=null && client.Lng!=null){
            selectedResult = new AutoCompleteResult(client.AddressStreet, AutoCompleteResult.TYPE_ADDRESS, new LatLng(client.Lat, client.Lng));
            tarif =  vm.getDeliveryTarif(selectedResult.getLatLng());
            if(tarif!=null) {
                vm.setDeliveryTarif(tarif, selectedResult);
                status.setText(getString(R.string.status_delivery_price) + StringUtils.parseCurrency(tarif.Price));
            }else{
                status.setText("");
            }

            if(!StringUtils.isNullOrWhitespace(client.AdressNo)) {
                ((EditText) getView().findViewById(R.id.deliveryadress)).setText(client.AdressNo);
            }
            addressBox.setText(client.AddressStreet);
            btnEnterAddress.setText(getString(R.string.street_address) + "\r\n" + client.AddressStreet);

//            int splitIndex = selectedResult.getDescription().indexOf(',');
//            if(splitIndex > 0){
//                String address_number = selectedResult.getDescription().substring(0, splitIndex);
//                ((EditText) getView().findViewById(R.id.deliveryadress)).setText(address_number);
//
//                if((splitIndex + 1) < selectedResult.getDescription().length()) {
//                    String street_location = selectedResult.getDescription().substring(splitIndex + 1);
//                    addressBox.setText(street_location);
//                    btnEnterAddress.setText(getString(R.string.street_address) + "\r\n" + street_location);
//                }
//            }else {
//                addressBox.setText(selectedResult.getDescription());
//                btnEnterAddress.setText(getString(R.string.street_address) + "\r\n" + selectedResult.getDescription());
//            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FragmentDishEdit.DISH_EDITED && resultCode == Activity.RESULT_OK){
            load();
            getActivity().setResult(Activity.RESULT_OK);

            if(orderChangeListener!=null){
                orderChangeListener.onOrderChanged();
            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        //addPayPalButton();

        ViewModelOrderDetails vm = (ViewModelOrderDetails) getViewModel();
        vm.Submit.invoke(null, null);
    }



}
