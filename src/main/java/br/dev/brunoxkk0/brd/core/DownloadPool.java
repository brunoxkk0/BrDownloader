package br.dev.brunoxkk0.brd.core;

import br.dev.brunoxkk0.brd.utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloadPool {

    private static final int THREAD_POOL_SIZE = Utils.getCoreCount();
    private static final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static Future<?> queueDownload(DownloadExecutor executor){
        return threadPoolExecutor.submit(executor);
    }


}
