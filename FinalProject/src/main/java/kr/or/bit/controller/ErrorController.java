package kr.or.bit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/error")
public class ErrorController {
  @GetMapping("/403")
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public String error403() {
    return "error/errorPage403";
  }

  @GetMapping("/404")
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String error404() {
    return "error/errorPage404";
  }

  @GetMapping("/500")
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String error500() {
    return "error/errorPage500";
  }
}
