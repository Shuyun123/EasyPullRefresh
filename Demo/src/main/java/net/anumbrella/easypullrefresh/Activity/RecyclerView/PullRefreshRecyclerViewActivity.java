package net.anumbrella.easypullrefresh.Activity.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import net.anumbrella.easypullrefresh.Activity.RecyclerView.PullRefreshRecyclerGridViewActivity;
import net.anumbrella.easypullrefresh.Activity.RecyclerView.PullRefreshRecyclerListViewActivity;
import net.anumbrella.easypullrefresh.R;


/**
 * author：anumbrella
 * Date:16/7/31 下午11:54
 */
public class PullRefreshRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_recyclerview_list;

    private Button btn_recyclerview_grid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        btn_recyclerview_grid = (Button) findViewById(R.id.recylerview_gridView);
        btn_recyclerview_list = (Button) findViewById(R.id.recylerview_ListView);
        btn_recyclerview_list.setOnClickListener(this);
        btn_recyclerview_grid.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recylerview_ListView:
                Intent listIntent = new Intent();
                listIntent.setClass(this, PullRefreshRecyclerListViewActivity.class);
                startActivity(listIntent);
                break;
            case R.id.recylerview_gridView:
                Intent gridIntent = new Intent();
                gridIntent.setClass(this,PullRefreshRecyclerGridViewActivity.class);
                startActivity(gridIntent);
                break;
        }
    }
}
