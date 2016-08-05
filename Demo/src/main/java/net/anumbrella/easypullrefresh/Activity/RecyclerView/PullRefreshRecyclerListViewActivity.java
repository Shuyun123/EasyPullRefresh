package net.anumbrella.easypullrefresh.Activity.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import net.anumbrella.easypullrefresh.Adapter.ArrayAdapter;
import net.anumbrella.easypullrefresh.Model.DataProviderModel;
import net.anumbrella.easypullrefresh.R;
import net.anumbrella.pullrefresh.Adapter.RecyclerAdapter;
import net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle.ProgressBarHeaderLoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshRecyclerview;

import java.util.Arrays;

/**
 * author：anumbrella
 * Date:16/8/1 下午4:43
 */
public class PullRefreshRecyclerListViewActivity extends AppCompatActivity implements PullRefreshBase.OnRefreshListener<RecyclerView> {


    private PullRefreshRecyclerview refreshRecyclerview;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_listview);
        refreshRecyclerview = (PullRefreshRecyclerview) findViewById(R.id.PullRefreshRecyclerview);
        adapter = new ArrayAdapter(this);
        //可以上拉刷新
        refreshRecyclerview.setPullLoadEnabled(true);
        refreshRecyclerview.setScrollLoadEnabled(false);
        refreshRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        refreshRecyclerview.setOnRefreshListener(this);
        refreshRecyclerview.setErrorView(R.layout.error_view);
        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_view, null);
        adapter.addFooterView(footerView);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_view, null);
        adapter.addHeaderView(headerView);
        adapter.setmOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(PullRefreshRecyclerListViewActivity.this, "index " + (position+1), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
        ProgressBarHeaderLoadingLayout headerLoadingLayout = new ProgressBarHeaderLoadingLayout(this);
        refreshRecyclerview.setHeaderLayout(headerLoadingLayout);
        refreshRecyclerview.setAdapterWithProgress(adapter);
        onPullDownRefresh(refreshRecyclerview);

    }

    private void setData() {
        adapter.addAll(DataProviderModel.getImageListData());
    }

    @Override
    public void onPullDownRefresh(PullRefreshBase<RecyclerView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getCount() != 0) {
                    adapter.clear();
                    refreshRecyclerview.onPullUpRefreshComplete();
                }
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
                if (adapter.getCount() > 30) {
                    refreshRecyclerview.onPullUpRefreshComplete();
                    refreshRecyclerview.setHasMoreData(false);
                    return;
                }

                setData();
                adapter.notifyDataSetChanged();
                refreshRecyclerview.onPullUpRefreshComplete();
                refreshRecyclerview.setHasMoreData(true);
            }
        },2000);

    }
}