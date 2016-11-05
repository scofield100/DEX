package com.delivery2go.order;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.delivery2go.R;
import com.delivery2go.base.data.RepositoryManager;
import com.delivery2go.base.data.web.WebRepositoryManager;
import com.enterlib.DialogUtil;

import junit.framework.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class ActivityWebPayment extends Activity {

    public class WebInterface {
        Activity context;
        private ProgressDialog dialog;

        public WebInterface(Activity context){
            this.context = context;
        }

        @JavascriptInterface
        public void showPaymentProgressDialog(){
            if(dialog == null || !dialog.isShowing()) {
                dialog = DialogUtil.getProgressDialog(context, R.string.loading);
                dialog.show();
            }
        }

        @JavascriptInterface
        public void closePaymentProgressDialog(){
            if(dialog!=null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }

        @JavascriptInterface
        public void onAccept(String paymentRef){
            context.setResult(Activity.RESULT_OK, new Intent().putExtra(PAYMENT_REF, paymentRef));
            context.finish();
        }

        @JavascriptInterface
        public void onCancel(){
            context.setResult(Activity.RESULT_CANCELED, new Intent());
            context.finish();
        }
    }

    public static final String ORDER_ID="ORDER_ID";

    public static final String PAYMENT_REF="PEYMENT_REF";

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_payment);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebInterface(this), "Android");

        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        WebRepositoryManager repositoryManager = (WebRepositoryManager) RepositoryManager.getInstance();
        int orderId = getIntent().getIntExtra(ORDER_ID, 0);
        Assert.assertTrue(orderId > 0);

        String urlString = repositoryManager.getBaseUrl();
        try {
            URL url = new URL(urlString);

            String payUrl = String.format("https://%s:44300/PaymentWeb/?orderid=%d", url.getHost(),orderId);
            webView.loadUrl(payUrl);


        } catch (MalformedURLException e) {
            DialogUtil.showErrorDialog(this, e.getLocalizedMessage());
        }

        ActionBar actionBar = getActionBar();
        if(actionBar!=null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setResult(RESULT_CANCELED);
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return  true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_web_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
