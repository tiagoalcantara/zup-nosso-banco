package com.tiago.nossobancodigital.shared.providers.storage.models;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageProvider {
  String save(MultipartFile file);
}
