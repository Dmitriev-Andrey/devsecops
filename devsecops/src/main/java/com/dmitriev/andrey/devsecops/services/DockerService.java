package com.dmitriev.andrey.devsecops.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DockerService {

    private static final String CURR_DIR = System.getProperty("user.dir");

    public void runScanning(String dir) throws IOException, InterruptedException {

        final String[] cmd = {
                "docker", "run", "--rm",
                "--name", dir,
                "-v", CURR_DIR + "/" + dir + "/:/scanning_files:ro",
                "-v", CURR_DIR + "/output/:/output",
                "sast",
                "bash", "sast/findsecbugs.sh", "-progress", "-html", "-output", "/output/" + dir + ".html",
                "scanning_files/"
        };

        ProcessBuilder procBuilder = new ProcessBuilder(cmd);
        procBuilder.redirectErrorStream(true);
        Process process = procBuilder.start();
        //todo: use async approach
        process.waitFor();
    }
}
