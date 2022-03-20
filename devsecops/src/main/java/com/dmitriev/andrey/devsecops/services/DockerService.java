package com.dmitriev.andrey.devsecops.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

@Component
public class DockerService {

    private static final String CURR_DIR = System.getProperty("user.dir");

    public void scanFile(String fileName) throws IOException, InterruptedException {

        final String[] cmd = {
                "docker", "run", "--rm",
                "--name", fileName,
                "-v", CURR_DIR + "/input/:/scanning_files:ro",
                "-v", CURR_DIR + "/output/:/output",
                "sast",
                "bash", "sast/findsecbugs.sh", "-high", "-progress", "-html", "-output", "/output/" + fileName + ".html",
                "scanning_files/" + fileName
        };

        ProcessBuilder procBuilder = new ProcessBuilder(cmd);
        procBuilder.inheritIO();
//        procBuilder.redirectErrorStream(true);
        Process process = procBuilder.start();

        final int i = process.waitFor();
    }
}
