package net.anumbrella.easypullrefresh.Activity.ListView;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingle.widget.LoadingView;

import net.anumbrella.easypullrefresh.R;
import net.anumbrella.pullrefresh.PullRefreshBase.CustomLoadingLayout;

/**
 * author：anumbrella
 * Date:16/8/4 上午11:18
 * 自定义样式
 */
public class CustomStyleHeaderLoadingLayout extends CustomLoadingLayout {


    private LoadingView mLoadingView;

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
     * 显示时间布局
     */
    private LinearLayout displayTimeLayout;


    public CustomStyleHeaderLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public CustomStyleHeaderLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mLoadingView = (LoadingView) findViewById(R.id.loadView);
        displayTimeLayout = (LinearLayout) findViewById(R.id.pull_refresh_time_layout);
        mHintTextView = (TextView) findViewById(R.id.pull_refresh_header_hint_textview);
        mHeaderTimeView = (TextView) findViewById(R.id.pull_to_refresh_header_time);
        mHeaderTimeViewTitle = (TextView) findViewById(R.id.pull_refresh_last_update_time_text);
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.custom_style_header_loading_layout, null);
        return container;
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
    public LinearLayout getDisplayTimeLayout() {
        return displayTimeLayout;
    }

    @Override
    protected void onStateChanged(State curState, State oldState) {
        super.onStateChanged(curState, oldState);
    }

    @Override
    protected void onReset() {
        mHintTextView.setText("下拉可刷新哦~_~");
    }

    @Override
    protected void onPullToRefresh() {
        mHintTextView.setText("下拉可刷新哦~_~");
    }

    @Override
    protected void onReleaseToRefresh() {
        mHintTextView.setText("松开可刷新");
    }


    @Override
    protected void onRefreshing() {

        mHintTextView.setText("正在刷新(＞﹏＜)");
    }


}
