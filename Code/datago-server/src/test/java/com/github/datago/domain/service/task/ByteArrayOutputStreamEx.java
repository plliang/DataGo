package com.github.datago.domain.service.task;

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStreamEx extends ByteArrayOutputStream {

    public byte[] getBytes() {
        return buf;
    }
}
