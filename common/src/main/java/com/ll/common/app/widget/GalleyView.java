package com.ll.common.app.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ll.common.R;
import com.ll.common.widget.recycler.RecyclerAdapter;

import java.util.LinkedList;
import java.util.List;

public class GalleyView extends RecyclerView {
    private static final int LOADER_ID = 0x0100;
    private static final int GALLEY_VIEW_SPAN_COUNT = 4;
    private static final int MAX_IMAGE_SELECT_COUNT = 4;

    private Adapter mAdapter = new Adapter();
    private LoaderCallback mLoaderCallback = new LoaderCallback();

    private List<Image> mSelectedImages = new LinkedList<>();

    public GalleyView(Context context) {
        this(context, null);
    }

    public GalleyView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public GalleyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), GALLEY_VIEW_SPAN_COUNT));
        setAdapter(mAdapter);
        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Image image) {
                super.onItemClick(holder, image);
            }
        });
    }

    /**
     * 初始化LoaderManager
     *
     * @param loaderManager
     * @return LOADER_ID
     */
    public int setup(LoaderManager loaderManager) {
        loaderManager.initLoader(LOADER_ID, null, mLoaderCallback);
        return LOADER_ID;
    }

    /**
     * 处理点击事件逻辑
     *
     * @param image
     * @return true代表进行了数据修改需要刷新。
     */
    private boolean onItemSelectCllick(Image image) {
        boolean notifyRefresh = false;
        if (mSelectedImages.contains(image)) {
            //选中过该cell，移除
            mSelectedImages.remove(image);
            image.isSelect = false;

            notifyRefresh = true;
        } else {
            if (mSelectedImages.size() >= MAX_IMAGE_SELECT_COUNT) {
                //todo 大于最大选中数量，逻辑不做处理，可以给个提示
                notifyRefresh = false;
            } else {
                image.isSelect = true;
                mSelectedImages.add(image);
                notifyRefresh = true;
            }
        }

        if (notifyRefresh) {
            notifySelectChanged();
        }

        return notifyRefresh;
    }

    /**
     * 通知选中状态改变
     */
    private void notifySelectChanged() {
    }

    /**
     * 获取所以选中图片的路径
     *
     * @return 路径数组
     */
    public String[] getSelectedPath() {
        String[] paths = new String[mSelectedImages.size()];
        int index = 0;
        for (Image imageItem : mSelectedImages) {
            paths[index++] = imageItem.path;
        }

        return paths;
    }

    /**
     * 一键清除选取图片
     */
    public void clearSelected() {
        for (Image imageItem : mSelectedImages) {
            imageItem.isSelect = false;
        }
        mSelectedImages.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 内部加载数据结构
     */
    private static class Image {
        int id;
        String path;
        long date;
        boolean isSelect;

        @Override
        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Image img = (Image) obj;
            boolean result = false;
            if (path != null) {
                result = path.equals(img.path);
            } else {
                result = (img.path == null);
            }
            return result;
        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }

    public class Adapter extends RecyclerAdapter<Image> {

        @Override
        protected ViewHolder createViewHolder(View root, int viewType) {
            return new GalleyView.ViewHolder(root);
        }

        @Override
        protected int getItemViewType(int position, Image img) {
            return R.layout.cell_galley_layout;
        }

        @Override
        public void update(Image img, ViewHolder holder) {

        }
    }

    public class ViewHolder extends RecyclerAdapter.ViewHolder<Image> {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Image image) {

        }
    }

    /**
     * 内部类LoaderManager加载回调
     */
    public class LoaderCallback implements LoaderManager.LoaderCallbacks {

        @NonNull
        @Override
        public Loader onCreateLoader(int id, @Nullable Bundle args) {
            return null;
        }

        @Override
        public void onLoadFinished(@NonNull Loader loader, Object data) {

        }

        @Override
        public void onLoaderReset(@NonNull Loader loader) {

        }
    }
}
