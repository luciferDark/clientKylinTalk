package com.ll.kylintalk;

import android.widget.TextView;

import com.ll.common.app.Activity;

import butterknife.BindView;


public class MainActivity extends Activity {
    @BindView(R.id.textTest)
    TextView textTest;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        textTest.setText("测试ButterKnife");
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
