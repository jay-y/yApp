package org.yapp.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import org.yapp.utils.Log;

public final class ViewFinder {
    private View view;
    private Activity activity;
    private Object fragment;

    public static ViewFinder get(Object obj) {
        if (obj instanceof View) {
            return new ViewFinder((View) obj);
        } else if (obj instanceof Activity) {
            return new ViewFinder((Activity) obj);
        } else if (obj instanceof android.app.Fragment || obj instanceof android.support.v4.app.Fragment) {
            return new ViewFinder(obj);
        }
        Log.e("No matching type of finder");
        return null;
    }

    private ViewFinder(View view) {
        this.view = view;
    }

    private ViewFinder(Activity activity) {
        this.activity = activity;
    }

    private ViewFinder(Object fragment) {
        this.fragment = fragment;
    }

    public View findViewById(int id) {
        if (null != view) return view.findViewById(id);
        if (null != activity) return activity.findViewById(id);
        if (null != fragment) {
            if(fragment instanceof android.app.Fragment){
                return ((android.app.Fragment) fragment).getView().findViewById(id);
            }else if(fragment instanceof android.support.v4.app.Fragment){
                return ((android.support.v4.app.Fragment) fragment).getView().findViewById(id);
            }
        }
        Log.e("No matching type");
        return null;
    }

    public View findViewById(int id, int pid) {
        View pView = null;
        if (pid > 0) {
            pView = this.findViewById(pid);
        }

        View view = null;
        if (pView != null) {
            view = pView.findViewById(id);
        } else {
            view = this.findViewById(id);
        }
        return view;
    }

    public Context getContext() {
        if (null != view) return view.getContext();
        if (null != activity) return activity;
        if (null != fragment) {
            if(fragment instanceof android.app.Fragment){
                Log.d("DEBUG:android.app.Fragment");
                return ((android.app.Fragment) fragment).getActivity();
            }else if(fragment instanceof android.support.v4.app.Fragment){
                Log.d("DEBUG:android.support.v4.app.Fragment");
                return ((android.support.v4.app.Fragment) fragment).getActivity();
            }
        }
        return null;
    }
}
