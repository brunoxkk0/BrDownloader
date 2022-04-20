package br.dev.brunoxkk0.brd.core;

import java.util.concurrent.Future;
import java.util.function.Consumer;

public class DownloadHolder {

    private DownloadExecutor downloadExecutor;
    private Future<?> downloadExecutorFuture;

    private Consumer<Exception> exceptionConsumer = null;
    private Consumer<DownloadExecutor> executorConsumer = null;
    private Consumer<DownloadStatus> finishConsumer = null;


    public DownloadExecutor getDownloadExecutor() {
        return downloadExecutor;
    }

    public Future<?> getDownloadExecutorFuture() {
        return downloadExecutorFuture;
    }

    public Consumer<Exception> getExceptionConsumer() {
        return exceptionConsumer;
    }

    public Consumer<DownloadExecutor> getExecutorConsumer() {
        return executorConsumer;
    }

    public Consumer<DownloadStatus> getFinishConsumer() {
        return finishConsumer;
    }

    public void onException(Consumer<Exception> exceptionConsumer){
        this.exceptionConsumer = exceptionConsumer;
    }

    public void onUpdate(Consumer<DownloadExecutor> executorConsumer){
        this.executorConsumer = executorConsumer;
    }

    public void onFinish(Consumer<DownloadStatus> finishConsumer){
        this.finishConsumer = finishConsumer;
    }

    public void setDownloadExecutor(DownloadExecutor downloadExecutor) {
        this.downloadExecutor = downloadExecutor;
    }

    public void setDownloadExecutorFuture(Future<?> downloadExecutorFuture) {
        this.downloadExecutorFuture = downloadExecutorFuture;
    }
}
