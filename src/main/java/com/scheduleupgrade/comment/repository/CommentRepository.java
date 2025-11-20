package com.scheduleupgrade.comment.repository;

import com.scheduleupgrade.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByScheduleId(Long scheduleId);

    void deleteAllByScheduleId(Long scheduleId);

    void deleteAllByUserId(Long userId);

    int countAllByScheduleId(Long scheduleId);
}
