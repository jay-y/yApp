package org.yapp.core.ui.adapter;

import android.view.View;

import org.yapp.view.ViewInjector;

/**
 * Description: BaseViewHolder. <br>
 * Date: 2016/4/1 15:05 <br>
 * Author: ysj
 */
public class BaseViewHolder{

    public BaseViewHolder(View itemView) {
        ViewInjector.inject(itemView,this);
    }
}
