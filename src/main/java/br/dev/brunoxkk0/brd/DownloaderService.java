package br.dev.brunoxkk0.brd;

import br.dev.brunoxkk0.brd.core.DownloadExecutor;
import br.dev.brunoxkk0.brd.core.DownloadHolder;
import br.dev.brunoxkk0.brd.core.DownloadPool;
import br.dev.brunoxkk0.brd.core.DownloadSubscriber;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.Future;


public class DownloaderService {

    public static DownloadSubscriber Request(String url, String folder){
        return Request(url, new File(folder));
    }

    public static DownloadSubscriber Request(String url, HashMap<String, Object> options, String folder){
        return Request(url, options, new File(folder));
    }

    public static DownloadSubscriber Request(String url, File folder){
        return Request(url, new HashMap<>(), folder);
    }

    public static DownloadSubscriber Request(String url, HashMap<String, Object> options, File folder){
        return download -> download.accept(createDownload(url, options, folder));
    }

    private static DownloadHolder createDownload(String url, HashMap<String, Object> options, File folder) {

        try {

            URL _url = new URL(url);
            URLConnection connection = openConnection(_url, options);

            options.remove("proxy");
            options.forEach((k, v) -> {
                connection.setRequestProperty(k, (String) v);
            });


            String contentDisposition = connection.getHeaderField("content-disposition");
            String filename = "Donwload.any";

            if(contentDisposition != null){
                if(contentDisposition.contains("filename")){
                    filename = contentDisposition.split("filename=")[1];
                }
            }


            File file = new File(folder, filename);
            createNewFile(file);

            long length = connection.getContentLengthLong();

            DownloadHolder downloadHolder = new DownloadHolder();

            DownloadExecutor downloadExecutor = new DownloadExecutor(file, connection.getInputStream(), length, (err) -> {
                if(downloadHolder.getExceptionConsumer() != null)
                    downloadHolder.getExceptionConsumer().accept(err);
            }, (de) -> {
                if(downloadHolder.getExecutorConsumer() != null)
                    downloadHolder.getExecutorConsumer().accept(de);
            }, (ds) -> {
                if(downloadHolder.getFinishConsumer() != null)
                    downloadHolder.getFinishConsumer().accept(ds);
            });

            Future<?> queuedDownload = DownloadPool.queueDownload(downloadExecutor);

            downloadHolder.setDownloadExecutorFuture(queuedDownload);
            downloadHolder.setDownloadExecutor(downloadExecutor);

            return downloadHolder;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static URLConnection openConnection(URL url, HashMap<String, Object> options) throws IOException {

        if(options.containsKey("proxy")){
            return url.openConnection((Proxy) options.get("proxy"));
        }

        return url.openConnection();
    }

    private static void createNewFile(File file) throws IOException {

        if(!file.exists()){

            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            file.createNewFile();

        }
    }

}
