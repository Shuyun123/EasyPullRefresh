package net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.anumbrella.pullrefresh.LoadingStyle.AVLoadingIndicatorView;
import net.anumbrella.pullrefresh.PullRefreshBase.CustomLoadingLayout;
import net.anumbrella.pullrefresh.R;

/**
 * author：anumbrella
 * Date:16/8/3 上午11:22
 */
public class IndicatorViewHeaderLoadingLayout extends CustomLoadingLayout {


    /**
     * @seehttps://github.com/81813780/AVLoadingIndicatorView
     */
    private AVLoadingIndicatorView LoadingView;


    /**
     * 状态提示TextView*
     */
    private TextView mHintTextView;

    /**
     * 最后更新时间的TextView
     */
    private TextView mHeaderTimeView;

    /**
     * 最后更新时间的标题
     */
    private TextView mHeaderTimeViewTitle;


    /**
     * 下拉顶部显示图片
     */
    private ImageView mIcon;

    /**
     * 显示时间布局
     */
    private LinearLayout displayTimeLayout;

    public IndicatorViewHeaderLoadingLayout(Context context) {
        super(context);
        init(context);
    }


    public IndicatorViewHeaderLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LoadingView = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
        displayTimeLayout = (LinearLayout) findViewById(R.id.pull_refresh_time_layout);
        mHintTextView = (TextView) findViewById(R.id.pull_refresh_header_hint_textview);
        mHeaderTimeView = (TextView) findViewById(R.id.pull_to_refresh_header_time);
        mHeaderTimeViewTitle = (TextView) findViewById(R.id.pull_refresh_last_update_time_text);
        mIcon = (ImageView) findViewById(R.id.pull_icon);
    }


    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_refresh_indicatorview_header, null);
        return container;
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
    public void setLastUpdatedLabel(CharSequence label) {
        // 如果最后更新的时间的文本是空的话，隐藏前面的标题
        mHeaderTimeViewTitle.setVisibility(TextUtils.isEmpty(label) ? View.INVISIBLE : View.VISIBLE);
        mHeaderTimeView.setText(label);
    }

    @Override
    public TextView getHintTextView() {
        return mHintTextView;
    }

    @Override
    public TextView getHeaderTimeView() {
        return mHeaderTimeView;
    }

    @Override
    public TextView getHeaderTimeViewTitle() {
        return mHeaderTimeViewTitle;
    }

    @Override
    public ImageView getIcon() {
        return mIcon;
    }


    @Override
    public LinearLayout getDisplayTimeLayout() {
        return displayTimeLayout;
    }

    @Override
    protected void onStateChanged(State curState, State oldState) {
        super.onStateChanged(curState, oldState);
    }

    @Override
    protected void onReset() {
        mHintTextView.setText(R.string.pull_refresh_header_hint_normal_down);
    }

    @Override
    protected void onPullToRefresh() {
        mHintTextView.setText(R.string.pull_refresh_header_hint_normal_down);
    }

    @Override
    protected void onReleaseToRefresh() {
        mHintTextView.setText(R.string.pull_refresh_header_hint_ready);
    }

    @Override
    protected void onRefreshing() {
        mHintTextView.setText(R.string.pull_refresh_header_hint_loading);
    }

}
