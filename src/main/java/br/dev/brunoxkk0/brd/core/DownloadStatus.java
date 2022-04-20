package br.dev.brunoxkk0.brd.core;

public class DownloadStatus {

    private final StatusEnum statusEnum;
    private final long elapsedTime;

    public DownloadStatus(StatusEnum statusEnum, long elapsedTime){
        this.statusEnum = statusEnum;
        this.elapsedTime = elapsedTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }
}
