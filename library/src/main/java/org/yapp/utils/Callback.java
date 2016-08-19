package org.yapp.utils;

/**
 * ClassName: Callback <br>
 * Description: 通用回调接口. <br>
 * Date: 2015-12-2 下午2:32:52 <br>
 * Author: ysj
 */
public interface Callback {

	/**
	 * 通用回调
	 *
	 * @param <ResultType>
	 */
	public interface CommonCallback<ResultType> extends Callback {
		void onSuccess(ResultType result);

		void onError(Throwable ex, boolean isOnCallback);

		void onCancelled(CancelledException cex);

		void onFinished();
	}

	public interface GroupCallback<ItemType> extends Callback {
		void onSuccess(ItemType item);

		void onError(ItemType item, Throwable ex, boolean isOnCallback);

		void onCancelled(ItemType item, CancelledException cex);

		void onFinished(ItemType item);

		void onAllFinished();
	}

	/**
	 * 处理回调接口
	 */
	public interface HandlerCallback<ResultType> extends Callback{
		void onHandle(ResultType result);
	}

	/**
	 * 执行回调接口
	 */
	public interface ExecCallback extends Callback{
		void run();
	}

	/**
	 * 取消回调接口
	 */
	public interface Cancelable {
		void cancel();

		boolean isCancelled();
	}

	/**
	 * Dialog处理回调
	 */
	public interface DialogCallback{
		void onPositive();

		void onNegative();
	}

	public static class CancelledException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public CancelledException(String detailMessage) {
			super(detailMessage);
		}
	}
}
