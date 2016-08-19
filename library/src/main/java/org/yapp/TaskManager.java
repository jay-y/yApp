package org.yapp;

import org.yapp.task.AbsTask;
import org.yapp.utils.Callback;

/**
 * ClassName: TaskManager <br>
 * Description: 任务管理接口. <br> 
 * Date: 2015-12-4 上午10:33:52 <br> 
 * Author: ysj
 */
public interface TaskManager {

    /**
     * 在UI线程执行runnable.
     * 如果已在UI线程, 则直接执行.
     *
     * @param runnable
     */
    void autoPost(Runnable runnable);

    /**
     * 在UI线程执行runnable.
     * post到msg queue.
     *
     * @param runnable
     */
    void post(Runnable runnable);

    /**
     * 在UI线程执行runnable.
     *
     * @param runnable
     * @param delayMillis 延迟时间(单位毫秒)
     */
    void postDelayed(Runnable runnable, long delayMillis);

    /**
     * 在后台线程执行runnable
     *
     * @param runnable
     */
    void run(Runnable runnable);

    /**
     * 移除post或postDelayed提交的, 未执行的runnable
     *
     * @param runnable
     */
    void removeCallbacks(Runnable runnable);

    /**
     * 开始一个异步任务
     *
     * @param task
     * @param <T>
     * @return
     */
    <T> AbsTask<T> start(AbsTask<T> task);

    /**
     * 同步执行一个任务
     *
     * @param task
     * @param <T>
     * @return
     * @throws Throwable
     */
    <T> T startSync(AbsTask<T> task) throws Throwable;

    /**
     * 批量执行异步任务
     *
     * @param groupCallback
     * @param tasks
     * @param <T>
     * @return
     */
    <T extends AbsTask<?>> Callback.Cancelable startTasks(Callback.GroupCallback<T> groupCallback, @SuppressWarnings("unchecked") T... tasks);
}
