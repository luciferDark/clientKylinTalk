package com.ll.common.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ll.common.R;
import com.ll.common.widget.recycler.RecyclerAdapter;

public class GalleyView extends RecyclerView {
    private static final int GALLEY_VIEW_SPAN_COUNT = 4;
    private Adapter mAdapter = new Adapter();

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
        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListener() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Object o) {

            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Object o) {

            }
        });
    }

    private static class Image {

    }

    public class Adapter extends RecyclerAdapter {

        @Override
        protected ViewHolder createViewHolder(View root, int viewType) {
            return new GalleyView.ViewHolder(root);
        }

        @Override
        protected int getItemViewType(int position, Object o) {
            return R.layout.cell_galley_layout;
        }

        @Override
        public void update(Object o, ViewHolder holder) {

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

}
