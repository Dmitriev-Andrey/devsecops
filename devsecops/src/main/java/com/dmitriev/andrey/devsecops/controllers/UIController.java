package com.dmitriev.andrey.devsecops.controllers;

import java.io.IOException;
import java.util.List;

import com.dmitriev.andrey.devsecops.db.entities.Scanning;
import com.dmitriev.andrey.devsecops.services.FileService;
import com.dmitriev.andrey.devsecops.services.ScanningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UIController {

    private final ScanningService scanningService;
    private final FileService fileService;

    public UIController(ScanningService scanningService, FileService fileService) {
        this.scanningService = scanningService;
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String main() {
        return "ui";
    }

    @PostMapping(value = "/scanJar")
    public String submit(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.writeFile(file);
        scanningService.scanFile(file.getOriginalFilename());
        return "redirect:/all_scans";
    }

    @GetMapping("/all_scans")
    public String allScan(Model model) {
        List<Scanning> allScanning = scanningService.getAllScanning();
        model.addAttribute("allScanning", allScanning);
        return "all_scanning";
    }

    @GetMapping("/scanning/{id}")
    @ResponseBody
    public String scan(@PathVariable Long id) {
        return scanningService.getScanningResult(id);
    }

}
