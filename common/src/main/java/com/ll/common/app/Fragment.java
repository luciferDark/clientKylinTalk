package com.ll.common.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class Fragment extends androidx.fragment.app.Fragment {
    protected View mRootView;
    protected Unbinder mRootUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            int _layoutId = getContentLayoutId();
            View view = inflater.inflate(_layoutId, container, false);
            mRootView = view;
            initWidget(mRootView);
        } else {
            if (mRootView.getParent() != null) {
                // 移除其父控件
                ((ViewGroup) (mRootView.getParent())).removeView(mRootView);
            }
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fragment 创建完成后初始化数据
        initData();
    }

    /**
     * 获取布局文件id接口
     *
     * @return 布局文件id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化参数是否正确
     *
     * @param bundle
     */
    protected void initArgs(Bundle bundle) {
    }

    /**
     * 初始化窗口设置
     */
    protected void initWindows() {

    }

    /**
     * 初始化控件
     */
    protected void initWidget(View view) {
        mRootUnbinder = ButterKnife.bind(this, view);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 处理返回键逻辑
     *
     * @return true 标识拦截返回键处理，false 不拦截
     */
    public boolean onBackPressed() {
        return false;
    }
}
