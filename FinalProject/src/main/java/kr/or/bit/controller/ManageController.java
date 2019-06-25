package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class ManageController {
	
	@GetMapping("/home")
	public String manageHome() {
		return "manage/home";
	}
}
