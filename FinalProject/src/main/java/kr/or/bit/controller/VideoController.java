package kr.or.bit.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/video")
public class VideoController {

	@Autowired
	private SqlSession sqlSession;

	@GetMapping("/home")
	public String videoHome() {
		return "video/home";
	}
	@GetMapping("/detail")
	public String videoDetail() {
		return "video/detail";
	}
	@GetMapping("/write")
	public String videoWrite() {
		return "video/write";
	}
}
