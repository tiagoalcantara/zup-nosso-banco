package com.tiago.nossobancodigital.modules.files.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tiago.nossobancodigital.shared.errors.AppException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ServeImageService {
  
  public byte[] execute(String fileName) throws AppException {
    Path dirPath = Paths.get("tmp", "uploads");

    try {
      Path imagePath = dirPath.resolve(fileName);
      byte[] image = Files.readAllBytes(imagePath);
      return image;
    } catch (IOException e) {
      throw new AppException(HttpStatus.NOT_FOUND, "Image not found");
    }
  }
}
