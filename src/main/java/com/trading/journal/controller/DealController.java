package com.trading.journal.controller;

import com.trading.journal.currenciesDB.Currencies;
import com.trading.journal.model.Deal;
import com.trading.journal.model.DealTotalList;
import com.trading.journal.service.DealService;
import com.trading.journal.service.DealTotalListService;
import com.trading.journal.service.ImageServiceImpl;
import jakarta.persistence.NonUniqueResultException;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/deal")
@CrossOrigin
@AllArgsConstructor
public class DealController {

    private final DealService dealService;

    private final ImageServiceImpl imageServiceImpl;

    private final DealTotalListService dealTotalListService;

    @GetMapping("/getAllDeals")
    public List<Deal> getAllDeals() {
        return dealService.getAllDeals();
    }

    @GetMapping("/getDeal/{id}")
    public Deal getDeal(@PathVariable int id) {
        return dealService.getDeal(id);
    }

    @PostMapping("/addDeal")
    public String addDeal(@RequestBody Deal deal) throws JSONException, IOException {
        dealService.saveDeal(deal);
        return "New deal added";
    }


    @DeleteMapping("/deleteDeal/{id}")
    public String deleteDeal(@PathVariable int id) {
        dealService.deleteDeal(id);
        return "Deal with ID = " + id + " was deleted";
    }


    @GetMapping("/dropDownList")
    public List<String> getForexCurrencies() {
        List<String> forexCurrencies = Currencies.forexCurrencies;
        return forexCurrencies;
    }

    //change to updateNote
    @PutMapping("/updateDeal/{id}")
    public Deal updateDealNote(@PathVariable int id, @RequestBody Deal deal) {
        dealService.updateDealNoteById(id, deal);
        return deal;
    }

    // Total calculations
    @GetMapping("/getTotalInfo")
    public DealTotalList getTotalInfo () {
        return dealTotalListService.totalDealsMenuCounter();
    }

    ////////////////////////////////////////////////////////////
    ///////////////////////// IMAGES ///////////////////////////
    ////////////////////////////////////////////////////////////

    @PostMapping("/{id}/addImageToDeal")
    public ResponseEntity<?> uploadImageToFileSystem(
            @RequestParam("image") MultipartFile file ,
            @PathVariable int id) throws IOException {

        String uploadImage = imageServiceImpl.uploadImageFromFileSystem(file, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("{id}/getImage/{imgNumber}")
    public ResponseEntity<?> downloadImageFromFileSystem(
            @PathVariable int id, @PathVariable int imgNumber) throws IOException, NonUniqueResultException {

        byte[][] images = imageServiceImpl.downloadImageFromFileSystem(id);

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(images[imgNumber]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}/deleteImage/{imgNumber}")
    public void deleteImage(@PathVariable int id, @PathVariable int imgNumber) {
        imageServiceImpl.deleteImage(id ,imgNumber);
    }

}
