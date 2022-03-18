package com.dmitriev.andrey.devsecops.services;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class GithubService {

    public void clone(String dir, String link) throws IOException, InterruptedException {
        //todo: validate link

        ProcessBuilder procBuilder = new ProcessBuilder("git", "clone", link, dir);
        procBuilder.redirectErrorStream(true);
        Process process = procBuilder.start();
        //todo: use async approach
        process.waitFor();
    }
}
