package com.delivery2go.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;

import com.delivery2go.AutoCompleteResult;
import com.delivery2go.DeliveryApp;
import com.delivery2go.OrderStateEnum;
import com.delivery2go.R;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.repository.IClientRepository;
import com.delivery2go.base.data.repository.IDishRepository;
import com.delivery2go.base.data.repository.IOrderDishRepository;
import com.delivery2go.base.data.repository.IOrderRepository;
import com.delivery2go.base.dish.ActivityDishDetails;
import com.delivery2go.base.dish.FragmentDishEdit;
import com.delivery2go.base.models.Client;
import com.delivery2go.base.models.DeliveryAgent;
import com.delivery2go.base.models.DeliveryTarif;
import com.delivery2go.base.models.Dish;
import com.delivery2go.base.models.Entity;
import com.delivery2go.base.models.Order;
import com.delivery2go.base.models.OrderDish;
import com.delivery2go.base.orderdish.ActivityOrderDishDetails;
import com.delivery2go.entity.ActivityOrderDish;
import com.enterlib.DialogUtil;
import com.enterlib.StringUtils;
import com.enterlib.app.UIUtils;
import com.enterlib.exceptions.BusinessException;
import com.enterlib.fields.Field;
import com.enterlib.fields.Form;
import com.enterlib.mvvm.Command;
import com.enterlib.mvvm.DataViewModel;
import com.enterlib.mvvm.IAsyncLoadOperation;
import com.enterlib.mvvm.IDataView;
import com.google.android.gms.maps.model.LatLng;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ansel on 17/08/2016.
 */
public class ViewModelOrderDetails extends DataViewModel {

    private static final int PAY_PAL_REQUEST = 479531;
    private static final int WEB_PAYMENT_FORM = 9674543;
    private final int orderId;
    private final IOrderRepository orderRep;
    private final IOrderDishRepository dishOrderRep;
    private final OnOrderChangeListener orderChangeListener;
    private final FragmentOrderDetails context;
    private Order order;
    private Entity entity;
    private DeliveryAgent deliveryAgent;
    private ArrayList<DeliveryTarif> tarifs;
    private DeliveryTarif deliveryTarif;

    public String AdressNumber;
    private AutoCompleteResult address;
    private Client client;

    public ViewModelOrderDetails(FragmentOrderDetails context, IDataView view, OnOrderChangeListener orderChangeListener, int orderId) {
        super(view);

        this.context = context;
        this.orderId = orderId;
        this.orderRep = RepositoryManager.getInstance().getIOrderRepository();
        this.dishOrderRep = RepositoryManager.getInstance().getIOrderDishRepository();
        this.orderChangeListener = orderChangeListener;
    }

    public Order getOrder(){
        return  order;
    }

    public Entity getEntity() {
        return entity;
    }

    public Client getClient() {
        return client;
    }

    @Override
    protected boolean loadAsync() throws Exception {
        order = orderRep.get(orderId);
        entity = order.getEntity();

        deliveryAgent = RepositoryManager.getInstance().getEntityContext().get(DeliveryAgent.class, DeliveryApp.DELIVERY_ACOUNT_ID);
        if(deliveryAgent == null){
            throw  new BusinessException("The Delivery Agent is not registered");
        }

        tarifs = deliveryAgent.getDeliveryTarifs().toList();
        if(tarifs == null || tarifs.size() ==0){
            throw  new BusinessException("The Delivery Agent does not have delivery tariff");
        }

        for (OrderDish item : order.getOrderDishs().load()){
            item.getOrderDishAddonses().load();
            item.getDressing();
        }

        client = DeliveryApp.getServerClient();

        return true;
    }

    @Override
    protected void onLoaded(Exception workException) {
        super.onLoaded(workException);

        if(order.OrderStateId != OrderStateEnum.Open ){
            Submit.setEnabled(false);
            Cancel.setEnabled(false);
        }
    }

    public Command Submit = new Command() {
        @Override
        public void invoke(Object invocator, Object args) {
            if(order == null)
                return;

            Form form = context.getForm();

            Field phoneField = form.getFieldByBinding("Phone");
            boolean valid = true;
            if(!phoneField.validate()) {
                valid = false;
            }else {
                phoneField.updateSource(order);
            }

            Field addressField = form.getFieldByBinding("AdressNumber");
            if(!addressField.validate()) {
                valid = false;
            }else {
                addressField.updateSource(ViewModelOrderDetails.this);
            }

            if(address == null){
                DialogUtil.showErrorDialog(context.getActivity(), context.getString(R.string.validation_select_street_location));
                valid = false;
            }
            else if(tarifs == null){
                DialogUtil.showErrorDialog(context.getActivity(), context.getString(R.string.status_delivery_charge_not_found));
                valid = false;
            }

            if(valid) {
                if(!StringUtils.isNullOrWhitespace(AdressNumber))
                    order.DeliveryAdress = AdressNumber +", "+address.getDescription();
                else
                    order.DeliveryAdress = address.getDescription();

                order.DeliveryLat = address.getLatLng().latitude;
                order.DeliveryLng= address.getLatLng().longitude;

                doLoadOperationAsync(new IAsyncLoadOperation() {
                    @Override
                    public boolean loadAsync() throws Exception {
                        orderRep.update(order);
                        return true;
                    }

                    @Override
                    public void onDataLoaded() {
                        Intent i = new Intent(context.getActivity().getApplicationContext(), ActivityWebPayment.class)
                                .putExtra(ActivityWebPayment.ORDER_ID, order.OrderId);
                        context.startActivityForResult(i, WEB_PAYMENT_FORM);
                    }
                });
                
                //startPayPalActivity();
            }
        }
    };


    private void startPayPalActivity(){

        if(StringUtils.isNullOrWhitespace(deliveryAgent.Account)) {
            getView().onFailure(new BusinessException(context.getString(R.string.error_account_misssing)));
            return;
        }

        // Create a basic PayPal payment
        PayPalPayment payment = new PayPalPayment();

        // Set the currency type
        payment.setCurrencyType("USD");

        // Set the recipient for the payment (can be a phone number)
        payment.setRecipient(deliveryAgent.Account);
        payment.setMerchantName(deliveryAgent.Name);

        // Set the payment amount, excluding tax and shipping costs
        payment.setSubtotal(new BigDecimal(order.getTotalPayment()));

        // Set the payment type--his can be PAYMENT_TYPE_GOODS,
        // PAYMENT_TYPE_SERVICE, PAYMENT_TYPE_PERSONAL, or PAYMENT_TYPE_NONE
        payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);

        // PayPalInvoiceData can contain tax and shipping amounts, and an
        // ArrayList of PayPalInvoiceItem that you can fill out.
        // These are not required for any transaction.
        //PayPalInvoiceData invoice = new PayPalInvoiceData();

        // Set the tax amount
       // invoice.setTax(new BigDecimal(order.TaxAmount));
        //invoice.setShipping(new BigDecimal(order.DeliveryCharge));

        //payment.setInvoiceData(invoice);
        payment.setMemo(String.format("Delivery to:%s", order.DeliveryAdress));

        Intent paypalIntent = PayPal.getInstance().checkout(payment, context.getActivity());
        context.startActivityForResult(paypalIntent, PAY_PAL_REQUEST);

    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PAY_PAL_REQUEST){
            if(resultCode == Activity.RESULT_OK) {
                final String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
                onPaymentReceived(payKey);
            }else if(resultCode == PayPalActivity.RESULT_FAILURE){
                String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
                String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
                getView().onFailure(new BusinessException(String.format("Payment failure\r\n. ErrorCode:%s\r\nMessage:%s\r\n",errorID, errorMessage)));
            }
        } else if(requestCode == WEB_PAYMENT_FORM){
            if(resultCode == Activity.RESULT_OK){
                final String paymentREf = data.getStringExtra(ActivityWebPayment.PAYMENT_REF);
                onPaymentReceived(paymentREf);
            }
        }else {
            super.onResult(requestCode, resultCode, data);
        }
    }

    public void onPaymentReceived(final String paymentRef){
        if(StringUtils.isNullOrWhitespace(paymentRef)){
            getView().onFailure(new BusinessException("An error has ocurred"));
            return;
        }

        doLoadOperationAsync(new IAsyncLoadOperation() {
            @Override
            public boolean loadAsync() throws Exception {

                //validates the order
                order.OrderStateId = OrderStateEnum.Submited;
                order.PaymentRef = paymentRef;
                order.UpdateDate = new Date();

                boolean clientChange = false;

                if(StringUtils.isNullOrWhitespace(client.Mobile)) {
                    client.Mobile = order.Phone;
                    clientChange = true;
                }

                if(StringUtils.isNullOrWhitespace(client.Phone)){
                    client.Phone = order.Phone;
                    clientChange = true;
                }

                if(StringUtils.isNullOrWhitespace(client.AdressNo)){
                    client.AdressNo = AdressNumber;
                    clientChange = true;
                }

                if(StringUtils.isNullOrWhitespace(client.AddressStreet)){
                    client.AddressStreet = address.getDescription();
                    client.Lat = order.DeliveryLat;
                    client.Lng = order.DeliveryLng;
                    clientChange = true;
                }

                if(clientChange){
                    IClientRepository clientRepository = RepositoryManager.getInstance().getIClientRepository();
                    clientRepository.update(client);
                    DeliveryApp.setClient(client);
                    clientRepository.close();
                }

                if (!orderRep.update(order))
                    throw new BusinessException("Error sending the order");

                IDishRepository dishRep = RepositoryManager.getInstance().getIDishRepository();

                //update the dish count
                for (OrderDish item : order.getOrderDishs()){
                    Dish dish = item.getDish();
                    dish.OrderCount++;
                    dishRep.update(dish);
                }

                dishRep.close();

                return true;
            }

            @Override
            public void onDataLoaded() {

                Submit.setEnabled(false);
                Cancel.setEnabled(false);
                context.enableSubmit(false);

                UIUtils.showMessage(context.getActivity(), context.getString(R.string.msg_order_submitted));
                context.getActivity().setResult(Activity.RESULT_OK);

                if (orderChangeListener != null) {
                    orderChangeListener.onOrderChanged();
                    orderChangeListener.onOrderStateChange(OrderStateEnum.Submited);
                }
            }
        });
    }

    public Command Cancel = new Command() {
        @Override
        public void invoke(Object invocator, Object args) {
            if(order == null)
                return;

            DialogUtil.showAlertDialog(context.getActivity(), context.getString(R.string.msg_order_cancel), "",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doLoadOperationAsync(new IAsyncLoadOperation() {
                                @Override
                                public boolean loadAsync() throws Exception {
                                    order.OrderStateId = OrderStateEnum.Cancelled;
                                    if(!orderRep.delete(order))
                                        throw  new BusinessException("Error cancelling the order");
                                    return true;
                                }

                                @Override
                                public void onDataLoaded() {
                                    Submit.setEnabled(false);
                                    Cancel.setEnabled(false);

                                    context.enableSubmit(false);

                                    UIUtils.showMessage(context.getActivity(), context.getString(R.string.msg_order_cancelled));
                                    context.getActivity().setResult(Activity.RESULT_OK);

                                    if (orderChangeListener != null) {
                                        orderChangeListener.onOrderChanged();
                                        orderChangeListener.onOrderStateChange(OrderStateEnum.Cancelled);
                                    }

                                }
                            });
                        }
                    },
                    null);
        }
    };

    public Command EditItem = new Command() {
        @Override
        public void invoke(Object invocator, Object args) {
            if(order.OrderStateId!=OrderStateEnum.Open) {
                DialogUtil.showInfoDialog(context.getActivity(), context.getString(R.string.info), context.getString(R.string.message_order_sealed), null);
                return;
            }

            OrderDish item = (OrderDish) args;
            Intent i =new  Intent(context.getActivity().getApplicationContext(), ActivityOrderDish.class);
            i.putExtra(ActivityDishDetails.ID, new int[]{item.DishId})
                    .putExtra(ActivityOrderDishDetails.ID, item.Id );

            context.startActivityForResult(i, FragmentDishEdit.DISH_EDITED);
        }
    };

    public Command DeleteItem = new Command() {
        @Override
        public void invoke(Object invocator, Object args) {

            if(order.OrderStateId!=OrderStateEnum.Open) {
                DialogUtil.showInfoDialog(context.getActivity(), context.getString(R.string.info), context.getString(R.string.message_order_sealed), null);
                return;
            }

            final OrderDish item = (OrderDish) args;
            AlertDialog.Builder ab = new AlertDialog.Builder(context.getActivity());

            ab.setTitle(context.getString(R.string.msg_confirm_delete_orderdish));
            ab.setMessage(item.DishName);
            ab.setIcon(com.enterlib.R.drawable.ic_action_warning);
            ab.setNegativeButton(com.enterlib.R.string.cancel, null);
            ab.setPositiveButton(com.enterlib.R.string.accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    doLoadOperationAsync(new IAsyncLoadOperation() {
                        @Override
                        public boolean loadAsync() throws Exception {
                            dishOrderRep.delete(item);
                            updateOrder(item);
                            return true;
                        }

                        @Override
                        public void onDataLoaded() {
                            if (orderChangeListener != null) {
                                orderChangeListener.onOrderChanged();
                            }
                            load();
                            context.getActivity().setResult(Activity.RESULT_OK);
                        }
                    });
                }
            });
            ab.show();
        }
    };

    private void updateOrder(OrderDish orderDish){
        Order openOrder = orderDish.getOrder();
        if(entity.DeliveryPrice!=null)
            openOrder.DeliveryCharge = entity.DeliveryPrice;
        else
            openOrder.DeliveryCharge = 0;

        double totalAmount = 0;
        for (OrderDish item : openOrder.getOrderDishs()){
            totalAmount+=item.SubTotal;
        }
        openOrder.getOrderDishs().close();

        openOrder.TaxAmount = 0;
        openOrder.TotalAmount = totalAmount;

        orderRep.update(openOrder);
    }

    public DeliveryTarif getDeliveryTarif(LatLng latLng) {

        if(entity.Lat == null || entity.Lng == null || tarifs == null || tarifs.size() == 0)
            return null;

        float[]result =new float[1];
        Location.distanceBetween(latLng.latitude, latLng.longitude, entity.Lat, entity.Lng, result);
        double distance = Double.valueOf( result[0] / DeliveryApp.MILLE_METERS);

       DeliveryTarif tarif = null;
       for (DeliveryTarif f : tarifs){
            if(f.ToMiles == null){
                if(distance >= f.FromMiles)
                    tarif = f;
            }else if(distance >= f.FromMiles && distance<=f.ToMiles){
                tarif = f;
            }
       }
        return  tarif;
    }

    public void setDeliveryTarif(DeliveryTarif deliveryTarif, AutoCompleteResult selectedResult) {
        this.deliveryTarif = deliveryTarif;
        this.address = selectedResult;

        this.order.DeliveryCharge= deliveryTarif.Price;

        this.order.onPropertyChange("DeliveryCharge");
        this.order.onPropertyChange("TotalPayment");
    }
}
