package kr.or.bit.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Article {
  private int id;
  private String title;
  private String content;
  private Timestamp time;
  private Timestamp updated_time;
  private int view_count; // default 0
  private int original_id;
  private int level; // default 1
  private int enable; // default 1
  private String username;
  private int board_id;

  private LocalDateTime timeLocal;
  private LocalDateTime updatedTimeLocal;
  private Member writer;
  private List<String> tags;
  private List<Comment> commentlist;
  private ArticleOption option;
  private Map<String, Integer> vote;
  private int votenum;
  private int commentnum;

  public LocalDateTime getUpdatedTimeLocal() {
    return updatedTimeLocal;
  }

  public void setUpdatedTimeLocal(LocalDateTime updatedTimeLocal) {
    this.updatedTimeLocal = updatedTimeLocal;
  }

  public LocalDateTime getTimeLocal() {
    return timeLocal;
  }

  public void setTimeLocal(LocalDateTime timeLocal) {
    this.timeLocal = timeLocal;
  }

  public void setVote(Map<String, Integer> vote) {
    this.vote = vote;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Timestamp getUpdated_time() {
    return updated_time;
  }

  public void setUpdated_time(Timestamp updated_time) {
    this.updated_time = updated_time;
  }

  public int getView_count() {
    return view_count;
  }

  public void setView_count(int view_count) {
    this.view_count = view_count;
  }

  public int getOriginal_id() {
    return original_id;
  }

  public void setOriginal_id(int original_id) {
    this.original_id = original_id;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getEnable() {
    return enable;
  }

  public void setEnable(int enable) {
    this.enable = enable;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getBoard_id() {
    return board_id;
  }

  public void setBoard_id(int board_id) {
    this.board_id = board_id;
  }

  public Member getWriter() {
    return writer;
  }

  public void setWriter(Member writer) {
    this.writer = writer;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public ArticleOption getOption() {
    return option;
  }

  public void setOption(ArticleOption option) {
    this.option = option;
  }

  public List<Comment> getCommentlist() {
    return commentlist;
  }

  public void setCommentlist(List<Comment> commentlist) {
    this.commentlist = commentlist;
  }

  public Map<String, Integer> getVote() {
    return vote;
  }

  public void setVote(HashMap<String, Integer> vote) {
    this.vote = vote;
  }

  public int getVotenum() {
    return votenum;
  }

  public void setVotenum(int votenum) {
    this.votenum = votenum;
  }

  public int getCommentnum() {
    return commentnum;
  }

  public void setCommentnum(int commentnum) {
    this.commentnum = commentnum;
  }

  
}
