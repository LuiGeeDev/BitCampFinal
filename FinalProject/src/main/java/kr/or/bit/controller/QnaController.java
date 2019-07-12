package kr.or.bit.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.ArticleUpdateService;
import kr.or.bit.service.ArticleVoteService;
import kr.or.bit.service.CommentService;
import kr.or.bit.utils.Helper;

@Controller
@RequestMapping("/myclass")
public class QnaController {
  
  private final int QNA_BOARD_ID = 6;
  
  @Autowired
  private SqlSession sqlsession;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ArticleInsertService articleInsertService;
  @Autowired
  private ArticleVoteService articleVoteService;
  @Autowired
  private ArticleUpdateService articleUpdateService;
 
  
}
