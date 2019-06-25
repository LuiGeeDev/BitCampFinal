package kr.or.bit.controller;

import java.security.Principal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Member;

@Controller
public class MemberController {
	@Autowired
	private SqlSession sqlSession;
	
	@GetMapping("/memberJoin")
	public String join() {
		return "memberJoinForm";
	}
	
	@GetMapping("/memberJoinForm")
	public void insertMember(Member member, Model model) {
		MemberDao memberdao = sqlSession.getMapper(MemberDao.class);
		memberdao.insertMember(member);
	}
	
	@GetMapping("/memberSelectAll")
	public String selectAllMembers(Model model) {
		MemberDao memberdao = sqlSession.getMapper(MemberDao.class);
		List<Member> list = memberdao.selectAllMembers();
		
		System.out.println(list);
		model.addAttribute("list", list);
		
		return "memberSelectAll";
	}
	
	@GetMapping("/mypage")
	public String updateMember(Model model) {
		MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		Member user = memberDao.selectMemberByUsername(username);
		model.addAttribute("user", user);
		return "mypageForm";
	}
	
	@PostMapping("/mypage")
	public String updateMember(Member member, Principal principal) {
		MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
		memberDao.updateMember(member);
		
		return "redirect:/";
		
	}
}
