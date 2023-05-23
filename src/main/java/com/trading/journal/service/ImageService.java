package com.trading.journal.service;

import com.trading.journal.model.Image;
import com.trading.journal.repositoty.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private DealService dealService;


    private final String FOLDER_PATH = "C:/Users/GEO/Desktop/UploadedFiles/";

    public String uploadImageFromFileSystem(MultipartFile file, int id) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Image image = imageRepository.save(Image
                .builder()
                .screenName(file.getOriginalFilename())
                .screenType(file.getContentType())
                .screenFilePath(filePath)
                .deal(dealService.getDeal(id)).build() );

        file.transferTo(new File(filePath));

        if (image != null && dealService.getDeal(id) != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    public byte[][] downloadImageFromFileSystem(int id) throws IOException {
        List<Image> fileData = imageRepository.findAllByDeal_Id(id);

        byte[][] allImages = new byte[fileData.size()][];

        for (int i = 0; i < fileData.size(); i++) {
            String filePath = fileData.get(i).getScreenFilePath();
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            allImages[i] = images;
        }

        return allImages;
    }

    public void deleteImage(int id, int imgNumber) {
        Image img = imageRepository.findAllByDeal_Id(id).get(imgNumber);
        imageRepository.deleteById((int) img.getId());
    }


}