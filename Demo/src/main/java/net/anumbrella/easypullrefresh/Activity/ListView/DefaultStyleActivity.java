package net.anumbrella.easypullrefresh.Activity.ListView;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import net.anumbrella.easypullrefresh.R;
import net.anumbrella.pullrefresh.PullRefreshBase.PullRefreshBase;
import net.anumbrella.pullrefresh.Widget.PullRefreshListView;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * author：anumbrella
 * Date:16/8/3 下午11:39
 * 默认样式
 */
public class DefaultStyleActivity extends AppCompatActivity implements PullRefreshBase.OnRefreshListener<ListView> {


    private PullRefreshListView mPullListView;

    private ArrayAdapter<String> mAdapter;

    private ListView mListView;

    private LinkedList<String> mListItems;

    private boolean hasNetWork = true;

    public static final String[] mStrings = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mPullListView = (PullRefreshListView) findViewById(R.id.PullRefreshListView);
        mPullListView.setPullLoadEnabled(false);
        mPullListView.setScrollLoadEnabled(true);
        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        mListView = mPullListView.getRefreshableView();
        mPullListView.setErrorView(R.layout.error_view);
        mPullListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                if (position < mListItems.size()) {
                    String text = mListItems.get(position) + ", index = " + (position + 1);
                    Toast.makeText(DefaultStyleActivity.this, text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPullListView.setOnRefreshListener(this);
        mPullListView.doAutoPullRefreshing(true, 500);
    }


    @Override
    public void onPullDownRefresh(PullRefreshBase<ListView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hasNetWork) {
                    mListItems.addFirst("新数据");
                    mAdapter.notifyDataSetChanged();
                } else {
                    mPullListView.showErrorView();
                }
                mPullListView.onPullDownRefreshComplete();
            }
        }, 2000);

    }

    @Override
    public void onPullUpToRefresh(PullRefreshBase<ListView> refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!hasNetWork) {
                    mPullListView.onPullUpRefreshComplete();
                    mPullListView.setNetError(true);
                    return;
                }

                if (mListItems.size() > 30) {
                    mPullListView.onPullUpRefreshComplete();
                    mPullListView.setHasMoreData(false);
                    return;
                }

                mListItems.addAll(Arrays.asList(mStrings));
                mAdapter.notifyDataSetChanged();
                mPullListView.onPullUpRefreshComplete();
                mPullListView.setHasMoreData(true);

            }
        }, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.checkbox);
        CheckBox box = (CheckBox) item.getActionView();
        box.setChecked(true);
        box.setText("网络");
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasNetWork = isChecked;
            }
        });
        return true;
    }

}
