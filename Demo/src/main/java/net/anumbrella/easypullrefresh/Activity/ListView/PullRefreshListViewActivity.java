package net.anumbrella.easypullrefresh.Activity.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import net.anumbrella.easypullrefresh.R;

/**
 * author：anumbrella
 * Date:16/7/19 上午10:37
 */
public class PullRefreshListViewActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_default;


    private Button btn_setting;


    private Button btn_custom;


    private Button btn_indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_main);
        btn_default = (Button) findViewById(R.id.DefaultStyle);
        btn_custom = (Button) findViewById(R.id.CustomStyle);
        btn_setting = (Button) findViewById(R.id.SettingStyle);
        btn_indicator = (Button) findViewById(R.id.IndicatorStyle);
        btn_default.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        btn_custom.setOnClickListener(this);
        btn_indicator.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.DefaultStyle:
                Intent defaultIntent = new Intent();
                defaultIntent.setClass(this, DefaultStyleActivity.class);
                startActivity(defaultIntent);
                break;
            case R.id.SettingStyle:
                Intent settingIntent = new Intent();
                settingIntent.setClass(this, SettingStyleActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.CustomStyle:
                Intent customIntent = new Intent();
                customIntent.setClass(this, CustomStyleActivity.class);
                startActivity(customIntent);
                break;
            case R.id.IndicatorStyle:
                Intent indicatorIntent = new Intent();
                indicatorIntent.setClass(this, IndicatorStyleActivity.class);
                startActivity(indicatorIntent);
                break;

        }
    }
}