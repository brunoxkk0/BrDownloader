package br.dev.brunoxkk0.brd.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProgressObservableInputStream extends FilterInputStream {

    private final long size;
    private long readCount = 0;

    public double getPercentage() {

        return ((readCount * 100.0) / size);
    }

    public ProgressObservableInputStream(InputStream in, long size) {
        super(in);
        this.size = size;
    }

    @Override
    public int read() throws IOException {

        int count = in.read();

        if (count >= 0) {
            readCount++;
        }

        return count;
    }

    @Override
    public int read(byte[] b) throws IOException {

        int count = in.read(b);

        if (count > 0) {
            readCount += count;
        }

        return count;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int count = in.read(b, off, len);

        if (count > 0) {
            readCount += count;
        }

        return count;
    }

    @Override
    public long skip(long n) throws IOException {

        long count = in.skip(n);

        if (count > 0) {
            readCount += count;
        }

        return count;
    }


    @Override
    public synchronized void reset() throws IOException {
        in.reset();

        readCount = size - in.available();

    }
}