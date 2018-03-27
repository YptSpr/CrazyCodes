package com.example.spr_ypt.crazycodes.thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MoliveThreadPool extends ThreadPoolExecutor {
    private final static int SIZE_POOL_CORE = 2;
    private final static int SIZE_POLL_MAX = 10;
    private final static int TIME_KEEP_ALIVE = 2;
    private final static TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private MoliveThreadPool() {
        super(SIZE_POOL_CORE, SIZE_POLL_MAX, TIME_KEEP_ALIVE, TIME_UNIT, new LinkedBlockingQueue<Runnable>(), new RejectedHandler());
    }

    public MoliveThreadPool(int min, int max) {
        super(min, max, TIME_KEEP_ALIVE, TIME_UNIT, new LinkedBlockingQueue<Runnable>(), new RejectedHandler());
    }

    public MoliveThreadPool(int min, int max, int keeptime) {
        super(min, max, keeptime, TIME_UNIT, new LinkedBlockingQueue<Runnable>(), new RejectedHandler());
    }

    public MoliveThreadPool(int min, int max, RejectedExecutionHandler executionHandler) {
        super(min, max, TIME_KEEP_ALIVE, TIME_UNIT, new LinkedBlockingQueue<Runnable>(), executionHandler);
    }

    private static class RejectedHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            String imageId = "";

        }
    }

    private static ThreadPoolExecutor globalPool = null;
    private static ThreadPoolExecutor bubblePool = null;

    private static ThreadPoolExecutor msgMapPool = null;

    private static ThreadPoolExecutor messagePoll = null;
    private static ThreadPoolExecutor logPool = null;

    private static ThreadPoolExecutor frameAnimationPool = null;

    private static ThreadPoolExecutor asyncInitPool = null;

    private static ThreadPoolExecutor audioAnimator;

    /**
     * 获取一个全新的线程池对象
     *
     * @return
     */
    public static ThreadPoolExecutor getNewThreadPool() {
        return new MoliveThreadPool();
    }

    /**
     * 获取一个全局静态的线程池对象
     *
     * @return
     */
    public static ThreadPoolExecutor getGlobalThreadPool() {
        if (globalPool == null) {
            globalPool = new MoliveThreadPool(10, 10);
        }
        return globalPool;
    }

    public static ThreadPoolExecutor getBubbleThreadPool() {
        if (bubblePool == null) {
            bubblePool = new MoliveThreadPool(10, 10);
        }
        return bubblePool;
    }

    /**
     * 获取一个聊天界面地图消息的线程池对象
     *
     * @return
     */
    public static ThreadPoolExecutor getMsgMapThreadPool() {
        if (msgMapPool == null) {
            msgMapPool = new MoliveThreadPool(3, 3);
        }
        return msgMapPool;
    }

    public static ThreadPoolExecutor getLogThreadPool() {
        if (logPool == null) {
            logPool = new MoliveThreadPool(5, 10);
        }
        return logPool;
    }

    public static ThreadPoolExecutor getFrameAnimationThreadPoll() {
        if (frameAnimationPool == null) {
            frameAnimationPool = new MoliveThreadPool(1, 1);
        }
        return frameAnimationPool;
    }

    /**
     * 获取聊天消息的线程池对象
     */
    public static ThreadPoolExecutor getMessageThreadPool() {
        if (messagePoll == null) {
            messagePoll = new MoliveThreadPool(5, 10);
        }
        return messagePoll;
    }

    public static void shutdownMsgMapThreadPool() {
        if (msgMapPool != null) {
            try {
                msgMapPool.shutdownNow();
            } catch (Exception e) {
            }
            msgMapPool = null;
        }
    }

    public static ThreadPoolExecutor getAsyncInitThreadPool() {
        if (asyncInitPool == null) {
            asyncInitPool = new MoliveThreadPool(4, 10);
        }
        return asyncInitPool;
    }

    private static ThreadPoolExecutor singleGlobalPool = null;

    public static ThreadPoolExecutor getGlobalSingleThreadPool() {
        if (singleGlobalPool == null) {
            singleGlobalPool = new MoliveThreadPool(1, 1);
        }
        return singleGlobalPool;
    }

    public static ThreadPoolExecutor getSingleThreadPool() {
        return new MoliveThreadPool(1, 1);
    }

    private static ThreadPoolExecutor httpPool = null;

    public static ThreadPoolExecutor getHttpImagePool() {
        if (httpPool == null) {
            httpPool = new MoliveThreadPool(2, 2);
        }
        return httpPool;
    }

    private static ExecutorService localPool = null;

    public static ExecutorService getLocalImagePool() {
        if (localPool == null) {
            int c = Runtime.getRuntime().availableProcessors();
            localPool = new MoliveThreadPool(c, c);
        }
        return localPool;
    }

    private static ThreadPoolExecutor profileLocalAvatarPool = null;

    public static ThreadPoolExecutor getProfileLocalAvatarPool() {
        if (profileLocalAvatarPool == null) {
            profileLocalAvatarPool = new MoliveThreadPool(1, 1);
        }
        return profileLocalAvatarPool;
    }

    private static ThreadPoolExecutor profileHttpAvatarPool = null;

    public static ThreadPoolExecutor getProfileHttpAvatarPool() {
        if (profileHttpAvatarPool == null) {
            profileHttpAvatarPool = new MoliveThreadPool(3, 3);
        }
        return profileHttpAvatarPool;
    }

    public static ThreadPoolExecutor getAudioAnimatorPool() {
        if (audioAnimator == null) {
            audioAnimator = new MoliveThreadPool(9, 9);
        }

        return audioAnimator;
    }

    public static void reset() {
        if (profileLocalAvatarPool != null) {
            try {
                profileLocalAvatarPool.shutdownNow();
            } catch (Exception e) {
            }
            profileLocalAvatarPool = null;
        }

        if (profileHttpAvatarPool != null) {
            try {
                profileHttpAvatarPool.shutdownNow();
            } catch (Exception e) {
            }
            profileHttpAvatarPool = null;
        }

        if (globalPool != null) {
            try {
                globalPool.shutdownNow();
            } catch (Exception e) {
            }
            globalPool = null;
        }
        if (bubblePool != null) {
            try {
                bubblePool.shutdownNow();
            } catch (Exception e) {
            }
            bubblePool = null;
        }
        if (msgMapPool != null) {
            try {
                msgMapPool.shutdownNow();
            } catch (Exception e) {
            }
            msgMapPool = null;
        }
        if (messagePoll != null) {
            try {
                messagePoll.shutdownNow();
            } catch (Exception e) {
            }
            messagePoll = null;

        }
        if (asyncInitPool != null) {
            try {
                asyncInitPool.shutdown();
            } catch (Exception e) {
            }
            asyncInitPool = null;
        }
        if (logPool != null) {
            try {
                logPool.shutdown();
            } catch (Exception e) {

            }
            logPool = null;
        }

        if (frameAnimationPool != null) {
            try {
                frameAnimationPool.shutdown();
            } catch (Exception e) {

            }
            frameAnimationPool = null;
        }

        if (audioAnimator != null) {
            try {
                audioAnimator.shutdown();
            } catch (Exception e) {

            }
            audioAnimator = null;
        }
    }

    public static void shutDownHttpImage(){
        if (httpPool != null) {
            try {
                httpPool.shutdownNow();
            } catch (Exception e) {
            }
            httpPool = null;
        }
    }

}
