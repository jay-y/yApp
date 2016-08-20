package org.yapp;

import android.app.Application;

import org.yapp.db.Config;
import org.yapp.db.DbManagerImpl;
import org.yapp.ex.ExceptionManagerImpl;
import org.yapp.task.TaskManagerImpl;

/**
 * Description: 控制中心.(需要在在application的onCreate中初始化: y.Core.init(this);). <br>
 * Date: 2015-12-2 下午3:02:04 <br>
 * Author: ysj
 */
public class y {
    public static boolean isDebug() {
        return Core.debug;
    }

    public static void setDebug(boolean debug) {
        Core.debug = debug;
    }

    public static Application app() {
        if (Core.app == null) {
            throw new RuntimeException(
                    "Please invoke y.Core.init(app) on Application#onCreate()");
        }
        return Core.app;
    }

    public static TaskManager task() {
        if (Core.mTaskManager == null) {
            TaskManagerImpl.registerInstance();
        }
        return Core.mTaskManager;
    }

    public static ExceptionManager ex() {
        if (Core.mExceptionManager == null) {
            ExceptionManagerImpl.registerInstance();
        }
        return Core.mExceptionManager;
    }

    public static DbManager getDb(Config daoConfig) {
        return DbManagerImpl.getInstance(daoConfig);
    }

    private y() {
    }

    public static class Core {
        private static boolean debug = false;
        private static Application app;
        private static TaskManager mTaskManager;
        private static ExceptionManager mExceptionManager;

        private Core() {
        }

        /**
         * init:(初始化). <br>
         *
         * @author ysj
         * @param app
         * @since JDK 1.7 date: 2015-12-2 下午4:36:30 <br>
         */
        public static void init(Application app) {
            if (Core.app == null) {
                Core.app = app;
            }
        }

        public static void setTaskManager(TaskManager taskManager) {
            Core.mTaskManager = taskManager;
        }

        public static void setExceptionManager(ExceptionManager exceptionManager) {
            Core.mExceptionManager = exceptionManager;
        }
    }
}
