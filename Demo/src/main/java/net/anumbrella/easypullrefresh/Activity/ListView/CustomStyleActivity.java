package net.anumbrella.easypullrefresh.Activity.ListView;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import net.anumbrella.easypullrefresh.R;
import net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle.ProgressBarHeaderLoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.LoadingStyle.RotateHeaderLoadingLayout;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshListView;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * author：anumbrella
 * Date:16/8/3 下午11:40
 */
public class CustomStyleActivity extends AppCompatActivity implements PullRefreshBase.OnRefreshListener<ListView> {


    private PullRefreshListView mPullListView;

    private ArrayAdapter<String> mAdapter;

    private ListView mListView;

    private LinkedList<String> mListItems;


    public static final String[] mStrings = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mPullListView = (PullRefreshListView) findViewById(R.id.PullRefreshListView);
        //上拉刷新
        mPullListView.setPullLoadEnabled(false);
        //滑动到底部自动加载
        mPullListView.setScrollLoadEnabled(true);
        mListItems = new LinkedList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        mListView = mPullListView.getRefreshableView();

        //设置刷新的样式，选择内置提供的样式，可以自己定义
        CustomStyleHeaderLoadingLayout headerLoadingLayout = new CustomStyleHeaderLoadingLayout(this);

        mPullListView.setHeaderLayout(headerLoadingLayout);

        //需要footerLoadingLayout可以仿照DefaultFooterLoadingLayout自定义

        //设置到底部自动滑动刷新布局
        //mPullListView.setScrollFooterLayout(footerLoadingLayout);

        mPullListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                if (position < mListItems.size()) {
                    String text = mListItems.get(position) + ", index = " + (position + 1);
                    Toast.makeText(CustomStyleActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPullListView.setFooterLoadingBgColor(R.color.footer_bg);
        mPullListView.setHeaderLoadingBgColor(R.color.header_bg);
        //是否显示时间
        //mPullListView.setDisplayTime(false);

        //友好显示时间
        mPullListView.setFriendlyTime(false);

        mPullListView.setOnRefreshListener(this);

        mPullListView.doAutoPullRefreshing(true,500);
    }


    @Override
    public void onPullDownRefresh(PullRefreshBase<ListView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListItems.addAll(Arrays.asList(mStrings));
                mAdapter.notifyDataSetChanged();
                mPullListView.setHasMoreData(true);
                mPullListView.onPullDownRefreshComplete();
            }
        }, 5000);

    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<ListView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListItems.addAll(Arrays.asList(mStrings));
                mAdapter.notifyDataSetChanged();
                mPullListView.onPullUpRefreshComplete();
                mPullListView.setHasMoreData(true);

            }
        }, 2000);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
