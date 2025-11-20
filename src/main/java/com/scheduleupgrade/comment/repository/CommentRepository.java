package com.scheduleupgrade.comment.repository;

import com.scheduleupgrade.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //댓글 전체 조회 메서드
    List<Comment> findAllByScheduleId(Long scheduleId);

    //해당 스캐줄에 있는 모든 댓글 삭제 메서드
    void deleteAllByScheduleId(Long scheduleId);

    //해당 유저에 있는 모든 댓글 삭제 메서드
    void deleteAllByUserId(Long userId);

    //해당 스캐줄에 있는 모든 댓글의 개수를 세는 메서드
    int countAllByScheduleId(Long scheduleId);
}
