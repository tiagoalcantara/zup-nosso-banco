package com.tiago.nossobancodigital.shared.providers.storage.implementations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.tiago.nossobancodigital.shared.providers.storage.models.IStorageProvider;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DiskStorageProvider implements IStorageProvider {

  public String save(MultipartFile file) {
    String generatedFileName = String.format("%s-%s", UUID.randomUUID(), file.getOriginalFilename());
    Path dirPath = Paths.get("tmp", "uploads");
    Path filePath = dirPath.resolve(generatedFileName);
    
    try {
      Files.createDirectories(dirPath);
      byte[] fileBytes = file.getBytes();
      Files.write(filePath, fileBytes);
    } catch (IOException e) {
      throw new RuntimeException("Could not upload file");
    }

    return String.format("http://localhost:4000/files/images/%s", generatedFileName);
  }
}
