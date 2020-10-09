package com.tiago.nossobancodigital.modules.files.controllers;

import com.tiago.nossobancodigital.modules.files.services.ServeImageService;
import com.tiago.nossobancodigital.shared.errors.AppException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/files")
public class FilesController {
  private final ServeImageService serveImage;

  public FilesController(ServeImageService serveImage){
    this.serveImage = serveImage;
  }

  @GetMapping("images/{fileName}")
  public ResponseEntity<byte[]> show(@PathVariable String fileName) {
    try {
      MediaType contentType = null;
      if(fileName.endsWith(".png")){
        contentType = MediaType.IMAGE_PNG;
      } else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
        contentType = MediaType.IMAGE_JPEG;
      }

      byte[] image = this.serveImage.execute(fileName);
    return ResponseEntity.ok().contentType(contentType).body(image);
    } catch (AppException e) {
      throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
    
  }
}
