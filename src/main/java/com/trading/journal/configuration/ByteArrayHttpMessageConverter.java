package com.trading.journal.configuration;


import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.ArrayList;

public class ByteArrayHttpMessageConverter extends AbstractHttpMessageConverter<ArrayList<byte[]>> {
    public ByteArrayHttpMessageConverter() {
        super(MediaType.IMAGE_PNG);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ArrayList.class.isAssignableFrom(clazz);
    }

    @Override
    protected ArrayList<byte[]> readInternal(Class<? extends ArrayList<byte[]>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Reading not supported");
    }

    @Override
    protected void writeInternal(ArrayList<byte[]> bytes, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        for (byte[] image : bytes) {
            outputMessage.getHeaders().setContentType(MediaType.IMAGE_PNG);
            outputMessage.getBody().write(image);
        }
    }



}
