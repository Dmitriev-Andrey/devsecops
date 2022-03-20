package com.dmitriev.andrey.devsecops.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileService {

    public String readResult(String name) throws IOException {
         return Files.readString(Path.of("./output/" + name + ".html"));
    }

    public void clearTrash(String name) throws IOException {
        Files.delete(Path.of("./output/" + name + ".html"));
        Files.delete(Path.of("./input/" + name));
    }

    public void writeFile(MultipartFile file) throws IOException {
        Path path = Paths.get("./input/" + file.getOriginalFilename());
        Files.createFile(path);
        Files.write(path, file.getBytes());
    }
}
