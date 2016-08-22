package org.yapp.core.ui.fragment;

import org.yapp.utils.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: Fragment工厂. <br>
 * Date: 2016/1/26 19:58 <br>
 * Author: ysj
 */
public class FragmentFactory {
    private static final Object lock = new Object();
    private static FragmentFactory instance;
    //Fragment注册集合
    private Map<Integer,android.app.Fragment> REGISTER = new HashMap<>();
    //V4Fragment注册集合
    private Map<Integer,android.support.v4.app.Fragment> REGISTERV4 = new HashMap<>();
    //注册ID
    private Map<Integer,Integer> REGISTER_ids = new HashMap<>();
    //记录条数
    private Integer count = 0;

    /**
     * getInstance:(获取实例). <br>
     *
     * @author ysj
     * @return Fragment
     * @since JDK 1.7
     * date: 2015-6-24 下午5:48:12 <br>
     */
    public static FragmentFactory getInstance(){
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new FragmentFactory();
                }
            }
        }
        return instance;
    }

    /**
     * newInstance:(创建新实例). <br>
     *
     * @return this
     */
    public static FragmentFactory newInstance(){
        return new FragmentFactory();
    }

    /**
     * releaseInstance:(释放实例). <br>
     *
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:50:21 <br>
     */
    public static void releaseInstance(){
        instance = null;
    }

    public android.app.Fragment getFragment(int code){
        if(null == REGISTER.get(code))
            throw new RuntimeException("Please register the Fragment first");
        return REGISTER.get(code);
    }

    public android.support.v4.app.Fragment getV4Fragment(int code){
        if(null == REGISTERV4.get(code))
            throw new RuntimeException("Please register the Fragment first");
        return REGISTERV4.get(code);
    }

    /**
     * 获取注册时使用ID
     * @param index
     * @return
     */
    public int getId(int index){
        return REGISTER_ids.get(index);
    }

    /**
     * 获取组件注册总数
     * @return
     */
    public int getCount(){
        return count;
    }

    /**
     * 注册Fragment
     * @param code
     * @param fragment
     */
    public void registerFragment(int code,Object fragment){
        synchronized (count){
            if(REGISTER_ids.containsValue(code))
                throw new RuntimeException("Fragment already registered");
            REGISTER_ids.put(count,code);
            if(fragment instanceof android.app.Fragment){
                REGISTER.put(code, (android.app.Fragment) fragment);
                count++;
            }else if(fragment instanceof android.support.v4.app.Fragment){
                REGISTERV4.put(code, (android.support.v4.app.Fragment) fragment);
                count++;
            }
            Log.d("Fragment register count:"+count);
        }
    }

    /**
     * 释放
     */
    public void releaseFragment(){
        synchronized (count){
            for (int index = 0;index < count;index++){
                REGISTER.remove(REGISTER_ids.get(index));
                REGISTERV4.remove(REGISTER_ids.get(index));
				REGISTER_ids.remove(index);
            }
            count = 0;
        }
    }
}
