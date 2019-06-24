package kr.or.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/axios")
public class AxiosContoller {

	@GetMapping("/addTodo")
	public void addTodo() {
		System.out.println("애드투두");
	}
}
