package com.dmitriev.andrey.devsecops.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import org.springframework.stereotype.Component;

@Component
public class ResultService {

    public String readResult(String name) throws IOException {
         return Files.readString(Path.of("./output/" + name + ".html"));
    }

    public void clearTrash(String name) throws IOException {
        Files.delete(Path.of("./output/" + name + ".html"));

        Files.walk(Path.of(name))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
