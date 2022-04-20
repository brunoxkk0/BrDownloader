package br.dev.brunoxkk0.brd.core;

import br.dev.brunoxkk0.brd.utils.ProgressObservableInputStream;

import java.io.*;
import java.util.function.Consumer;

public class DownloadExecutor extends Thread{

    private final File file;
    private final ProgressObservableInputStream inputStream;

    private final Consumer<Exception> exceptionConsumer;
    private final Consumer<DownloadExecutor> updateConsumer;
    private final Consumer<DownloadStatus> finishConsumer;

    public DownloadExecutor(File file, InputStream stream, long length, Consumer<Exception> exceptionConsumer, Consumer<DownloadExecutor> updateConsumer, Consumer<DownloadStatus> finishConsumer){
        this.file = file;
        this.inputStream = new ProgressObservableInputStream(stream, length);
        this.exceptionConsumer = exceptionConsumer;
        this.updateConsumer = updateConsumer;
        this.finishConsumer = finishConsumer;
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();

        try {

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[4096];

            while (inputStream.read(buffer) != -1 && !Thread.currentThread().isInterrupted()){
                bufferedOutputStream.write(buffer);
                updateConsumer.accept(this);
            }

            inputStream.close();
            bufferedOutputStream.close();

            long elapsedTime = (System.currentTimeMillis() - startTime);

            if(Thread.currentThread().isInterrupted()){
                finishConsumer.accept(new DownloadStatus(StatusEnum.CANCELED, (elapsedTime)));
                return;
            }

            finishConsumer.accept(new DownloadStatus(StatusEnum.SUCCESS, (elapsedTime)));

        } catch (IOException e) {

            long elapsedTime = (System.currentTimeMillis() - startTime);

            exceptionConsumer.accept(e);
            finishConsumer.accept(new DownloadStatus(StatusEnum.ERROR, (elapsedTime)));
        }

    }

    public double getPercentage() {
        return inputStream.getPercentage();
    }

}
