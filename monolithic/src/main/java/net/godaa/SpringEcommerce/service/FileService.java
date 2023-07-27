package net.godaa.SpringEcommerce.service;

import net.godaa.SpringEcommerce.domain.File;
import net.godaa.SpringEcommerce.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    FileRepo fileRepo;

    private final Path root = Paths.get("uploads");

    //         <  ---------------------------------------------------------------  >
    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File _file = new File(fileName, file.getContentType(), file.getBytes());
        return fileRepo.save(_file);
    }

    public File getFile(String id) {
        return fileRepo.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileRepo.findAll().stream();
    }


    //         <  ---------------------------------------------------------------  >

    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }


    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public void delete(String filename) {
        try {
            FileSystemUtils.deleteRecursively(root.resolve(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
