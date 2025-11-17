package com.scheduleupgrade.comment.repository;

import com.scheduleupgrade.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllbyScheduleId(Long scheduleId);
}
