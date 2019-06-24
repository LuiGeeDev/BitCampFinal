package kr.or.bit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import kr.or.bit.model.Vote;

@Controller
@RequestMapping("/axios")
public class AxiosContoller {
	@Autowired
	private View jsonView;
	
	@GetMapping("/addTodo")
	public View addTodo(Model model) {
		System.out.println("애드투두");
		Vote vote = new Vote();
		vote.setComment_id(1);
		vote.setUsername("권순조");
		model.addAttribute("vote",vote);
		return jsonView;
	}
}
