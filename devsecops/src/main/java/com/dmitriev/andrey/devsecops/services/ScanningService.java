package com.dmitriev.andrey.devsecops.services;

import java.util.List;

import com.dmitriev.andrey.devsecops.db.entities.Scanning;
import com.dmitriev.andrey.devsecops.db.repos.ScanningRepo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static com.dmitriev.andrey.devsecops.db.entities.Status.ERROR;
import static com.dmitriev.andrey.devsecops.db.entities.Status.SUCCESS;
import static com.dmitriev.andrey.devsecops.db.entities.Status.WORKING;

@Component
public class ScanningService {

    private final ScanningRepo scanningRepo;
    private final DockerService dockerService;
    private final FileService fileService;

    public ScanningService(ScanningRepo scanningRepo, DockerService dockerService, FileService fileService) {
        this.scanningRepo = scanningRepo;
        this.dockerService = dockerService;
        this.fileService = fileService;
    }

    @Async
    public void scanFile(String name) {
        try {
            scanningRepo.createNewScan(name, WORKING);
            dockerService.scanFile(name);
            String result = fileService.readResult(name);
            scanningRepo.updateScan(name, SUCCESS, result);
            fileService.clearTrash(name);
        } catch (Exception e) {
            scanningRepo.updateScan(name, ERROR, e.getMessage());
        }
    }

    public List<Scanning> getAllScanning() {
        return scanningRepo.getAllScan();
    }

    public String getScanningResult(long id) {
        return scanningRepo.getById(id);
    }
}
