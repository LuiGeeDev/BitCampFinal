package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
