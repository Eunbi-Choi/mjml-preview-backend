package com.mailsmith.controller;

import com.mailsmith.dto.MjmlRenderResult;
import com.mailsmith.dto.RenderRequest;
import com.mailsmith.dto.RenderResponse;
import com.mailsmith.service.MjmlRenderService;
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

