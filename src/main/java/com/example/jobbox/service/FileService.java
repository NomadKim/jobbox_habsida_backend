package com.example.jobbox.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.jobbox.config.util.FileUtil;
import com.example.jobbox.model.File;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

//    private FileUtil fileStorage;
    private final FileRepository repository;
    //private final AmazonS3 s3client;

//    @Value("${amazon.s3.endpoint-url}")
//    private String endpointUrl;
//    @Value("${amazon.s3.bucket-name}")
//    private String bucketName;


    @Transactional
    public File upload(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String type = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
        String fileName = getUUID() + "." + type;


//        String path = fileStorage.uploadFile(file, fileName);
        String path = uploadFile(file, fileName);

        File fileDB = new File();
        fileDB.setPath(path);
        fileDB.setCreated_at(LocalDateTime.now());
        fileDB.setOriginalTitle(originalFileName);
        fileDB.setType(type);
//        fileDB.setCreated_at(LocalDateTime.now());

        return repository.saveAndFlush(fileDB);
    }
    public void delete(Long id) {
        File file = getById(id);

        repository.deleteById(id);
//        return fileStorage.deleteFile(file.getPath());
        deleteFileFromS3Bucket(file.getPath());
    }

    public List<File> getAllByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)){
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<File> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<File>();
        }
    }

    public File getById(Long id) {
        File file = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
//        String newPath = endpointUrl + file.getPath();
//        file.setPath(newPath);
        return file;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    //AWS
    private java.io.File convertMultiPartToFile(MultipartFile file) throws IOException {
        java.io.File convFile = new java.io.File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    private void uploadFileTos3bucket(String fileName, java.io.File file) {
//        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile, String fileName) {
        try {
            java.io.File file = convertMultiPartToFile(multipartFile);
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public void deleteFileFromS3Bucket(String fileUrl) {
//        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}
