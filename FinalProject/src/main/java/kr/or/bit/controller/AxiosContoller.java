package kr.or.bit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.model.Vote;

@RestController
@RequestMapping("/axios")
public class AxiosContoller {
	
	@GetMapping("/addTodo")
	public Vote addTodo() {
		System.out.println("애드투두");
		Vote vote = new Vote();
		vote.setComment_id(1);
		vote.setUsername("권순조");
		return vote;
	}
}
