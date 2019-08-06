package com.ll.common.app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


/**
 * @author kylin
 */
public abstract class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();

        if (initArgs(getIntent().getExtras())) {
            int _layoutId = getContentLayoutId();
            setContentView(_layoutId);
            initWidget();
            initData();
        } else {
            finish();
        }

    }

    /**
     * 初始化窗口设置
     */
    protected void initWindows() {

    }

    /**
     * 初始化参数是否正确
     *
     * @param bundle
     * @return
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 获取布局文件id接口
     *
     * @return 布局文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        //当点击导航返回时，结束当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 返回点击
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        List<androidx.fragment.app.Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments != null && fragments.size() > 0) {
            for (androidx.fragment.app.Fragment fragment : fragments) {
                if (fragment instanceof com.ll.common.app.Fragment) {
                    if (((com.ll.common.app.Fragment) fragment).onBackPressed()) {
                        //判断fragment中是否拦截了返回键事件，是则直接返回
                        return;
                    }
                }
            }
        }

        finish();
    }
}
