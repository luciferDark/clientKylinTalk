package com.ll.kylintalk.helper;

import android.content.Context;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * Fragment 调度器
 *
 * @param <T>
 */
public class NavHelper<T> {
    //所有的调度的tab集合
    private final SparseArray<Tab<T>> tabs = new SparseArray<>();

    private final Context context;
    private final int containerId;
    private final FragmentManager fragmentManager;
    private final OnTabChangeListener<T> tabChangeListener;

    private Tab<T> currentTab;//当前切换的tab

    public NavHelper(Context context, int containerId,
                     FragmentManager fragmentManager,
                     OnTabChangeListener<T> tabChangeListener) {
        this.context = context;
        this.containerId = containerId;
        this.fragmentManager = fragmentManager;
        this.tabChangeListener = tabChangeListener;
    }

    public NavHelper<T> add(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }

    private Tab<T> getCurrentTab() {
        return currentTab;
    }

    /**
     * 提交菜单选择操作
     * @param menuId
     * @return
     */
    public boolean performClickMenu(int menuId){
        Tab<T> tab = tabs.get(menuId);
        if (tab != null){
            doSelect(tab);
            return true;
        }
        return false;
    }

    /**
     * 选择tab
     * @param tab
     */
    private  void doSelect(Tab<T> tab){
        Tab<T> oldTab = null;
        // 判断一下是否有tab，是否是当前的tab
        if (currentTab != null){
            oldTab = currentTab;
            if (oldTab == tab){
                // 说明当前的tab就是点击的tab
                notifyTabReselect(tab);
                return;
            }
        }
        currentTab = tab;
        doTabChanged(currentTab,oldTab);
    }

    /**
     * 正在tab调度
     * @param newTab
     * @param oldTab
     */
    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab){
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (oldTab != null){
            if (oldTab.fragment != null){
                ft.detach(oldTab.fragment);
            }
        }

        if (newTab != null){
            if (newTab.fragment != null){
                ft.attach(newTab.fragment);
            } else {
                Fragment fragment = Fragment.instantiate(context, newTab.clx.getName(), null);
                newTab.fragment = fragment;
                ft.add(containerId, newTab.fragment, newTab.clx.getName());
            }
        }

        ft.commit();
        notifyTabSelect(newTab, oldTab);
    }

    /**
     * 通知回调监听器
     * @param newTab
     * @param oldTab
     */
    private void notifyTabSelect(Tab<T> newTab, Tab<T> oldTab){
        if (tabChangeListener !=  null){
            tabChangeListener.onTabChanged(newTab, oldTab);
        }
    }

    private void notifyTabReselect(Tab<T> tab){

    }

    /**
     * 定义Tab类的基础属性
     *
     * @param <T>
     */
    public static class Tab<T> {
        public Class<?> clx;// Fragment 的Class信息
        public T extra;//扩展字段
        Fragment fragment;

        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }
    }

    /**
     * 切换调度完成后接口回调
     *
     * @param <T>
     */
    public interface OnTabChangeListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
