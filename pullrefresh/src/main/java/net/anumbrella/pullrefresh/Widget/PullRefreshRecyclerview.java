package net.anumbrella.pullrefresh.Widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.anumbrella.pullrefresh.Adapter.RecyclerAdapter;
import net.anumbrella.pullrefresh.PullRefreshBase.ILoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.LoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle.DefaultFooterLoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.R;
import net.anumbrella.pullrefresh.Utils.BaseUtils;
import net.anumbrella.pullrefresh.Utils.PreferenceUtils;


/**
 * author：anumbrella
 * Date:16/7/18 下午7:00
 */
public class PullRefreshRecyclerview extends PullRefreshBase<RecyclerView> {

    private RecyclerView mRecycler;

    private RecyclerView.OnScrollListener onScrollListener;

    private int color = -1;

    private static boolean isLastItemVisible = false;

    private static ViewGroup progressView = null;

    private static ViewGroup emptyView = null;

    private static ViewGroup errorView = null;

    /**
     * 用于滑到底部自动加载的Footer
     */
    private LoadingLayout mLoadMoreFooterLayout;


    private static boolean scrollLoadEnabled = false;

    private static boolean pullLoadEnabled = false;


    public PullRefreshRecyclerview(Context context) {
        this(context, null);
    }

    public PullRefreshRecyclerview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshRecyclerview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPullLoadEnabled(false);

    }

    @Override
    protected void startLoading() {
        super.startLoading();
        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(ILoadingLayout.State.REFRESHING);
        }
    }

    @Override
    public void setScrollLoadEnabled(boolean scrollLoadEnabled) {
        super.setScrollLoadEnabled(scrollLoadEnabled);
        this.scrollLoadEnabled = scrollLoadEnabled;
    }

    @Override
    public void setPullLoadEnabled(boolean pullLoadEnabled) {
        super.setPullLoadEnabled(pullLoadEnabled);
        this.pullLoadEnabled = pullLoadEnabled;
    }


    @Override
    public void onPullUpRefreshComplete() {
        super.onPullUpRefreshComplete();
        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(ILoadingLayout.State.RESET);
            mLoadMoreFooterLayout.setClickable(false);
        }
        isLastItemVisible = false;
    }

    /**
     * 设置Footer背景颜色
     *
     * @param color
     */
    public void setFooterLoadingBgColor(int color) {
        if (mLoadMoreFooterLayout != null) {
            mLoadMoreFooterLayout.setBackgroundResource(color);
        }
        this.color = color;
    }


    /**
     * 设置adapter
     *
     * @param adapter
     */
    public void setAdapter(RecyclerAdapter adapter) {
        mRecycler.setAdapter(adapter);
        if (scrollLoadEnabled && adapter.getFooterView(true) == null) {
            if (null == mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout = new DefaultFooterLoadingLayout(getContext());
            }
            adapter.addFooterView(mLoadMoreFooterLayout);
            adapter.getFooterView(true).setVisibility(GONE);
        }
        if (color != -1) {
            mLoadMoreFooterLayout.setBackgroundResource(color);
        }
    }


    /**
     * 设置adapter,在加载数据前先显示progress
     *
     * @param adapter
     */
    public void setAdapterWithProgress(RecyclerAdapter adapter) {
        mRecycler.setAdapter(adapter);
        if (scrollLoadEnabled && adapter.getFooterView(true) == null) {
            if (null == mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout = new DefaultFooterLoadingLayout(getContext());
            }
            adapter.addFooterView(mLoadMoreFooterLayout);
            adapter.getFooterView(true).setVisibility(GONE);
        }
        if (color != -1) {
            mLoadMoreFooterLayout.setBackgroundResource(color);
        }

        adapter.registerAdapterDataObserver(new DataObserver(mRecycler, this));
        if (adapter.getCount() == 0) {
            showProgress();
        }
    }

    @Override
    public LoadingLayout getFooterLoadingLayout() {
        if (isScrollLoadEnabled()) {
            return mLoadMoreFooterLayout;
        }
        return super.getFooterLoadingLayout();
    }

    private boolean isNetError() {
        if ((null != mLoadMoreFooterLayout) && (mLoadMoreFooterLayout.getState() == ILoadingLayout.State.NetWorkError)) {
            return true;
        }
        return false;
    }

    private boolean hasMoreData() {
        if ((null != mLoadMoreFooterLayout) && (mLoadMoreFooterLayout.getState() == ILoadingLayout.State.NO_MORE_DATA)) {
            return false;
        }
        return true;
    }


    /**
     * 设置网络错误
     *
     * @param isNetError
     */
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
     * 设置滑动布局Footer样式
     *
     * @param layout
     */
    public void setScrollFooterLayout(LoadingLayout layout) {
        if (scrollLoadEnabled) {
            if (mRecycler.getAdapter() != null && mLoadMoreFooterLayout != null) {
                if (((RecyclerAdapter) mRecycler.getAdapter()).getFooterView(true) != null) {
                    ((RecyclerAdapter) mRecycler.getAdapter()).removeFooterView(mLoadMoreFooterLayout);
                    ((RecyclerAdapter) mRecycler.getAdapter()).addFooterView(layout);
                    ((RecyclerAdapter) mRecycler.getAdapter()).getFooterView(true).setVisibility(GONE);
                }
            }
            mLoadMoreFooterLayout = layout;
        }
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
     * 更新显示的时间
     */
    @Override
    protected void updateDisplayTime() {
        long time = System.currentTimeMillis();
        if (PreferenceUtils.readBoolean(getContext(), BaseUtils.Md5(getClass().getName()), getClass().getName(), true)) {
            setLastUpdatedLabel(BaseUtils.friendlyTime(time));
        } else {
            setLastUpdatedLabel(BaseUtils.formatDateTime(time));
        }
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


    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        mRecycler = new RecyclerView(context);
        onScrollListener = new android.support.v7.widget.RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {

                if (isScrollLoadEnabled()) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                        if (isReadyForPullUp()) {
                            if (hasMoreData() && !isNetError()) {
                                startLoading();
                            }
                        }
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom()) {
                    if (scrollLoadEnabled && mLoadMoreFooterLayout != null) {
                        if (((RecyclerAdapter) mRecycler.getAdapter()).getFooterView(true) != null) {
                            ((RecyclerAdapter) mRecycler.getAdapter()).getFooterView(true).setVisibility(VISIBLE);
                        }
                    }
                }
                isLastItemVisible = isSlideToBottom();

                super.onScrolled(recyclerView, dx, dy);

            }
        };
        mRecycler.setOnScrollListener(onScrollListener);
        return mRecycler;
    }


    public static class DataObserver extends RecyclerView.AdapterDataObserver {

        private RecyclerView mRecyclerView = null;

        private PullRefreshRecyclerview mPullRefreshRecyclerview = null;


        public DataObserver(RecyclerView recyclerView, PullRefreshRecyclerview pullRefreshRecyclerview) {
            this.mRecyclerView = recyclerView;
            this.mPullRefreshRecyclerview = pullRefreshRecyclerview;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            update();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            update();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            update();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            update();
        }

        @Override
        public void onChanged() {
            super.onChanged();
            update();
        }

        private void update() {
            int count;
            if (mRecyclerView != null) {
                count = ((RecyclerAdapter) mRecyclerView.getAdapter()).getCount();
                if (count > 0) {
                    mPullRefreshRecyclerview.showRecycleView();
                }
                if (count == 0) {
                    mPullRefreshRecyclerview.showEmpty();
                }
            }

        }
    }


    private void hideAllViews() {
        if (mRefreshableViewWrapper.getChildCount() > 0) {
            mRecycler.setVisibility(GONE);
            progressView.setVisibility(GONE);
            emptyView.setVisibility(GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }
    }

    private void displayRefreshViews() {
        if (mRefreshableViewWrapper.getChildCount() > 0) {
            mRecycler.setVisibility(VISIBLE);
        }

    }


    private void showRecycleView() {
        hideAllViews();
        displayRefreshViews();
    }

    private void showEmpty() {
        if (emptyView != null) {
            hideAllViews();
            displayRefreshViews();
            emptyView.setVisibility(VISIBLE);
        }
    }


    private void showProgress() {
        if (progressView != null) {
            hideAllViews();
            progressView.setVisibility(VISIBLE);
        }
    }


    public void showErrorView() {
        if (errorView != null) {
            hideAllViews();
            errorView.setVisibility(VISIBLE);
            errorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgress();
                    startLoading();
                }
            });
        }
    }

    @Override
    protected void addRefreshableView(Context context, RecyclerView refreshableView) {
        super.addRefreshableView(context, refreshableView);
        progressView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_progress, null);
        progressView.setId(R.id.recycleview_progress);
        progressView.setVisibility(GONE);
        emptyView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_empty, null);
        emptyView.setId(R.id.recycleview_empty);
        emptyView.setVisibility(GONE);
        mRefreshableViewWrapper.addView(progressView);
        mRefreshableViewWrapper.addView(emptyView);
    }


    public void setErrorView(int layout) {
        if (layout != -1 && mRefreshableViewWrapper != null) {
            errorView = (ViewGroup) LayoutInflater.from(getContext()).inflate(layout, null);
            errorView.setVisibility(GONE);
            mRefreshableViewWrapper.addView(errorView);
        }
    }


    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecycler.setLayoutManager(manager);
    }


    @Override
    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    @Override
    protected boolean isReadyForPullUp() {
        return isLastItemVisible;
    }


    /**
     * 判断第一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isFirstItemVisible() {
        final RecyclerAdapter adapter = (RecyclerAdapter) mRecycler.getAdapter();

        if (null == adapter || adapter.getCount() == 0) {
            return true;
        }
        int mostTop = (mRecycler.getChildCount() > 0) ? mRecycler.getChildAt(0).getTop() : 0;
        if (mostTop >= 0) {
            return true;
        }

        return false;
    }


    /**
     * 判断是否滑动到底部
     *
     * @return
     */
    private boolean isSlideToBottom() {
        final RecyclerAdapter adapter = (RecyclerAdapter) mRecycler.getAdapter();
        if (adapter == null) {
            return false;
        }

        int visibleItemCount = mRecycler.getChildCount();
        if (visibleItemCount == adapter.getItemCount()) {
            return false;
        }


        if (mRecycler != null) {
            RecyclerView.LayoutManager layoutManager = mRecycler.getLayoutManager();
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layout =
                        (StaggeredGridLayoutManager) layoutManager;
                int column = layout.getColumnCountForAccessibility(null, null);
                int positions[] = new int[column];
                // 获取lastItem的positions
                layout.findLastVisibleItemPositions(positions);
                for (int i = 0; i < positions.length; i++) {
                    /**
                     * 判断lastItem的底边到recyclerView顶部的距离
                     * 是否小于recyclerView的高度
                     * 如果小于或等于 说明滚动到了底部
                     */
                    if (layout.findViewByPosition(positions[i]).getBottom() <= getHeight()) {
                        return true;
                    }
                }
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager layout =
                        (LinearLayoutManager) layoutManager;
                int position = layout.findLastVisibleItemPosition();
                if (layout.findViewByPosition(position).getBottom() <= getHeight()) {
                    return true;
                }
            } else if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager layout =
                        (GridLayoutManager) layoutManager;
                int position = layout.findLastVisibleItemPosition();
                if (layout.findViewByPosition(position).getBottom() <= getHeight()) {
                    return true;
                }

            }
        }

        return false;
    }

}
