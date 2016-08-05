package net.anumbrella.pullrefresh.Widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import net.anumbrella.pullrefresh.PullRefreshBase.LoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.R;
import net.anumbrella.pullrefresh.Utils.BaseUtils;
import net.anumbrella.pullrefresh.Utils.PreferenceUtils;

/**
 * author：anumbrella
 * Date:16/7/18 下午6:58
 */
public class PullRefreshWebView extends PullRefreshBase<WebView> {


    private WebView mWebView;


    private static ViewGroup progressView = null;


    public PullRefreshWebView(Context context) {
        super(context);
    }


    public PullRefreshWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }


    public PullRefreshWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected WebView createRefreshableView(Context context, AttributeSet attrs) {
        mWebView = new WebView(context);
        return mWebView;
    }


    @Override
    protected boolean isReadyForPullDown() {
        return mRefreshableView.getScrollY() == 0;

    }


    public void setWebViewClient(WebViewClient webViewClient) {
        if (webViewClient != null && mWebView != null) {
            mWebView.setWebViewClient(webViewClient);
        }
    }

    private void hideAllViews() {
        if (mRefreshableViewWrapper.getChildCount() > 0) {
            mWebView.setVisibility(GONE);
            progressView.setVisibility(GONE);
        }
    }


    public void showProgress() {
        if (progressView != null) {
            hideAllViews();
            progressView.setVisibility(VISIBLE);
        }
    }


    private void showWebView() {
        hideAllViews();
        mWebView.setVisibility(VISIBLE);
    }


    @Override
    public void onPullDownRefreshComplete() {
        super.onPullDownRefreshComplete();
        showWebView();
    }


    @Override
    protected void addRefreshableView(Context context, WebView webView) {
        super.addRefreshableView(context, webView);
        progressView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_progress, null);
        progressView.setId(R.id.recycleview_progress);
        progressView.setVisibility(GONE);
        mRefreshableViewWrapper.addView(progressView);
    }

    /**
     * 加载url
     *
     * @param url
     */
    public void loadUrl(String url) {
        if (url == null) {
            throw new RuntimeException("url not is null");
        }

        if (!BaseUtils.isUrl(url)) {
            throw new RuntimeException("url is incorrect");
        }

        mWebView.loadUrl(url);
    }


    @Override
    protected boolean isReadyForPullUp() {
        float exactContentHeight = (float) Math.floor(mRefreshableView.getContentHeight() * mRefreshableView.getScale());
        return mRefreshableView.getScrollY() >= (exactContentHeight - mRefreshableView.getHeight());
    }


    /**
     * 设置Header样式
     *
     * @param layout
     */
    public void setHeaderLayout(LoadingLayout layout) {
        super.setHeaderLoadingLayout(getContext(), layout);
    }

    /**
     * 设置Footer样式
     *
     * @param layout
     */
    public void setFooterLayout(LoadingLayout layout) {
        super.setFooterLoadingLayout(getContext(), layout);
    }


    /**
     * 设置图标是否可以显示
     *
     * @param value
     */
    public void setIconVisibility(boolean value) {
        if (getHeaderLoadingLayout().getIcon() != null) {
            if (value) {
                getHeaderLoadingLayout().getIcon().setVisibility(VISIBLE);
            } else {
                getHeaderLoadingLayout().getIcon().setVisibility(GONE);
            }
        }
    }

    /**
     * 设置图片
     *
     * @param imageView
     */
    public void setIconImage(int imageView) {
        if (getHeaderLoadingLayout().getIcon() != null) {
            if (imageView != -1) {
                ((ImageView) getHeaderLoadingLayout().getIcon()).setImageResource(imageView);
            }
        }
    }


    /**
     * 设置时间显示
     *
     * @param friendlyTime
     */
    public void setFriendlyTime(boolean friendlyTime) {
        PreferenceUtils.write(getContext(), BaseUtils.Md5(getClass().getName()), getClass().getName(), friendlyTime);
        if (friendlyTime) {
            updateDisplayTime();
        } else {
            updateDisplayTime();
        }
    }


    /**
     * 下拉刷新显示中是否展示时间
     *
     * @param value
     */
    public void setDisplayTime(boolean value) {
        if (getHeaderLoadingLayout().getDisplayTimeLayout() != null) {
            if (value) {
                getHeaderLoadingLayout().getDisplayTimeLayout().setVisibility(VISIBLE);
            } else {
                getHeaderLoadingLayout().getDisplayTimeLayout().setVisibility(GONE);
            }
        }
    }


    @Override
    protected void updateDisplayTime() {
        long time = System.currentTimeMillis();
        if (PreferenceUtils.readBoolean(getContext(), BaseUtils.Md5(getClass().getName()), getClass().getName(), true)) {
            setLastUpdatedLabel(BaseUtils.friendlyTime(time));
        } else {
            setLastUpdatedLabel(BaseUtils.formatDateTime(time));
        }
    }



}
