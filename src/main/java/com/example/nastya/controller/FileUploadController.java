package com.example.nastya.controller;

import com.example.nastya.entity.Reviews;
import com.example.nastya.entity.model.Error;
import com.example.nastya.service.ReviewsService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Hidden
@RestController
public class FileUploadController {

    @Autowired
    private ReviewsService reviewsService;

    @RequestMapping(value="/upload/event/{idE}/user/{idU}", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> handleFileUpload(@RequestParam("description") String description,
                                                            @RequestParam("file") MultipartFile file,
                                                            @PathVariable("idE") long idE, @PathVariable("idU") long idU){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(".\\src\\main\\resources\\static\\images\\" + file.getOriginalFilename()))); //.\src\main\resources\static\images\
                stream.write(bytes);
                stream.close();

                return new ResponseEntity<Reviews>(reviewsService.addReview(idE, idU, new Reviews(description, file.getOriginalFilename())), HttpStatus.OK);
            } catch (Exception e) {
                return null;
            }
        } else {
            return new ResponseEntity<Reviews>(reviewsService.addReview(idE, idU, new Reviews(description, null)), HttpStatus.OK);
        }
    }
}
