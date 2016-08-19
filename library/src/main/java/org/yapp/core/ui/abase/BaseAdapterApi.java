package org.yapp.core.ui.abase;

import java.util.List;

/**
 * Description: 适配器基础接口. <br>
 * Date: 2016/4/1 9:46 <br>
 * Author: ysj
 */
public interface BaseAdapterApi<T>{

    /**
     * clear list
     */
    void clear();

    /**
     * before add data , it will remove history data
     *
     * @param dataList
     */
    void update(List<T> dataList);

    /**
     * add data append to history data
     *
     * @param dataList new dataList
     */
    void addAll(List<T> dataList);

    /**
     * add data append to history data
     *
     * @param data
     */
    void add(T data);

    /**
     * get data list
     *
     * @return
     */
    List<T> get();

    /**
     * get a data
     *
     * @param pos
     * @return
     */
    T get(int pos);
}
