package com.templify.controller;

import com.templify.dto.MjmlRenderResult;
import com.templify.dto.RenderRequest;
import com.templify.dto.RenderResponse;
import com.templify.service.MjmlRenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MjmlRenderController {

    private final MjmlRenderService mjmlRenderService;

    @PostMapping("/render")
    public RenderResponse render(@RequestBody RenderRequest request) {
        MjmlRenderResult result =
                mjmlRenderService.render(request.getMjml(), request.getVariables());

        return new RenderResponse(result.getHtml(), result.getErrors());
    }
}

