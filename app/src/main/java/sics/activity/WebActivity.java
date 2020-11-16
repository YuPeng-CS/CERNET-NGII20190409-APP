package sics.activity;

import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import sics.cling.R;

public class WebActivity extends BaseActivity {

    @BindView(R.id.web_web)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int urlId = this.getIntent().getIntExtra("url", R.string.url_heart);
        String str = this.getString(urlId);
        webView.loadUrl(str);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }
}