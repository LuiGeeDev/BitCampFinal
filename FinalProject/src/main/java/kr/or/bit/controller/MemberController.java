package kr.or.bit.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Files;
import kr.or.bit.model.Member;
import kr.or.bit.service.FileUploadService;
import kr.or.bit.service.MemberService;
import kr.or.bit.utils.Helper;

@Controller
public class MemberController {
  @Autowired
  private MemberService service;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private FileUploadService fileUploadService;
  @Autowired
  private SqlSession sqlSession;

  @GetMapping("/mypage")
  public String updateMember(Model model, Principal principal) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    user.setPassword(user.getPassword());
    model.addAttribute("user", user);
    return "mypage/mypageForm";
  }

  @PostMapping("/mypage")
  public String updateMember(Member member, Principal principal, MultipartFile files1, HttpServletRequest request)
      throws IllegalStateException, IOException {
    if (files1 != null) {
      FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
      Files file = fileUploadService.uploadFile(files1, request);
      filesDao.insertFiles(file);
      member.setProfile_photo(file.getFilename());
      service.updateMemberOnlyFile(member);
    } else {
      member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
      service.updateMemberWithoutFile(member);
    }
    return "redirect:/";
  }
}
