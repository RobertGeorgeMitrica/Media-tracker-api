package com.example.MediaTracker.controller;

import com.example.MediaTracker.repository.MediaRepository;
import com.example.MediaTracker.service.MediaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class MediaController {

    private final MediaRepository mediaRepository;
    private final MediaService mediaService;

    public MediaController(MediaRepository mediaRepository, MediaService mediaService) {
        this.mediaRepository = mediaRepository;
        this.mediaService = mediaService;
    }

    @GetMapping
    public String showDashboard(Model model) {
        model.addAttribute("mediaList", mediaRepository.findAll());
        return "index";
    }


    @PostMapping("/setup")
    public String setupProfile(@RequestParam String genre) {
        mediaService.fetchAndSaveMovies(genre);
        try {Thread.sleep(1500); } catch (InterruptedException e) {}
        return "redirect:/";
    }
}