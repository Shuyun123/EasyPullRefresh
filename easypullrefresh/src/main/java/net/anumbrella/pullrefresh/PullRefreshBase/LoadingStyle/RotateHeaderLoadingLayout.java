package net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.anumbrella.pullrefresh.PullRefreshBase.CustomLoadingLayout;
import net.anumbrella.pullrefresh.R;

/**
 * author：anumbrella
 * Date:16/7/21 上午9:35
 */
public class RotateHeaderLoadingLayout extends CustomLoadingLayout {
    /**
     * 旋转动画的时间
     */
    static final int ROTATION_ANIMATION_DURATION = 1200;
    /**
     * 动画插值
     */
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();

    /**
     * 箭头图片
     */
    private ImageView mArrowImageView;
    /**
     * 状态提示TextView
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
     * 旋转的动画
     */
    private Animation mRotateAnimation;


    /**
     * 显示时间布局
     */
    private LinearLayout displayTimeLayout;



    /**
     * 下拉顶部显示图片
     */
    private ImageView mIcon;

    /**
     * 构造方法
     *
     * @param context context
     */
    public RotateHeaderLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public RotateHeaderLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context context
     */
    private void init(Context context) {
        mArrowImageView = (ImageView) findViewById(R.id.pull_refresh_header_arrow);
        displayTimeLayout = (LinearLayout) findViewById(R.id.pull_refresh_time_layout);
        mHintTextView = (TextView) findViewById(R.id.pull_refresh_header_hint_textview);
        mHeaderTimeView = (TextView) findViewById(R.id.pull_to_refresh_header_time);
        mHeaderTimeViewTitle = (TextView) findViewById(R.id.pull_refresh_last_update_time_text);
        mIcon = (ImageView) findViewById(R.id.pull_icon);

        mArrowImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mArrowImageView.setImageResource(R.drawable.default_ptr_rotate);

        float pivotValue = 0.5f;
        float toDegree = 720.0f;
        mRotateAnimation = new RotateAnimation(0.0f, toDegree, Animation.RELATIVE_TO_SELF, pivotValue,
                Animation.RELATIVE_TO_SELF, pivotValue);
        mRotateAnimation.setFillAfter(true);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);

    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_refresh_rotate_header, null);
        return container;
    }



    @Override
    public int getContentSize() {
        if (null != mContainer) {
            return mContainer.getHeight() + mIcon.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * 60);
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
        resetRotation();
        mHintTextView.setText(R.string.pull_refresh_header_hint_normal_down);
    }

    @Override
    protected void onReleaseToRefresh() {
        mHintTextView.setText(R.string.pull_refresh_header_hint_ready);
    }

    @Override
    protected void onPullToRefresh() {
        mHintTextView.setText(R.string.pull_refresh_header_hint_normal_down);
    }

    @Override
    protected void onRefreshing() {
        resetRotation();
        mArrowImageView.startAnimation(mRotateAnimation);
        mHintTextView.setText(R.string.pull_refresh_header_hint_loading);
    }

    @Override
    public void onPull(float scale) {
        float angle = scale * 180f;
        mArrowImageView.setRotation(angle);
    }

    /**
     * 重置动画
     */
    private void resetRotation() {
        mArrowImageView.clearAnimation();
        mArrowImageView.setRotation(0);
    }


}
