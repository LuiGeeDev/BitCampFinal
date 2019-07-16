package kr.or.bit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.CommentDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.GeneralDao;
import kr.or.bit.dao.HomeworkDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Files;
import kr.or.bit.model.General;
import kr.or.bit.model.Member;
import kr.or.bit.model.Tag;
import kr.or.bit.utils.Helper;
import kr.or.bit.utils.Pager;

@Service
public class ArticleService {
  @Autowired
  private SqlSession sqlSession;

  public void updateArticle() {
  }

  public List<Article> selectArticlesOnNextPage(int board_id, int article_id) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
    List<Article> articleList = articleDao.selectArticlesOnNextPage(board_id, article_id);
    for (Article article : articleList) {
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setCommentlist(commentdao.selectAllComment(article.getId()));
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
      article.setOption(videoDao.selectVideoByArticleId(article.getId()));
    }
    return articleList;
  }

  public List<Article> selectAllArticle(String optionName, int boardId, Pager pager) {// 게시판아이디를 기준으로 게시글을 전부 불러오는 함수
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Article> list = null;
    if(optionName.equals("general")) {
      list = articledao.selectAllPagingArticlesByBoardId(boardId, pager);
    }else {
      list = articledao.selectFirstArticlesByBoardId(boardId); 
    }
    for (Article article : list) {
      ArticleOption option = null;
      switch (optionName.toLowerCase()) {
      case "video":
        VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
        option = videoDao.selectVideoByArticleId(article.getId());
        break;
      case "troubleshooting":
        TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
        option = troubleShootingDao.selectTroubleShootingByArticleId(article.getId());
        break;
      case "general":
        GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
        option = generalDao.selectGeneralByArticleId(article.getId());
        break;
      case "qna":
        QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
        option = qnaDao.selectQnaByArticleId(article.getId());
        break;
      case "homework":
        HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
        option = homeworkDao.selectHomeworkByArticleId(article.getId());
        break;
      default:
        break;
      }
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
      article.setCommentlist(commentdao.selectAllComment(article.getId()));
      for (Comment comment : article.getCommentlist()) {
        comment.setTimeLocal(comment.getTime().toLocalDateTime());
      }
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setOption(option);
    }
    return list;
  }

  public Article selectOneArticle(String optionName, int id) {
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    Article article = articledao.selectOneArticle(id);
    ArticleOption option = null;
    switch (optionName.toLowerCase()) {
    case "video":
      VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
      option = videoDao.selectVideoByArticleId(article.getId());
      break;
    case "troubleshooting":
      TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
      option = troubleShootingDao.selectTroubleShootingByArticleId(article.getId());
      break;
    case "general":
      GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
      option = generalDao.selectGeneralByArticleId(article.getId());
      General general = (General) option;
      List<Integer> fileidlist = new ArrayList<>();
      
      if (general.getFile1() != 0) {
        fileidlist.add(general.getFile1());
        System.out.println(general.getFile1());
        if (general.getFile2() != 0) {
          fileidlist.add(general.getFile2());
          System.out.println(general.getFile2());
        }
      }
      FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
      List<Files> filelist = new ArrayList<>();
      Files files = new Files();
      if (fileidlist.size() == 0) {
        break;
      } else if (fileidlist.size() > 0) {
        for (int fileid : fileidlist) {
          files = filesDao.selectFilesById(fileid);
          System.out.println(files);
          filelist.add(files);
        }
      }
      System.out.println(article);
      article.setFileslist(filelist);
      System.out.println(article);
      break;
    case "qna":
      QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
      StackDao stackDao = sqlSession.getMapper(StackDao.class);
      option = qnaDao.selectQnaByArticleId(article.getId());
      List<Tag> taglist = stackDao.selectTagList(article.getId());
      article.setTags(taglist);
      break;
    case "homework":
      HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
      option = homeworkDao.selectHomeworkByArticleId(article.getId());
      break;
    default:
      break;
    }
    article.setTimeLocal(article.getTime().toLocalDateTime());
    article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
    article.setCommentlist(commentdao.selectAllComment(id));
    
    for (Comment comment : article.getCommentlist()) {
      System.out.println(comment.getTime());
      comment.setTimeLocal(comment.getTime().toLocalDateTime());
      System.out.println("댓글이름:"+(memberDao.selectMemberByUsername(comment.getUsername())).getName());
      comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
      comment.setName(
          (memberDao.selectMemberByUsername(comment.getUsername())).getName()
          );
    }
    article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    article.setOption(option);
    return article;
  }
  
  public List<Article> selectAllStackArticles(Pager pager) {
    StackDao stackdao = sqlSession.getMapper(StackDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
    List<Article> articlelist = stackdao.selectAllStackArticle(pager);
    
    for (Article article : articlelist) {
      ArticleOption option = null;
      option = qnaDao.selectQnaByArticleId(article.getId());
      List<Comment> commentList = commentdao.selectAllComment(article.getId());
      List<Tag> taglist = stackdao.selectTagList(article.getId()); 
      for (Comment comment : commentList) {
        comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
      }
      article.setOption(option);
      article.setTags(taglist);
      article.setCommentlist(commentList);
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));   
    }
    return articlelist;
  }
  
  public List<Article> selectStackArticlesByboardSearch(Pager pager,String boardSearch,String criteria) {
    StackDao stackdao = sqlSession.getMapper(StackDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
    List<Article> articleList = null;
    
    if(criteria.equals("titleOrContent")) {
      articleList = stackdao.selectStackArticleByTitleOrContent(pager,boardSearch);
    }else if(criteria.equals("title")) {
      articleList = stackdao.selectStackArticleByTitle(pager,boardSearch);
    }else if(criteria.equals("Tag")) {
      articleList = stackdao.selectStackArticleByTag(pager, boardSearch);
    }
    else {
      articleList = stackdao.selectStackArticleByWriter(pager,boardSearch);
    }
    
    for (Article article : articleList) {
      ArticleOption option = null;
      option = qnaDao.selectQnaByArticleId(article.getId());
      List<Comment> commentList = commentdao.selectAllComment(article.getId());
      List<Tag> taglist = stackdao.selectTagList(article.getId());
      
      for (Comment comment : commentList) {
        comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
      }
      
      
      article.setOption(option);
      article.setTags(taglist);
      article.setCommentlist(commentList);
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));   
    }
    return articleList;
  }
  
  
  public List<Article> selectAllQnaArticles(Pager pager) {
    StackDao stackdao = sqlSession.getMapper(StackDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
    List<Article> articlelist = qnaDao.selectAllQnaArticle(pager);
    
    for (Article article : articlelist) {
      ArticleOption option = null;
      option = qnaDao.selectQnaByArticleId(article.getId());
      List<Comment> commentList = commentdao.selectAllComment(article.getId());
      List<Tag> taglist = stackdao.selectTagList(article.getId()); 
      for (Comment comment : commentList) {
        comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
      }
      article.setOption(option);
      article.setTags(taglist);
      article.setCommentlist(commentList);
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));   
    }
    return articlelist;
  }
  
  public List<Article> selectQnaArticlesByboardSearch(Pager pager,String boardSearch,String criteria) {
    StackDao stackdao = sqlSession.getMapper(StackDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
    List<Article> articleList = null;
    
    if(criteria.equals("titleOrContent")) {
      articleList = qnaDao.selectQnaArticleByTitleOrContent(pager,boardSearch);
    }else if(criteria.equals("title")) {
      articleList = qnaDao.selectQnaArticleByTitle(pager,boardSearch);
    }else if(criteria.equals("Tag")) {
      articleList = qnaDao.selectQnaArticleByTag(pager, boardSearch);
    }
    else {
      articleList = qnaDao.selectQnaArticleByWriter(pager,boardSearch);
    }
    
    for (Article article : articleList) {
      ArticleOption option = null;
      option = qnaDao.selectQnaByArticleId(article.getId());
      List<Comment> commentList = commentdao.selectAllComment(article.getId());
      List<Tag> taglist = stackdao.selectTagList(article.getId());
      
      for (Comment comment : commentList) {
        comment.setWriter(memberDao.selectMemberByUsername(comment.getUsername()));
      }
      
      
      article.setOption(option);
      article.setTags(taglist);
      article.setCommentlist(commentList);
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));   
    }
    return articleList;
  }
  
  
  public List<Article> selectAllArticlesByBoardSearch(int boardId, Pager pager, String boardSearch, String criteria) {
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
    List<Article> articleList = null;
    if(criteria.equals("titleOrContent")) {
      articleList = generalDao.selectGeneralArticlesByTitleOrContent(boardId, pager, boardSearch);
    }else if(criteria.equals("title")) {
      articleList = generalDao.selectGeneralArticlesByTitle(boardId, pager,boardSearch);
    }else {
      articleList = generalDao.selectGeneralArticlesByWriter(boardId, pager,boardSearch);
    }
    for (Article article : articleList) {
      ArticleOption option = null;
      option = generalDao.selectGeneralByArticleId(article.getId());
      
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
      article.setCommentlist(commentdao.selectAllComment(article.getId()));
      for (Comment comment : article.getCommentlist()) {
        comment.setTimeLocal(comment.getTime().toLocalDateTime());
      }
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setOption(option);
    }
    return articleList;
  }

  
  public List<Article> selectAllArticleViewCountByDesc(String optionName, int boardId, Pager pager) {// 게시판아이디를 기준으로 게시글을 전부 불러오는 함수
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    CommentDao commentdao = sqlSession.getMapper(CommentDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    List<Article> list = null;
    if(optionName.equals("general")) {
      list = articledao.selectAllPagingArticlesByViewCount(boardId, pager);
    }else {
      list = articledao.selectFirstArticlesByBoardId(boardId); 
    }
    for (Article article : list) {
      ArticleOption option = null;
      switch (optionName.toLowerCase()) {
      case "video":
        VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
        option = videoDao.selectVideoByArticleId(article.getId());
        break;
      case "troubleshooting":
        TroubleShootingDao troubleShootingDao = sqlSession.getMapper(TroubleShootingDao.class);
        option = troubleShootingDao.selectTroubleShootingByArticleId(article.getId());
        break;
      case "general":
        GeneralDao generalDao = sqlSession.getMapper(GeneralDao.class);
        option = generalDao.selectGeneralByArticleId(article.getId());
        break;
      case "qna":
        QnaDao qnaDao = sqlSession.getMapper(QnaDao.class);
        option = qnaDao.selectQnaByArticleId(article.getId());
        break;
      case "homework":
        HomeworkDao homeworkDao = sqlSession.getMapper(HomeworkDao.class);
        option = homeworkDao.selectHomeworkByArticleId(article.getId());
        break;
      default:
        break;
      }
      article.setTimeLocal(article.getTime().toLocalDateTime());
      article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
      article.setCommentlist(commentdao.selectAllComment(article.getId()));
      for (Comment comment : article.getCommentlist()) {
        comment.setTimeLocal(comment.getTime().toLocalDateTime());
      }
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setOption(option);
    }
    return list;
  }
  
  
  
  
  
  
  
}
