package com.example.sns.application.controller;

import com.example.sns.domain.post.dto.PostCommand;
import com.example.sns.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostWriteService postWriteService;

    @PostMapping("")
    public Long create(PostCommand command) {
        return postWriteService.create(command);
    }
}
