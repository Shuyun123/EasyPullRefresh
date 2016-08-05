package net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.anumbrella.pullrefresh.LoadingStyle.AVLoadingIndicatorView;
import net.anumbrella.pullrefresh.PullRefreshBase.CustomLoadingLayout;
import net.anumbrella.pullrefresh.R;

/**
 * author：anumbrella
 * Date:16/8/3 下午5:02
 */
public class IndicatorViewFooterLoadingLayout extends CustomLoadingLayout {

    /**
     * @seehttps://github.com/81813780/AVLoadingIndicatorView
     */
    private AVLoadingIndicatorView LoadingView;


    /**
     * 显示的文本
     */
    private TextView mHintTextView;


    public IndicatorViewFooterLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public IndicatorViewFooterLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LoadingView = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
        mHintTextView = (TextView) findViewById(R.id.pull_load_footer_hint_textview);
        setState(State.RESET);

    }

    /**
     * 设置loading的颜色
     *
     * @param style
     */
    public void setIndicatorStyle(String style) {
        if (LoadingView != null) {
            LoadingView.setIndicatorStyle(style);
        }
    }

    /**
     * 设置loading的样式
     *
     * @param color
     */
    public void setIndicatorColor(int color) {
        if (LoadingView != null) {
            LoadingView.setIndicatorColor(color);
        }
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_load_indicatorview_footer, null);
        return container;
    }


    @Override
    protected void onStateChanged(State curState, State oldState) {
        mHintTextView.setVisibility(View.INVISIBLE);
        super.onStateChanged(curState, oldState);
    }

    @Override
    protected void onReset() {
        mHintTextView.setText(R.string.pull_refresh_header_hint_loading);
    }


    @Override
    public TextView getHintTextView() {
        return mHintTextView;
    }

    @Override
    protected void onPullToRefresh() {
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.pull_refresh_header_hint_normal_up);
    }

    @Override
    protected void onReleaseToRefresh() {
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.pull_refresh_header_hint_ready);
    }

    @Override
    protected void onRefreshing() {
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.pull_refresh_header_hint_loading);
    }

    @Override
    protected void onNoMoreData() {
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.pushmsg_center_no_more_msg);
    }

    @Override
    protected void onNetWorkError() {
        mHintTextView.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.pushmsg_center_net_work_error_msg);
    }
}
