package com.templify.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.templify.dto.MjmlRenderResult;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MjmlRenderService {

    public MjmlRenderResult render(String mjml, Map<String, Object> variables) {
        List<String> errors = new ArrayList<>();

        try {
            String boundMjml = bindVariables(mjml, variables);

            Path tempDir = Files.createTempDirectory("mjml");
            Path inputPath = tempDir.resolve("input.mjml");
            Path outputPath = tempDir.resolve("output.html");

            Files.writeString(inputPath, boundMjml, StandardCharsets.UTF_8);

            ProcessBuilder pb = new ProcessBuilder(
                    "mjml",
                    inputPath.toAbsolutePath().toString(),
                    "-o",
                    outputPath.toAbsolutePath().toString()
            );
            pb.redirectErrorStream(true);

            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.toLowerCase().contains("error")) {
                        errors.add(line);
                    }
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                errors.add("MJML process exited with code " + exitCode);
            }

            String html = Files.exists(outputPath)
                    ? Files.readString(outputPath, StandardCharsets.UTF_8)
                    : "";

            return new MjmlRenderResult(html, errors);

        } catch (Exception e) {
            errors.add(e.getMessage());
            return new MjmlRenderResult("", errors);
        }
    }

    private String bindVariables(String template, Map<String, Object> variables) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(template), "mjml-template");

        StringWriter writer = new StringWriter();
        mustache.execute(writer, variables).flush();
        return writer.toString();
    }
}


