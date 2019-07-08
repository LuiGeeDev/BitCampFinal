package kr.or.bit.model;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
  private String board_name;
  private List<Tag> tags;
  private List<Comment> commentlist;
  private ArticleOption option;
  private Map<String, Integer> vote;
  private int votenum;
  private int vote_count;
  private List<Files> fileslist;

  public String getBoard_name() {
    return board_name;
  }

  public void setBoard_name(String board_name) {
    this.board_name = board_name;
  }

  public List<Files> getFileslist() {
    return fileslist;
  }

  public void setFileslist(List<Files> fileslist) {
    this.fileslist = fileslist;
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

  public LocalDateTime getTimeLocal() {
    return timeLocal;
  }

  public void setTimeLocal(LocalDateTime timeLocal) {
    this.timeLocal = timeLocal;
  }

  public LocalDateTime getUpdatedTimeLocal() {
    return updatedTimeLocal;
  }

  public void setUpdatedTimeLocal(LocalDateTime updatedTimeLocal) {
    this.updatedTimeLocal = updatedTimeLocal;
  }

  public Member getWriter() {
    return writer;
  }

  public void setWriter(Member writer) {
    this.writer = writer;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public List<Comment> getCommentlist() {
    return commentlist;
  }

  public void setCommentlist(List<Comment> commentlist) {
    this.commentlist = commentlist;
  }

  public ArticleOption getOption() {
    return option;
  }

  public void setOption(ArticleOption option) {
    this.option = option;
  }

  public Map<String, Integer> getVote() {
    return vote;
  }

  public void setVote(Map<String, Integer> vote) {
    this.vote = vote;
  }

  public int getVotenum() {
    return votenum;
  }

  public void setVotenum(int votenum) {
    this.votenum = votenum;
  }

  public int getVote_count() {
    return vote_count;
  }

  public void setVote_count(int vote_count) {
    this.vote_count = vote_count;
  }

  @Override
  public String toString() {
    return "Article [id=" + id + ", title=" + title + ", content=" + content + ", time=" + time + ", updated_time="
        + updated_time + ", view_count=" + view_count + ", original_id=" + original_id + ", level=" + level
        + ", enable=" + enable + ", username=" + username + ", board_id=" + board_id + ", timeLocal=" + timeLocal
        + ", updatedTimeLocal=" + updatedTimeLocal + ", writer=" + writer + ", tags=" + tags + ", commentlist="
        + commentlist + ", option=" + option + ", vote=" + vote + ", votenum=" + votenum + ", vote_count=" + vote_count
        + ", fileslist=" + fileslist + "]";
  }
}
