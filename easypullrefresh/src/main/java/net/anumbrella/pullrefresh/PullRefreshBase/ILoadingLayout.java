package net.anumbrella.pullrefresh.PullRefreshBase;

/**
 * author：anumbrella
 * Date:16/7/17 下午8:34
 */
public interface ILoadingLayout {

    /**
     * 当前的状态
     */
    public enum State {

        /**
         * 初始状态
         */
        NONE,

        /**
         * 把ui重新置为用户还未下拉刷新的初始状态
         */
        RESET,

        /**
         * 用户是处于下拉状态，但还未下拉到足够刷新的距离
         */
        PULL_TO_REFRESH,

        /**
         * 用户处于下拉释放状态，一点释放就可以刷新
         */
        RELEASE_TO_REFRESH,

        /**
         * 当前正在刷新
         */
        REFRESHING,

        /**
         * 没有更多的数据
         */
        NO_MORE_DATA,


        /**
         * 网络异常
         */
        NetWorkError,
    }


    /**
     * 设置当前状态，派生类应该根据这个状态的变化来改变View的变化
     *
     * @param state 状态
     */
    public void setState(State state);

    /**
     * 得到当前的状态
     *
     * @return 状态
     */
    public State getState();

    /**
     * 得到当前Layout的内容大小，它将作为一个刷新的临界点
     *
     * @return 高度
     */
    public int getContentSize();

    /**
     * 在拉动时调用
     *
     * @param scale 拉动的比例
     */
    public void onPull(float scale);


}
