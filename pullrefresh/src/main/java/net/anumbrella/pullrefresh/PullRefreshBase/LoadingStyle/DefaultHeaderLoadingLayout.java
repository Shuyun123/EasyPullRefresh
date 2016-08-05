package net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import net.anumbrella.pullrefresh.PullRefreshBase.CustomLoadingLayout;
import net.anumbrella.pullrefresh.R;

/**
 * author：anumbrella
 * Date:16/7/19 上午11:30
 */
public class DefaultHeaderLoadingLayout extends CustomLoadingLayout {

    /**
     * 旋转动画时间
     */
    private static final int ROTATE_ANIM_DURATION = 150;

    /**
     * 箭头图片
     */
    private ImageView mArrowImageView;

    /**
     * 进度条
     */
    private ProgressWheel mProgressBar;

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
     * 向上的动画
     */
    private Animation mRotateUpAnim;

    /**
     * 向下的动画
     */
    private Animation mRotateDownAnim;

    /**
     * 下拉顶部显示图片
     */
    private ImageView mIcon;

    /**
     * 显示时间布局
     */
    private LinearLayout displayTimeLayout;


    public DefaultHeaderLoadingLayout(Context context) {
        super(context);
        init(context);
    }


    public DefaultHeaderLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        mArrowImageView = (ImageView) findViewById(R.id.pull_refresh_header_arrow);
        displayTimeLayout = (LinearLayout) findViewById(R.id.pull_refresh_time_layout);
        mHintTextView = (TextView) findViewById(R.id.pull_refresh_header_hint_textview);
        mProgressBar = (ProgressWheel) findViewById(R.id.pull_refresh_header_progressbar);
        mHeaderTimeView = (TextView) findViewById(R.id.pull_to_refresh_header_time);
        mHeaderTimeViewTitle = (TextView) findViewById(R.id.pull_refresh_last_update_time_text);
        mIcon = (ImageView) findViewById(R.id.pull_icon);

        float pivotValue = 0.5f;
        float toDegree = -180f;
        // 初始化旋转动画
        mRotateUpAnim = new RotateAnimation(0.0f, toDegree, Animation.RELATIVE_TO_SELF, pivotValue,
                Animation.RELATIVE_TO_SELF, pivotValue);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(toDegree, 0.0f, Animation.RELATIVE_TO_SELF, pivotValue,
                Animation.RELATIVE_TO_SELF, pivotValue);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

    }


    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_refresh_default_header, null);
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
    public ProgressWheel getProgressBar() {
        return mProgressBar;
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
        mArrowImageView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        super.onStateChanged(curState, oldState);
    }

    @Override
    protected void onReset() {
        mArrowImageView.clearAnimation();
        mHintTextView.setText(R.string.pull_refresh_header_hint_normal_down);
    }

    @Override
    protected void onPullToRefresh() {
        if (State.RELEASE_TO_REFRESH == getPreState()) {
            mArrowImageView.clearAnimation();
            mArrowImageView.startAnimation(mRotateDownAnim);
        }
        mHintTextView.setText(R.string.pull_refresh_header_hint_normal_down);
    }

    @Override
    protected void onReleaseToRefresh() {
        mArrowImageView.clearAnimation();
        mArrowImageView.startAnimation(mRotateUpAnim);
        mHintTextView.setText(R.string.pull_refresh_header_hint_ready);
    }

    @Override
    public int getContentSize() {
        if (null != mContainer) {
            return mContainer.getHeight() + mIcon.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * 60);
    }

    @Override
    protected void onRefreshing() {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.pull_refresh_header_hint_loading);
    }
}
