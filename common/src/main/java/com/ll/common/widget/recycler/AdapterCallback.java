package com.ll.common.widget.recycler;

/**
 * @author  kylin
 * RecyclerView 回调
 */
public interface AdapterCallback<Data> {
    /**
     * RecyclerView 更新回调
     * @param data
     * @param holder
     */
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
