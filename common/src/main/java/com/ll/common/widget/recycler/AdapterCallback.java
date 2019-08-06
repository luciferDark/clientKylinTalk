package com.ll.common.widget.recycler;

/**
 * @author  kylin
 * RecyclerView 回调
 */
public interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
