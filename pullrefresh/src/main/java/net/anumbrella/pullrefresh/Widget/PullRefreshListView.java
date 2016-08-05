package net.anumbrella.pullrefresh.Widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import net.anumbrella.pullrefresh.PullRefreshBase.ILoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.LoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle.DefaultFooterLoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.R;
import net.anumbrella.pullrefresh.Utils.BaseUtils;
import net.anumbrella.pullrefresh.Utils.PreferenceUtils;

/**
 * author：anumbrella
 * Date:16/7/18 下午6:57
 */
public class PullRefreshListView extends PullRefreshBase<ListView> implements AbsListView.OnScrollListener {

    /**
     * ListView
     */
    private ListView mListView;

    /**
     * 用于滑到底部自动加载的Footer
     */
    private LoadingLayout mLoadMoreFooterLayout;

    private static boolean scrollLoadEnabled = false;

    private static boolean isLastItemVisible = false;

    private static boolean pullLoadEnabled = false;

    private static ViewGroup progressView = null;

    private static ViewGroup emptyView = null;

    private static ViewGroup errorView = null;


    /**
     * 滚动的监听器
     */
    private AbsListView.OnScrollListener mScrollListener;


    public PullRefreshListView(Context context) {
        this(context, null);
    }


    public PullRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPullLoadEnabled(false);

    }

    @Override
    protected ListView createRefreshableView(Context context, AttributeSet attrs) {
        ListView listView = new ListView(context);
        mListView = listView;
        listView.setOnScrollListener(this);
        return listView;
    }

    /**
     * 设置滑动的监听器
     *
     * @param listener 监听器
     */
    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        mScrollListener = listener;
    }

    @Override
    protected boolean isReadyForPullUp() {
        if (scrollLoadEnabled && !pullLoadEnabled) {
            return isLastItemVisible;
        }
        return isLastItemVisible();
    }

    @Override
    public void setPullLoadEnabled(boolean pullLoadEnabled) {
        super.setPullLoadEnabled(pullLoadEnabled);
        this.pullLoadEnabled = pullLoadEnabled;
    }

    @Override
    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    @Override
    protected void startLoading() {
        super.startLoading();

        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(ILoadingLayout.State.REFRESHING);
        }
    }

    /**
     * 设置adapter
     *
     * @param adapter
     */
    public void setAdapter(Adapter adapter) {
        mListView.setAdapter((ListAdapter) adapter);
        adapter.registerDataSetObserver(new DataObserver(mListView, this));
    }


    /**
     * 设置adapter,在加载数据前先显示progress
     *
     * @param adapter
     */
    public void setAdapterWithProgress(Adapter adapter) {
        mListView.setAdapter((ListAdapter) adapter);
        adapter.registerDataSetObserver(new DataObserver(mListView, this));
        if (adapter.getCount() == 0) {
            showProgress();
        }
    }

    public static class DataObserver extends DataSetObserver {

        private ListView mListView = null;

        private PullRefreshListView mpullRefreshListView = null;


        public DataObserver(ListView listView, PullRefreshListView pullRefreshListView) {
            this.mListView = listView;
            this.mpullRefreshListView = pullRefreshListView;
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            update();
        }


        @Override
        public void onChanged() {
            super.onChanged();
            update();
        }

        private void update() {
            int count;
            if (mListView != null) {
                count = mListView.getAdapter().getCount();
                if (count > 0) {
                    mpullRefreshListView.showListView();
                }
                if (count == 0) {
                    mpullRefreshListView.showEmpty();
                }
            }

        }
    }

    private void showListView() {
        hideAllViews();
        displayListViews();
    }

    private void showEmpty() {
        if (emptyView != null) {
            hideAllViews();
            displayListViews();
            emptyView.setVisibility(VISIBLE);
        }
    }

    private void showProgress() {
        if (progressView != null) {
            hideAllViews();
            progressView.setVisibility(VISIBLE);
        }
    }

    public void setErrorView(int layout) {
        if (layout != -1 && mRefreshableViewWrapper != null) {
            errorView = (ViewGroup) LayoutInflater.from(getContext()).inflate(layout, null);
            errorView.setVisibility(GONE);
            mRefreshableViewWrapper.addView(errorView);
        }
    }

    @Override
    protected void addRefreshableView(Context context, ListView listView) {
        super.addRefreshableView(context, listView);
        progressView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_progress, null);
        progressView.setId(R.id.recycleview_progress);
        progressView.setVisibility(GONE);
        emptyView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_empty, null);
        emptyView.setId(R.id.recycleview_empty);
        emptyView.setVisibility(GONE);
        mRefreshableViewWrapper.addView(progressView);
        mRefreshableViewWrapper.addView(emptyView);
    }

    public void showErrorView() {
        if (errorView != null) {
            hideAllViews();
            errorView.setVisibility(VISIBLE);
            errorView.setClickable(true);
        }
    }


    public void showErrorView(OnClickListener listener) {
        if (errorView != null) {
            hideAllViews();
            errorView.setVisibility(VISIBLE);
            if (listener != null) {
                errorView.setOnClickListener(listener);
            }
        }
    }

    private void hideAllViews() {
        if (mRefreshableViewWrapper.getChildCount() > 0) {
            mListView.setVisibility(GONE);
            progressView.setVisibility(GONE);
            emptyView.setVisibility(GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }
    }

    private void displayListViews() {
        if (mRefreshableViewWrapper.getChildCount() > 0) {
            mListView.setVisibility(VISIBLE);
        }

    }


    @Override
    public void onPullUpRefreshComplete() {
        super.onPullUpRefreshComplete();
        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(ILoadingLayout.State.RESET);
            mLoadMoreFooterLayout.setClickable(false);
        }
    }

    @Override
    public void setScrollLoadEnabled(boolean scrollLoadEnabled) {
        super.setScrollLoadEnabled(scrollLoadEnabled);
        this.scrollLoadEnabled = scrollLoadEnabled;
        if (scrollLoadEnabled) {
            // 设置Footer
            if (null == mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout = new DefaultFooterLoadingLayout(getContext());
            }

            if (null == mLoadMoreFooterLayout.getParent()) {
                mListView.addFooterView(mLoadMoreFooterLayout, null, false);
            }
        }
        if (mLoadMoreFooterLayout != null) {
            mLoadMoreFooterLayout.show(false);
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isScrollLoadEnabled() && hasMoreData() && !isNetError()) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                    || scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                if (isReadyForPullUp()) {
                    startLoading();
                }
            }
        }

        if (null != mScrollListener) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }


    @Override
    public LoadingLayout getFooterLoadingLayout() {
        if (isScrollLoadEnabled()) {
            return mLoadMoreFooterLayout;
        }
        return super.getFooterLoadingLayout();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > visibleItemCount) {
            if (scrollLoadEnabled && mLoadMoreFooterLayout != null) {
                mLoadMoreFooterLayout.show(isLastItemVisible);
            }
            isLastItemVisible = true;
        } else {
            isLastItemVisible = false;
        }
    }


    /**
     * 表示是否还有更多数据
     *
     * @return true表示还有更多数据
     */
    private boolean hasMoreData() {
        if ((null != mLoadMoreFooterLayout) && (mLoadMoreFooterLayout.getState() == ILoadingLayout.State.NO_MORE_DATA)) {
            return false;
        }

        return true;
    }

    /**
     * 设置是否有更多数据的标志
     *
     * @param hasMoreData true表示还有更多的数据，false表示没有更多数据了
     */
    public void setHasMoreData(boolean hasMoreData) {
        if (!hasMoreData) {
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.setState(ILoadingLayout.State.NO_MORE_DATA);
            }
            LoadingLayout footerLoadingLayout = getFooterLoadingLayout();
            if (null != footerLoadingLayout) {
                footerLoadingLayout.setState(ILoadingLayout.State.NO_MORE_DATA);
            }
        }
    }


    private boolean isNetError() {
        if ((null != mLoadMoreFooterLayout) && (mLoadMoreFooterLayout.getState() == ILoadingLayout.State.NetWorkError)) {
            return true;
        }
        return false;
    }

    public void setNetError(boolean isNetError) {
        if (isNetError) {
            OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LoadingLayout) v).setState(ILoadingLayout.State.NetWorkError);
                    startLoading();
                }
            };
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.setClickable(true);
                mLoadMoreFooterLayout.setState(ILoadingLayout.State.NetWorkError);
                mLoadMoreFooterLayout.setOnClickListener(listener);
                LoadingLayout footerLoadingLayout = getFooterLoadingLayout();
                if (null != footerLoadingLayout) {
                    footerLoadingLayout.setState(ILoadingLayout.State.NetWorkError);
                    footerLoadingLayout.setOnClickListener(listener);
                }
            }
        }

    }


    /**
     * 判断第一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isFirstItemVisible() {
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        int mostTop = (mListView.getChildCount() > 0) ? mListView.getChildAt(0).getTop() : 0;
        if (mostTop >= 0) {
            return true;
        }

        return false;
    }


    /**
     * 判断最后一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isLastItemVisible() {
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        final int lastItemPosition = adapter.getCount() - 1;
        final int lastVisiblePosition = mListView.getLastVisiblePosition();

        /**
         * This check should really just be: lastVisiblePosition == lastItemPosition, but ListView
         * internally uses a FooterView which messes the positions up. For me we'll just subtract
         * one to account for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - mListView.getFirstVisiblePosition();
            final int childCount = mListView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = mListView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= mListView.getBottom();
            }
        }

        return false;
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
     * 设置滑动布局Footer样式
     *
     * @param layout
     */
    public void setScrollFooterLayout(LoadingLayout layout) {
        if (null == mLoadMoreFooterLayout.getParent()) {
            mListView.removeFooterView(mLoadMoreFooterLayout);
        }
        mLoadMoreFooterLayout = layout;
        if (null == mLoadMoreFooterLayout.getParent()) {
            mListView.addFooterView(mLoadMoreFooterLayout);
        }
        mLoadMoreFooterLayout.show(false);
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


    /**
     * 设置时间友好显示
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
