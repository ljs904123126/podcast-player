package org.liaimei.podcast.player.controller;


import org.liaimei.podcast.player.domain.SysEpisodes;
import org.liaimei.podcast.player.entity.EpisodesEntity;
import org.liaimei.podcast.player.entity.SrtEntity;
import org.liaimei.podcast.player.service.SysEpisodesService;
import org.liaimei.podcast.player.service.SysPodcastsService;
import org.liaimei.podcast.player.service.SysUserService;
import org.liaimei.podcast.player.utils.SrtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

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
        SysEpisodes byId = episodesService.getById(id);
        if (null == byId) {
            return null;
        }
        String audioFilePath = basicPath + File.separator + byId.getSrtFile();
        List<SrtEntity> srtEntities = SrtUtils.readSrt(audioFilePath);
        mv.addObject("srts", srtEntities);
        List<Integer[]> collect = srtEntities.stream()
                .map(srtEntity -> new Integer[]{srtEntity.getNumber(), srtEntity.getStart(), srtEntity.getEnd()})
                .collect(Collectors.toList());
        mv.addObject("times", collect);

        return mv;
    }

    @GetMapping(value = "/stream-audio", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> streamAudio(@RequestParam(value = "id") Integer id,
                                                           @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {


        SysEpisodes byId = episodesService.getById(id);
        if (null == byId) {
            return null;
        }
        // Replace with your audio file path
        String audioFilePath = basicPath + File.separator + byId.getAudioFile();
        InputStream inputStream = new FileInputStream(audioFilePath);


        long contentLength = inputStream.available();
        long totalLength = 0;
        long start = 0;
        long end = 0;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring(6).split("-");
            start = Long.parseLong(ranges[0]);
            end = ranges.length > 1 ? Long.parseLong(ranges[1]) : contentLength - 1;
            long skip = inputStream.skip(start);// 跳过指定的字节数
            System.out.println("skip :" + skip + " target:" + start + " total:" + contentLength);
            totalLength = end - start + 1;
        } else {
            totalLength = contentLength;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(totalLength);
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
        headers.add("Content-Range", "bytes " + start + "-" + end + "/" + contentLength);

        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

//        return new ResponseEntity<>(resource, headers, HttpStatus.PARTIAL_CONTENT);


        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                .contentLength(inputStream.available())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers)
                .body(inputStreamResource);
    }

    @GetMapping(value = "/stream-audio-srt")
    @ResponseBody
    public List<SrtEntity> streamAudioSrt(@RequestParam(value = "id") Integer id) {
        SysEpisodes byId = episodesService.getById(id);
        if (null == byId) {
            return null;
        }
        String audioFilePath = basicPath + File.separator + byId.getSrtFile();

        return SrtUtils.readSrt(audioFilePath);
    }


}
