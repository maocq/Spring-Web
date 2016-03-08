package co.com.maocq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

  @RequestMapping(value = "/")
  public @ResponseBody String showAbout() {    
    return "index";
  }
}
