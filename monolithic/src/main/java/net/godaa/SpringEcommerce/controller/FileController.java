package net.godaa.SpringEcommerce.controller;

import net.godaa.SpringEcommerce.domain.File;
import net.godaa.SpringEcommerce.domain.payload.FileInfo;
import net.godaa.SpringEcommerce.domain.payload.ResponseFile;
import net.godaa.SpringEcommerce.domain.payload.ResponseMessage;
import net.godaa.SpringEcommerce.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class FileController {

    FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/v1/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            fileService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/v1/files/{id}")
    public ResponseEntity<byte[]> getFiles(@PathVariable String id) {
        File fileDB = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @GetMapping("/v1/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = fileService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    //         <  ---------------------------------------------------------------  >

    @PostMapping("/v2/upload")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            fileService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/v2/files")
    public ResponseEntity<List<FileInfo>> getListFile() {
        List<FileInfo> fileInfo = fileService.loadAll().map(
                path -> {
                    String filename = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder.fromMethodName(
                            FileController.class,
                            "getFile",
                            path.getFileName().toString()
                    ).build().toString();
                    return new FileInfo(filename, url);
                }
        ).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfo);
    }

    @GetMapping("/v2/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
