package net.anumbrella.easypullrefresh.Activity.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.pnikosis.materialishprogress.ProgressWheel;

import net.anumbrella.easypullrefresh.Model.Bean.ImageDataModel;
import net.anumbrella.easypullrefresh.Model.DataProviderModel;
import net.anumbrella.easypullrefresh.R;
import net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle.IndicatorViewHeaderLoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshRecyclerview;

/**
 * author：anumbrella
 * Date:16/8/2 上午9:14
 */
public class PullRefreshRecyclerGridViewActivity extends AppCompatActivity {


    private PullRefreshRecyclerview refreshRecyclerview;


    private net.anumbrella.easypullrefresh.Adapter.ArrayAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_listview);
        refreshRecyclerview = (PullRefreshRecyclerview) findViewById(R.id.PullRefreshRecyclerview);
        adapter = new net.anumbrella.easypullrefresh.Adapter.ArrayAdapter(this);
        refreshRecyclerview.setPullLoadEnabled(false);
        refreshRecyclerview.setScrollLoadEnabled(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        refreshRecyclerview.setLayoutManager(gridLayoutManager);

        //设置header样式
        IndicatorViewHeaderLoadingLayout headerLoadingLayout = new IndicatorViewHeaderLoadingLayout(this);
        //样式类型可参考：https://github.com/81813780/AVLoadingIndicatorView，进行选择。
        headerLoadingLayout.setIndicatorStyle("LineScalePulseOut");
        headerLoadingLayout.setIndicatorColor(R.color.colorPrimary);
        refreshRecyclerview.setHeaderLayout(headerLoadingLayout);

        refreshRecyclerview.setOnRefreshListener(new PullRefreshBase.OnRefreshListener<RecyclerView>() {

            @Override
            public void onPullDownRefresh(PullRefreshBase<RecyclerView> refreshView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setData();
                        adapter.notifyDataSetChanged();
                        refreshRecyclerview.onPullDownRefreshComplete();
                        refreshRecyclerview.setHasMoreData(true);

                    }
                }, 2000);

            }

            @Override
            public void onPullUpToRefresh(PullRefreshBase<RecyclerView> refreshView) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setData();
                        adapter.notifyDataSetChanged();
                        refreshRecyclerview.onPullUpRefreshComplete();
                        refreshRecyclerview.setHasMoreData(true);

                    }
                }, 2000);

            }
        });
        setData();
        adapter.notifyDataSetChanged();
        refreshRecyclerview.setAdapter(adapter);
        //这个ProgressBar必须在建立adapter后进行设置
        ((ProgressWheel) refreshRecyclerview.getFooterLoadingLayout().getProgressBar()).setBarColor(getResources().getColor(R.color.colorAccent));
        refreshRecyclerview.doAutoPullRefreshing(true, 500);
    }

    private void setData() {
        adapter.addAll(DataProviderModel.getImageListData());
    }

}
