package org.liaimei.podcast.player.controller;


import org.liaimei.podcast.player.domain.SysEpisodes;
import org.liaimei.podcast.player.entity.EpisodesEntity;
import org.liaimei.podcast.player.service.SysEpisodesService;
import org.liaimei.podcast.player.service.SysPodcastsService;
import org.liaimei.podcast.player.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {


    @Value("${podcast.basic-path}")
    private String basicPath;

    @Autowired
    SysUserService service;

    @Autowired
    SysPodcastsService podcastsService;

    @Autowired
    SysEpisodesService episodesService;


    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        List<EpisodesEntity> lastEpisode = episodesService.getLastEpisode();
        mv.addObject("episodes", lastEpisode);
        return mv;
    }

    @RequestMapping("/player/index")
    public ModelAndView playerIndex(@RequestParam(value = "id") Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("id", id);
        mv.setViewName("player/index");
        return mv;
    }

    @GetMapping(value = "/stream-audio", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> streamAudio(@RequestParam(value = "id") Integer id) throws IOException {
        SysEpisodes byId = episodesService.getById(id);
        if (null == byId) {
            return null;
        }
        // Replace with your audio file path
        String audioFilePath = basicPath + File.separator + byId.getAudioFile();
        InputStream inputStream = new FileInputStream(audioFilePath);

        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .contentLength(inputStream.available())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStreamResource);
    }


}
