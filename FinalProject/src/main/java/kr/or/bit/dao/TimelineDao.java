package kr.or.bit.dao;

import java.util.List;

import kr.or.bit.model.Timeline;

public interface TimelineDao {
  
  List<Timeline> selectTimelineByGroupId(int group_id);
  
  void insertTimeline(Timeline timeline);
  
}
