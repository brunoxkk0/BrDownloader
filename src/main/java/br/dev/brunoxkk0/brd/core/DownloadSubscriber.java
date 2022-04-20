package br.dev.brunoxkk0.brd.core;

import java.util.function.Consumer;

@FunctionalInterface
public interface DownloadSubscriber {

    void subscribe(Consumer<DownloadHolder> download);

}
