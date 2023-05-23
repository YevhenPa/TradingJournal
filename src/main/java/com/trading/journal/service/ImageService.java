package com.trading.journal.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    public String uploadImageFromFileSystem(MultipartFile file, int id) throws IOException;

    public byte[][] downloadImageFromFileSystem(int id) throws IOException;

    public void deleteImage(int id, int imgNumber);
}
