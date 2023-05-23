package com.trading.journal.service;

import com.trading.journal.model.Image;
import com.trading.journal.repositoty.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final DealService dealService;

    //TODO: Images folder
//    private final String FOLDER_PATH = "C:/Users/GEO/Desktop/UploadedFiles/";
    private final String FOLDER_PATH = "/home/trading_journal/webapp";

    @Override
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

    @Override
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

    @Override
    public void deleteImage(int id, int imgNumber) {
        Image img = imageRepository.findAllByDeal_Id(id).get(imgNumber);
        imageRepository.deleteById((int) img.getId());
    }

}