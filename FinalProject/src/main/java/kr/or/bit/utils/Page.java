package kr.or.bit.utils;

public class Page {
  private final int articlesOnPage = 10;
  private final int pageButtons = 5;
  private int currentPage;
  private int startPage;
  private int endPage;
  private int totalPages;
  private boolean prev;
  private boolean next;
  // private int totalArticles;
  private int start;
  private int end;

  public Page(int currentPage, int totalArticles) {
    this.currentPage = currentPage;
    this.start = (currentPage - 1) * articlesOnPage;
    this.end = currentPage * articlesOnPage;
    int q = totalArticles / articlesOnPage;
    int r = totalArticles % articlesOnPage;
    this.totalPages = (r == 0) ? q : (q + 1);
    
    if (currentPage <= pageButtons) {
      this.startPage = 1;
    } else if (currentPage % pageButtons == 0) {
      this.startPage = currentPage - 4;
    } else {
      this.startPage = (currentPage / pageButtons) * 5 + 1;
    }
    
    if (currentPage <= pageButtons) {
      this.endPage = currentPage;
    } else if (currentPage % pageButtons == 0) {
      this.endPage = currentPage;
    } else if ((currentPage / pageButtons + 1) * 5 < totalPages) {
      this.endPage = (currentPage / pageButtons + 1) * 5;
    } else {
      this.endPage = totalPages;
    }
    
    this.prev = (currentPage <= 5) ? false : true;
    this.next = (currentPage / pageButtons + 1) * 5 < totalPages ? true : false;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getEnd() {
    return end;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getPageButtons() {
    return pageButtons;
  }

  public boolean isPrev() {
    return prev;
  }

  public void setPrev(boolean prev) {
    this.prev = prev;
  }

  public boolean isNext() {
    return next;
  }

  public void setNext(boolean next) {
    this.next = next;
  }
}
