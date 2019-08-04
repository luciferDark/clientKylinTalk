package com.ll.common.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ll.common.R;
import com.ll.common.app.widget.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Kylin
 * 封装RecyclerAdpater
 */
public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data>{
    private final List<Data> mDataList = new ArrayList<>();//View对应的数据集合
    private AdapterListener adapterListener = null;

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

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

        // 设置Tag绑定holder
        root.setTag(R.id.tag_recycler_adapter_holder, holder);

        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        //butterKnife 绑定
        holder.mUnbinder = ButterKnife.bind(holder, root);
        // 绑定callback
        holder.adapterCallback = this;
        return holder;
    }

    /**
     * 创建ViewHolder回调
     * @param root
     * @param viewType
     * @return
     */
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

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 获取布局类型
     * @param position
     * @param data
     * @return xml的一个id
     */
    protected abstract int getItemViewType(int position, Data data);

    /*********************事件点击处理*********************/
    @Override
    public void onClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_adapter_holder);
        if (adapterListener != null){
            adapterListener.onItemClick(viewHolder, viewHolder.mData);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_adapter_holder);
        if (adapterListener != null){
            adapterListener.onItemLongClick(viewHolder, viewHolder.mData);
        }
        return false;
    }

    /**
     * VIewItem  点击和长按事件接口
     * @param <Data>
     */
    public interface AdapterListener<Data>{
        void onItemClick(ViewHolder holder, Data data);
        void onItemLongClick(ViewHolder holder, Data data);
    }

    /*********************事件点击处理*********************/
    /*********************数据操作封装*********************/
    /**
     * 插入单条数据
     * @param data
     */
    public void addData(Data data){
        mDataList.add(data);
        notifyItemChanged(mDataList.size() - 1);
    }

    /**
     * 插入数据集合
     * @param dataList
     */
    public void addData(Data... dataList){
        if (mDataList == null){
            return;
        }
        if (dataList != null && dataList.length > 0){
            int startPosition = mDataList.size();
            Collections.addAll(mDataList, dataList);

            notifyItemRangeChanged(startPosition, dataList.length);
        }
    }

    /**
     * 插入数据集合
     * @param dataList
     */
    public void addData(Collection<Data> dataList){
        if (mDataList == null){
            return;
        }
        if (dataList != null && dataList.size() > 0){
            int startPosition = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPosition, dataList.size());
        }
    }

    /**
     * 清理数据集合
     */
    public void clearData(){
        if (mDataList == null){
            return;
        }
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 整体替换数据集合
     * @param dataList
     */
    public void modifyData(Collection<Data> dataList){
        if (mDataList == null){
            return;
        }
        if (dataList == null || dataList.size() <= 0){
            return;
        }
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }
    /*********************数据操作封装*********************/

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
