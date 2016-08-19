package org.yapp.core.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yapp.core.Application;
import org.yapp.core.presenter.BasePresenter;
import org.yapp.core.ui.abase.BaseFragmentApi;
import org.yapp.view.ViewInjector;
import org.yapp.y;

/**
 * ClassName: BaseFragment <br> 
 * Description: 公共Fragment 通一编码规范. <br> 
 * Date: 2015-6-15 下午5:44:09 <br> 
 * 
 * @author ysj 
 * @version  1.0
 * @since JDK 1.7
 */
public abstract class BaseV4Fragment<P extends BasePresenter> extends Fragment implements BaseFragmentApi {
	protected Application app;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState){
		View view = inflater.inflate(getLayoutId(), container, false);
		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	@Override
	public void onDestroyView() {
		app = null;
		onClear();
		super.onDestroyView();
	}

	/**
	 * startActivity
	 *
	 * @param clazz
	 */
	protected void go(Class<?> clazz) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivity(intent);
	}

	/**
	 * startActivity with bundle
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void go(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/**
	 * startActivity with bundle and delayed
	 *
	 * @param clazz 需要跳转的Activity
	 * @param bundle 携带数据
	 * @param delayed 延迟加载时间
	 */
	protected void go(Class<?> clazz, Bundle bundle,int delayed) {
		final Intent intent = new Intent(getActivity(), clazz);
		if(bundle!=null) intent.putExtras(bundle);
		y.task().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(intent);
			}
		}, delayed * 1000);
	}

	/**
	 * startActivity then finish
	 *
	 * @param clazz
	 */
	protected void goThenKill(Class<?> clazz) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivity(intent);
		getActivity().finish();
	}

	/**
	 * startActivity with bundle then finish
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void goThenKill(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		getActivity().finish();
	}

	/**
	 * startActivity with bundle and delayed then finish
	 *
	 * @param clazz 需要跳转的Activity
	 * @param bundle 携带数据
	 * @param delayed 延迟加载时间
	 */
	protected void goThenKill(Class<?> clazz, Bundle bundle,int delayed) {
		final Activity parent = getActivity();
		final Intent intent = new Intent(parent, clazz);
		if(bundle!=null) intent.putExtras(bundle);
		y.task().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(intent);
				parent.finish();
			}
		}, delayed * 1000);
	}

	/**
	 * startActivityForResult
	 *
	 * @param clazz
	 * @param requestCode
	 */
	protected void goForResult(Class<?> clazz, int requestCode) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivityForResult(intent, requestCode);
	}

	/**
	 * startActivityForResult with bundle
	 *
	 * @param clazz
	 * @param requestCode
	 * @param bundle
	 */
	protected void goForResult(Class<?> clazz, int requestCode, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}

	/**
	 * startActivityForResult with bundle and delayed
	 *
	 * @param clazz 需要跳转的Activity
	 * @param bundle 携带数据
	 * @param delayed 延迟加载时间
	 */
	protected void goForResult(Class<?> clazz,final int requestCode,Bundle bundle,int delayed) {
		final Intent intent = new Intent(getActivity(), clazz);
		if(bundle!=null) intent.putExtras(bundle);
		y.task().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivityForResult(intent, requestCode);
			}
		}, delayed * 1000);
	}

	/**
	 * init:(初始化). <br>
	 *
	 * @author ysj
	 * @since JDK 1.7
	 * date: 2015-12-13 下午10:49:04 <br>
	 */
	private void init(){
		if(app == null)app = (Application) getActivity().getApplication();
		ViewInjector.inject(this);
		onBuild();
		onInit();
	}
}
