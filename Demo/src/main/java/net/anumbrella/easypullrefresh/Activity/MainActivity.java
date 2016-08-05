package net.anumbrella.easypullrefresh.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.anumbrella.easypullrefresh.Activity.ListView.PullRefreshListViewActivity;
import net.anumbrella.easypullrefresh.Activity.RecyclerView.PullRefreshRecyclerViewActivity;
import net.anumbrella.easypullrefresh.Activity.WebView.PullRefreshWebViewActivity;
import net.anumbrella.easypullrefresh.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_list;


    private Button btn_recycler;


    private Button btn_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_list = (Button) findViewById(R.id.listView);
        btn_recycler = (Button) findViewById(R.id.recyclerView);
        btn_web = (Button) findViewById(R.id.webview);
        btn_web.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_recycler.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listView:
                Intent listIntent = new Intent();
                listIntent.setClass(this, PullRefreshListViewActivity.class);
                startActivity(listIntent);
                break;
            case R.id.recyclerView:
                Intent recyclerIntent = new Intent();
                recyclerIntent.setClass(this, PullRefreshRecyclerViewActivity.class);
                startActivity(recyclerIntent);
                break;
            case R.id.webview:
                Intent webIntent = new Intent();
                webIntent.setClass(this,PullRefreshWebViewActivity.class);
                startActivity(webIntent);
                break;

        }
    }
}
