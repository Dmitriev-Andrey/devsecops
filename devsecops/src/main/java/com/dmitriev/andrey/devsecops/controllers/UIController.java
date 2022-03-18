package com.dmitriev.andrey.devsecops.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.dmitriev.andrey.devsecops.db.entities.Scanning;
import com.dmitriev.andrey.devsecops.services.DockerService;
import com.dmitriev.andrey.devsecops.services.GithubService;
import com.dmitriev.andrey.devsecops.services.ResultService;
import com.dmitriev.andrey.devsecops.services.ScanningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UIController {

    private final GithubService githubService;
    private final DockerService dockerService;
    private final ScanningService scanningService;
    private final ResultService resultService;

    public UIController(GithubService githubService, DockerService dockerService, ScanningService scanningService,
                        ResultService resultService) {
        this.githubService = githubService;
        this.dockerService = dockerService;
        this.scanningService = scanningService;
        this.resultService = resultService;
    }

    @GetMapping("/")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "ui";
    }

    @GetMapping("/scan_code")
    public RedirectView scanCode(@RequestParam(name = "githubLink") String link, Model model) throws IOException,
            InterruptedException {
        //todo: dont show log on frontend
        //todo: run scanning in separate thread
        //todo: add exception handler
        String dir = UUID.randomUUID().toString();
        scanningService.createNewScan(dir);
        githubService.clone(dir, link);
        dockerService.runScanning(dir);
        String result = resultService.readResult(dir);
        scanningService.finishScan(dir, result);
        resultService.clearTrash(dir);
        return new RedirectView("all_scans");
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
