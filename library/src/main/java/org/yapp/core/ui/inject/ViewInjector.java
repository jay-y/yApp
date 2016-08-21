package org.yapp.core.ui.inject;

import android.view.View;

import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Log;

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
     * @param root
     * @param pattern
     */
    public static void inject(Object root,Object pattern){
        ViewFinder finder = ViewFinder.get(root);
        injectObject(pattern,finder);
    }

    private static void injectObject(Object pattern,ViewFinder finder) {
        Class<?> clazz = pattern.getClass();
        if (clazz.getClass() == null) {
            Log.e("Inject null");
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            ViewInject inject = field.getAnnotation(ViewInject.class);
            if (inject != null) {
                try {
                    View view = finder.findViewById(inject.value());
                    if (view != null) {
                        field.setAccessible(true);
                        field.set(pattern, view);
                    } else {
                        throw new RuntimeException("Invalid id(" + inject.value() + ") for @ViewInject!"
                                + clazz.getSimpleName());
                    }
                } catch (Throwable ex) {
                    Log.e(ex.getMessage(), ex);
                }
            }
        }
    }
}
