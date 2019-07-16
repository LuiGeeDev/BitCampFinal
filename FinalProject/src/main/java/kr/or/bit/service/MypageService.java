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
import kr.or.bit.model.Tag;


@Service
public class MypageService {
  
  @Autowired
  private SqlSession sqlSession;
  
  public List<Article> allArticleByUsername(String username){
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    List<Article> articleList = articleDao.selectEnableArticleByUsername(username);
    for (Article article : articleList) {
      article.setTimeLocal(article.getTime().toLocalDateTime());
      //article.setUpdatedTimeLocal(article.getUpdated_time().toLocalDateTime());
    }  
    return articleList;
  }
  
    public Article selectOneArticleforMypage(String optionName, String board_id, int id) {
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
        comment.setName(
            (memberDao.selectMemberByUsername(comment.getUsername())).getName()
            );
      }
      article.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
      article.setOption(option);
      return article;
    }
  
  
}
