package id.co.bri.brimo.ui.activities.bripoin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.Bind;
import butterknife.ButterKnife;
import id.co.bri.brimo.contract.IPresenter.bripoin.IBripoinPresenter;
import id.co.bri.brimo.domain.helpers.GeneralHelper;
import id.co.bri.brimo.models.apimodel.response.BripoinWebviewRes;
import id.co.bri.brimo.ui.activities.base.BaseActivity;
import id.co.bri.brimo.ui.activities.bripoin.BripoinActivity$;
import id.co.bri.brimo.ui.customviews.dialog.DialogExitCeria;
import lpdti.m;
/* loaded from: /tmp/jadx-51380123185755461.dex */
public class BripoinActivity extends BaseActivity implements DialogExitCeria.DialogCeriaListener {
    private static BripoinWebviewRes response;
    @Bind({2131363245})
    ImageView ivClose;
    @Bind({2131364225})
    LinearLayout lyLoading;
    IBripoinPresenter<Object> presenter;
    @Bind({2131365262})
    Toolbar toolbar;
    @Bind({2131366732})
    WebView webView;
    private int second = 1000;
    private boolean isFinished = false;

    private void clearWebView() {
        this.webView.clearCache(true);
        this.webView.clearFormData();
        this.webView.clearHistory();
        this.webView.clearSslPreferences();
        this.webView.clearMatches();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        WebStorage.getInstance().deleteAllData();
    }

    private void initWebView() {
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName(m.a(25469));
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        this.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        this.webView.setWebChromeClient(new 2(this));
        this.webView.getSettings().setCacheMode(2);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.requestFocus();
        this.webView.setVerticalScrollBarEnabled(true);
        String str = m.a(25470) + response.getAccessToken();
        this.webView.setWebViewClient(new 3(this, str));
        this.webView.postUrl(response.getUrl(), str.getBytes());
        clearWebView();
    }

    private void injectDependency() {
        getActivityComponent().inject(this);
        IBripoinPresenter<Object> iBripoinPresenter = this.presenter;
        if (iBripoinPresenter != null) {
            iBripoinPresenter.setView(this);
            this.presenter.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupViews$0(View view) {
        exitWebApp();
    }

    public static void launchIntent(Activity caller, BripoinWebviewRes bripoinWebviewRes) {
        Intent intent = new Intent(caller, BripoinActivity.class);
        response = bripoinWebviewRes;
        caller.startActivityForResult(intent, 108);
    }

    private void setTextTimer(int timer) {
        new 1(this, this.second * timer, this.second).start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupViews() {
        GeneralHelper.setToolbarNoIconBack(this, this.toolbar, GeneralHelper.getString(2132019888));
        this.ivClose.setOnClickListener(new BripoinActivity$.ExternalSyntheticLambda0(this));
        setTextTimer(30);
        initWebView();
    }

    public void exitWebApp() {
        DialogExitCeria dialogExitCeria = new DialogExitCeria(this, m.a(25471), m.a(25472));
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.add(dialogExitCeria, (String) null);
        beginTransaction.commitAllowingStateLoss();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super/*androidx.fragment.app.FragmentActivity*/.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 114) {
            exitWebApp();
        }
    }

    public void onBackPressed() {
        exitWebApp();
    }

    public void onClickNo() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onClickYes() {
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131558448);
        ButterKnife.bind(this);
        injectDependency();
        setupViews();
    }
}
