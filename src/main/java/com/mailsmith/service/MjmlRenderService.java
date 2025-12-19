package com.mailsmith.service;

import com.mailsmith.dto.MjmlRenderResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class MjmlRenderService {

    public MjmlRenderResult render(String mjmlContent) {
        try {
            Path tempDir = Files.createTempDirectory("mjml");
            Path mjmlFile = tempDir.resolve("template.mjml");
            Path htmlFile = tempDir.resolve("result.html");

            Files.writeString(mjmlFile, mjmlContent, StandardCharsets.UTF_8);

            ProcessBuilder pb = new ProcessBuilder(
                    "mjml",
                    mjmlFile.toAbsolutePath().toString(),
                    "-o",
                    htmlFile.toAbsolutePath().toString()
            );

            Process process = pb.start();

            String errorOutput;
            try (BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
                errorOutput = errorReader.lines()
                        .collect(Collectors.joining("\n"));
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                return new MjmlRenderResult(null, errorOutput);
            }

            String htmlResult = Files.readString(htmlFile, StandardCharsets.UTF_8);
            return new MjmlRenderResult(htmlResult, null);

        } catch (Exception e) {
            return new MjmlRenderResult(null, e.getMessage());
        }
    }
}

