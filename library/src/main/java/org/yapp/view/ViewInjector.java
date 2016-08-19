package org.yapp.view;

import android.view.View;

import org.yapp.utils.Log;
import org.yapp.view.annotation.ViewInject;

import java.lang.reflect.Field;

/**
 * Description: 注入. <br>
 * Date: 2016/3/30 11:56 <br>
 * Author: ysj
 */
public class ViewInjector {
    /**
     * 注入(仅支持View,Activity,Fragment)
     *
     * @param root
     */
    public static void inject(Object root){
        inject(root, root);
    }

    /**
     * 注入(适用于大部分非View,Activity,Fragment继承类)
     *
     * @param handler
     */
    public static void inject(Object root,Object handler){
        ViewFinder finder = ViewFinder.get(root);
        injectObject(handler, finder);
    }

    private static void injectObject(Object handler, ViewFinder finder) {
        Class<?> clazz = handler.getClass();
        if (clazz.getClass() == null) {
            Log.e("Inject null");
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                try {
                    View view = finder.findViewById(viewInject.value());
                    if (view != null) {
                        field.setAccessible(true);
                        field.set(handler, view);
                    } else {
                        throw new RuntimeException("Invalid id(" + viewInject.value() + ") for @ViewInject!"
                                + clazz.getSimpleName());
                    }
                } catch (Throwable ex) {
                    Log.e(ex.getMessage(), ex);
                }
            }
        }
    }
}
