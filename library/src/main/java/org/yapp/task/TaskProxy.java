package org.yapp.task;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.yapp.utils.Callback;
import org.yapp.utils.Log;
import org.yapp.y;

import java.util.concurrent.Executor;

/**
 * ClassName: TaskProxy <br>
 * Description: 异步任务的代理类(仅在task包内可用). <br>
 * Date: 2015-12-4 上午10:34:46 <br>
 */
public class TaskProxy<ResultType> extends AbsTask<ResultType> {
    public static final InternalHandler<Object> mHandler = new InternalHandler<>();
    public static final PriorityExecutor mDefaultExecutor = new PriorityExecutor(true);

    private final AbsTask<ResultType> task;
    private final Executor executor;
    private volatile boolean callOnCanceled = false;
    private volatile boolean callOnFinished = false;

    protected TaskProxy(AbsTask<ResultType> task) {
        super(task);
        this.task = task;
        this.task.setTaskProxy(this);
        this.setTaskProxy(null);
        Executor taskExecutor = task.getExecutor();
        if (taskExecutor == null) {
            taskExecutor = mDefaultExecutor;
        }
        this.executor = taskExecutor;
    }

    @Override
    protected final ResultType doBackground() throws Throwable {
        this.onWaiting();
        PriorityRunnable runnable = new PriorityRunnable(
                task.getPriority(),
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 等待过程中取消
                            if (callOnCanceled || TaskProxy.this.isCancelled()) {
                                throw new Callback.CancelledException("");
                            }

                            // start running
                            TaskProxy.this.onStarted();

                            if (TaskProxy.this.isCancelled()) { // 开始时取消
                                throw new Callback.CancelledException("");
                            }

                            // 执行task, 得到结果.
                            task.setResult(task.doBackground());
                            TaskProxy.this.setResult(task.getResult());

                            // 未在doBackground过程中取消成功
                            if (TaskProxy.this.isCancelled()) {
                                throw new Callback.CancelledException("");
                            }

                            // 执行成功
                            TaskProxy.this.onSuccess(task.getResult());
                        } catch (Callback.CancelledException cex) {
                            TaskProxy.this.onCancelled(cex);
                        } catch (Throwable ex) {
                            TaskProxy.this.onError(ex, false);
                        } finally {
                            TaskProxy.this.onFinished();
                        }
                    }
                });
        this.executor.execute(runnable);
        return null;
    }

    /**
     * @see org.yapp.task.AbsTask#onSuccess(Object)
     */
    @Override
    protected void onSuccess(ResultType result) {
        this.setState(State.SUCCESS);
        mHandler.obtainMessage(MSG_WHAT_ON_SUCCESS, this).sendToTarget();
    }

    /**
     * @see org.yapp.task.AbsTask#onError(Throwable, boolean)
     */
    @Override
    protected void onError(Throwable ex, boolean isCallbackError) {
        this.setState(State.ERROR);
        mHandler.obtainMessage(MSG_WHAT_ON_ERROR, new ArgsObj<ResultType>(this, ex)).sendToTarget();
    }

    @Override
    protected void onWaiting() {
        this.setState(State.WAITING);
        mHandler.obtainMessage(MSG_WHAT_ON_WAITING, this).sendToTarget();
    }

    @Override
    protected void onStarted() {
        this.setState(State.STARTED);
        mHandler.obtainMessage(MSG_WHAT_ON_START, this).sendToTarget();
    }

    @Override
    protected void onUpdate(int flag, Object... args) {
        // obtainMessage(int what, int arg1, int arg2, Object obj), arg2 not be used.
        mHandler.obtainMessage(MSG_WHAT_ON_UPDATE, flag, flag, new ArgsObj(this, args)).sendToTarget();
    }

    @Override
    protected void onCancelled(Callback.CancelledException cex) {
        this.setState(State.CANCELLED);
        mHandler.obtainMessage(MSG_WHAT_ON_CANCEL, new ArgsObj<ResultType>(this, cex)).sendToTarget();
    }

    @Override
    protected void onFinished() {
        mHandler.obtainMessage(MSG_WHAT_ON_FINISHED, this).sendToTarget();
    }

    public final void setState(State state) {
        super.setState(state);
        this.task.setState(state);
    }

    @Override
    public final Priority getPriority() {
        return task.getPriority();
    }

    @Override
    public final Executor getExecutor() {
        return this.executor;
    }

    /**
     * ClassName: ArgsObj <br>
     * Description: 参数对象. <br>
     * Date: 2015-12-4 上午11:13:56 <br>
     *
     * @author user
     * @version TaskProxy@param <ResultType>
     * @since JDK 1.7
     */
    private static class ArgsObj<ResultType> {
        final TaskProxy<ResultType> taskProxy;
        final Object[] args;

        public ArgsObj(TaskProxy<ResultType> taskProxy, Object... args) {
            this.taskProxy = taskProxy;
            this.args = args;
        }
    }

    /**
     * 消息参数列表
     **/
    private final static int MSG_WHAT_BASE = 1000000000;
    private final static int MSG_WHAT_ON_WAITING = MSG_WHAT_BASE + 1;
    private final static int MSG_WHAT_ON_START = MSG_WHAT_BASE + 2;
    private final static int MSG_WHAT_ON_SUCCESS = MSG_WHAT_BASE + 3;
    private final static int MSG_WHAT_ON_ERROR = MSG_WHAT_BASE + 4;
    private final static int MSG_WHAT_ON_UPDATE = MSG_WHAT_BASE + 5;
    private final static int MSG_WHAT_ON_CANCEL = MSG_WHAT_BASE + 6;
    private final static int MSG_WHAT_ON_FINISHED = MSG_WHAT_BASE + 7;

    /**
     * ClassName: InternalHandler <br>
     * Description: 内部处理程序. <br>
     * Date: 2015-12-4 上午11:07:41 <br>
     *
     * @author ysj
     * @version TaskProxy@param <ResultType>
     * @since JDK 1.7
     */
    static final class InternalHandler<ResultType> extends Handler {
        private InternalHandler() {
            super(Looper.getMainLooper());
        }

        /**
         * @see Handler#handleMessage(Message)
         */
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj == null) {
                throw new IllegalArgumentException("msg must not be null");
            }
            TaskProxy<ResultType> taskProxy = null;
            Object[] args = null;
            // 对象类型区别处理
            if (msg.obj instanceof TaskProxy) {
                taskProxy = (TaskProxy<ResultType>) msg.obj;
            } else if (msg.obj instanceof ArgsObj) {
                ArgsObj<ResultType> argsObj = (ArgsObj<ResultType>) msg.obj;
                taskProxy = argsObj.taskProxy;
                args = argsObj.args;
            }
            if (taskProxy == null) {
                throw new RuntimeException("msg.obj not instanceof TaskProxy");
            }

            try {
                switch (msg.what) {
                    case MSG_WHAT_ON_WAITING: {
                        taskProxy.task.onWaiting();
                        break;
                    }
                    case MSG_WHAT_ON_START: {
                        taskProxy.task.onStarted();
                        break;
                    }
                    case MSG_WHAT_ON_SUCCESS: {
                        taskProxy.task.onSuccess(taskProxy.getResult());
                        break;
                    }
                    case MSG_WHAT_ON_ERROR: {
                        assert args != null;
                        Throwable throwable = (Throwable) args[0];
                        Log.d(throwable.getMessage(), throwable);
                        taskProxy.task.onError(throwable, false);
                        break;
                    }
                    case MSG_WHAT_ON_UPDATE: {
                        taskProxy.task.onUpdate(msg.arg1, args);
                        break;
                    }
                    case MSG_WHAT_ON_CANCEL: {
                        if (taskProxy.callOnCanceled) return;
                        taskProxy.callOnCanceled = true;
                        assert args != null;
                        taskProxy.task.onCancelled((org.yapp.utils.Callback.CancelledException) args[0]);
                        break;
                    }
                    case MSG_WHAT_ON_FINISHED: {
                        if (taskProxy.callOnFinished) return;
                        taskProxy.callOnFinished = true;
                        taskProxy.task.onFinished();
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } catch (Throwable ex) {
                taskProxy.setState(State.ERROR);
                if (msg.what != MSG_WHAT_ON_ERROR) {
                    taskProxy.task.onError(ex, true);
                } else if (y.isDebug()) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
