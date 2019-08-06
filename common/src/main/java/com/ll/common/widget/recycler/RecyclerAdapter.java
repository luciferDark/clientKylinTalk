package com.ll.common.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ll.common.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Kylin
 * 封装RecyclerAdpater
 */
public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener {
    private final List<Data> mDataList = new ArrayList<>();//View对应的数据集合
    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 约定好viewType是用的Xml的id
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, @LayoutRes int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = createViewHolder(root, viewType);

        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        // 设置Tag绑定holder
        root.setTag(R.id.tag_recycler_adapter_holder, holder);

        //butterKnife 绑定
        holder.mUnbinder = ButterKnife.bind(holder, root);

        return holder;
    }

    protected abstract ViewHolder<Data> createViewHolder(View root, int viewType);

    /**
     * 绑定数据到ViewHolder上
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> holder, int position) {
        if (mDataList == null || mDataList.size() <= 0) {
            return;
        }
        Data data = mDataList.get(position);
        holder.bindData(data);//绑定界面数据
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        protected Data mData;
        private Unbinder mUnbinder;

        private AdapterCallback<Data> adapterCallback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindData(Data data) {
            this.mData = data;
            onBind(data);
        }

        public void setAdapterCallback(AdapterCallback<Data> adapterCallback) {
            this.adapterCallback = adapterCallback;
        }

        /**
         * 数据绑定接口
         * 当绑定数据时会触发回调，需要复写接口
         *
         * @param data
         */
        protected abstract void onBind(Data data);

        /**
         * 更新数据
         *
         * @param data
         */
        public void updateData(Data data) {
            if (this.adapterCallback != null){
                this.adapterCallback.update(data, this);
            }
        }
    }
}
