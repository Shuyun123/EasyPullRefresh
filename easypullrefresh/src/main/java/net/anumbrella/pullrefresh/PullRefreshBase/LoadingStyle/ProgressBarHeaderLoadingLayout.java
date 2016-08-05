package net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import net.anumbrella.pullrefresh.PullRefreshBase.CustomLoadingLayout;
import net.anumbrella.pullrefresh.R;

/**
 * author：anumbrella
 * Date:16/8/3 上午10:40
 */
public class ProgressBarHeaderLoadingLayout extends CustomLoadingLayout {


    /**
     * @seehttps://github.com/lsjwzh/MaterialLoadingProgressBar
     */
    private CircleProgressBar progressBar;


    /**
     * 下拉顶部显示图片
     */
    private ImageView mIcon;

    public ProgressBarHeaderLoadingLayout(Context context) {
        super(context);
        init(context);
    }


    public ProgressBarHeaderLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        progressBar = (CircleProgressBar) findViewById(R.id.PullRefreshProgressBar);
        mIcon = (ImageView) findViewById(R.id.pull_icon);
    }


    @Override
    public int getContentSize() {
        if (null != mContainer) {
            return mContainer.getHeight() + mIcon.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * 60);
    }

    @Override
    public CircleProgressBar getProgressBar() {
        return progressBar;
    }

    public void setColorSchemeResources(int... colorResIds) {
        progressBar.setColorSchemeResources(colorResIds);
    }

    public void setColorSchemeColors(int... colors) {
        progressBar.setColorSchemeColors(colors);
    }

    public void setShowArrow(boolean showArrow) {
        progressBar.setShowArrow(showArrow);
    }

    public void setShowProgressText(boolean mIfDrawText) {
        progressBar.setShowProgressText(mIfDrawText);
    }

    public void setMax(int max) {
        progressBar.setMax(max);
    }


    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }

    public void setCircleBackgroundEnabled(boolean enableCircleBackground) {
       progressBar.setCircleBackgroundEnabled(enableCircleBackground);
    }

    public void setBackgroundColor(int colorRes) {
        progressBar.setBackgroundColor(colorRes);
    }

    @Override
    public ImageView getIcon() {
        return mIcon;
    }


    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_refresh_progressbar_header, null);
        return container;
    }
}
