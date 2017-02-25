package net.anumbrella.easypullrefresh.Activity.WebView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.anumbrella.easypullrefresh.R;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshWebView;

import java.util.Random;

/**
 * author：anumbrella
 * Date:16/8/3 上午9:24
 */
public class PullRefreshWebViewActivity extends AppCompatActivity implements PullRefreshBase.OnRefreshListener<WebView> {


    private PullRefreshWebView mPullWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mPullWebView = (PullRefreshWebView) findViewById(R.id.PullRefreshWebView);
        mPullWebView.setOnRefreshListener(this);
        mPullWebView.setPullLoadEnabled(true);
        mPullWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPullWebView.onPullDownRefreshComplete();

            }
        });
        mPullWebView.showProgress();
        onPullDownRefresh(mPullWebView);
    }


    @Override
    public void onPullDownRefresh(PullRefreshBase<WebView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadUrl();
            }
        },1000);
    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<WebView> refreshView) {



    }


    private void loadUrl() {

        String url = "http://www.jianshu.com/";
        mPullWebView.loadUrl(url);
    }
}
