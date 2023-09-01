package org.liaimei.podcast.player.controller;


import org.liaimei.podcast.player.entity.EpisodesEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login/index.html");
        return mv;
    }

}
