package com.yzk.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);
}
