package kr.or.bit.controller;

import java.security.Principal;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;
import kr.or.bit.service.MemberService;
import kr.or.bit.utils.Helper;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private SqlSession sqlSession;
	
	@GetMapping("/mypage")
	public String updateMember(Model model, Principal principal) {
	  MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
		String username = Helper.userName();
		Member user = memberDao.selectMemberByUsername(username);
		model.addAttribute("user", user);
		return "mypageForm";
	}
	
	@PostMapping("/mypage")
	public String updateMember(Member member, Principal principal) {	  
		member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
		service.updateMember(member);
		return "redirect:/";
		
	}
}
