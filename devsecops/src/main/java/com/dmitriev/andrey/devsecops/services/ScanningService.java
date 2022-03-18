package com.dmitriev.andrey.devsecops.services;

import java.util.List;

import com.dmitriev.andrey.devsecops.db.entities.Scanning;
import com.dmitriev.andrey.devsecops.db.repos.ScanningRepo;
import org.springframework.stereotype.Component;

import static com.dmitriev.andrey.devsecops.db.entities.Status.SUCCESS;
import static com.dmitriev.andrey.devsecops.db.entities.Status.WORKING;

@Component
public class ScanningService {

    private final ScanningRepo scanningRepo;

    public ScanningService(ScanningRepo scanningRepo) {
        this.scanningRepo = scanningRepo;
    }

    public void createNewScan(String name) {
        scanningRepo.createNewScan(name, WORKING);
    }

    public void finishScan(String name, String result) {
        scanningRepo.updateScan(name, SUCCESS, result);
    }

    public List<Scanning> getAllScanning() {
        return scanningRepo.getAllScan();
    }

    public String getScanningResult(long id) {
        return scanningRepo.getById(id);
    }
}
