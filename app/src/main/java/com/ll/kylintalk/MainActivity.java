package com.ll.kylintalk;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ll.common.app.Activity;
import com.ll.common.app.Util.Logger;
import com.ll.kylintalk.helper.NavHelper;
import com.ll.kylintalk.main.ActiveFragment;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends Activity
        implements NavHelper.OnTabChangeListener<Integer>,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_layout_navigation)
    BottomNavigationView mNavigationView;

    @BindView(R.id.main_layout_tabs_container)
    FrameLayout mTabContainer;

    @BindView(R.id.main_layout_txt_title)
    TextView mTitle;

    @BindView(R.id.main_layout_img_search)
    ImageView mImgSearch;

    @BindView(R.id.main_layout_btn_action)
    FloatActionButton mActionButton;

    private NavHelper<Integer> mNavHelper;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mNavHelper = new NavHelper<Integer>(MainActivity.this,
                R.id.main_layout_tabs_container,
                getSupportFragmentManager(),
                MainActivity.this);
        mNavHelper.add(R.id.action_home,
                new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_group,
                        new NavHelper.Tab<>(ActiveFragment.class, R.string.title_group))
                .add(R.id.action_contact,
                        new NavHelper.Tab<>(ActiveFragment.class, R.string.title_contact));
        mNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    protected void initData() {
        super.initData();
        Menu menu = mNavigationView.getMenu();
        mNavigationView.setSelectedItemId(R.id.action_home);
        menu.performIdentifierAction(R.id.action_home, 0);//初始化初次点击事件
    }

    @OnClick(R.id.main_layout_img_search)
    void onSearchMenuClick() {
        Logger.LogI("onSearchMenuClick");
    }

    @OnClick(R.id.main_layout_btn_action)
    void onFloatActionClick() {
        Logger.LogI("onFloatActionClick");

    }

    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        Logger.LogI("onTabChanged");
        mTitle.setText(newTab.extra);

        float transY = 0;
        float rotation = 0;

        if (Objects.equals(newTab.extra, R.string.title_home)) {
            transY = Ui.dipToPx(getResources(), 76);
        } else if (Objects.equals(newTab.extra, R.string.title_group)) {
            mActionButton.setImageResource(R.drawable.src_group_icon);
            rotation = -360;
        } else {
            mActionButton.setImageResource(R.drawable.src_contact_icon);
            rotation = 360;
        }

        mActionButton.animate()
                .rotation(rotation)
                .translationY(transY)
                .setDuration(480)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return mNavHelper.performClickMenu(item.getItemId());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
