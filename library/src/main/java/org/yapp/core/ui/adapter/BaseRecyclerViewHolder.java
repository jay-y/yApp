package org.yapp.core.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.yapp.view.ViewInjector;

/**
 * Description: BaseRecyclerHolder. <br>
 * Date: 2016/4/1 15:05 <br>
 * Author: ysj
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder{

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        ViewInjector.inject(itemView,this);
    }
}
