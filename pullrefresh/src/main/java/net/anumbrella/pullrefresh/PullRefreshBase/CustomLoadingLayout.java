package net.anumbrella.pullrefresh.PullRefreshBase;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import net.anumbrella.pullrefresh.R;

/**
 * author：anumbrella
 * Date:16/7/19 上午11:32
 */
public abstract class CustomLoadingLayout extends LoadingLayout {


    /**
     * LoadingLayout容器
     */
    protected ViewGroup mContainer;


    public CustomLoadingLayout(Context context) {
        super(context);
        init();
    }

    public CustomLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContainer = (ViewGroup) findViewById(R.id.pull_refresh_loadinglayout_content);
    }


    @Override
    public int getContentSize() {
        if (null != mContainer) {
            return mContainer.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * 60);
    }

}
