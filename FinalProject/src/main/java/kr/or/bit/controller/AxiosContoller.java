package kr.or.bit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.bit.model.CommentVote;

@RestController
@RequestMapping("/axios")
public class AxiosContoller {
	
	@GetMapping("/addTodo")
	public CommentVote addTodo() {
		System.out.println("애드투두");
		CommentVote vote = new CommentVote();
		vote.setComment_id(1);
		vote.setUsername("권순조");
		return vote;
	}
}
