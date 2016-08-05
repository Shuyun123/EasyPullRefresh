package net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import net.anumbrella.pullrefresh.PullRefreshBase.CustomLoadingLayout;
import net.anumbrella.pullrefresh.R;

/**
 * author：anumbrella
 * Date:16/7/18 上午9:04
 * 封装的底部刷新布局
 */
public class DefaultFooterLoadingLayout extends CustomLoadingLayout {


    /**
     * 进度条
     */
    private ProgressWheel mProgressBar;

    /**
     * 显示的文本
     */
    private TextView mHintTextView;


    public DefaultFooterLoadingLayout(Context context) {
        super(context);
        init(context);
    }


    public DefaultFooterLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        mProgressBar = (ProgressWheel) findViewById(R.id.pull_load_footer_progressbar);
        mHintTextView = (TextView) findViewById(R.id.pull_load_footer_hint_textview);
        setState(State.RESET);
    }


    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_load_default_footer, null);
        return container;
    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
    }

    @Override
    protected void onStateChanged(State curState, State oldState) {
        mProgressBar.setVisibility(View.GONE);
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
    public ProgressWheel getProgressBar() {
        return mProgressBar;
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
        mProgressBar.setVisibility(View.VISIBLE);
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
