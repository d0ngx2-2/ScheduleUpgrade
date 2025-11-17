package com.scheduleupgrade.comment.service;

import com.scheduleupgrade.comment.dto.*;
import com.scheduleupgrade.comment.entity.Comment;
import com.scheduleupgrade.comment.repository.CommentRepository;
import com.scheduleupgrade.exception.CustomException;
import com.scheduleupgrade.exception.ErrorCode;
import com.scheduleupgrade.schedule.entity.Schedule;
import com.scheduleupgrade.schedule.repository.ScheduleRepository;
import com.scheduleupgrade.schedule.service.ScheduleService;
import com.scheduleupgrade.user.entity.User;
import com.scheduleupgrade.user.repository.UserRepository;
import com.scheduleupgrade.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest request, Long loginUserId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        User user = userRepository.findById(loginUserId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Comment comment = new Comment(request.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getUser().getUserName(),
                savedComment.getContent(),
                savedComment.getCreatedDate(),
                savedComment.getModifiedDate()
        );
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAllContent(Long scheduleId) {
        scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        List<Comment> comments = commentRepository.findAllbyScheduleId(scheduleId);
        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getUser().getUserName(),
                    comment.getContent(),
                    comment.getCreatedDate(),
                    comment.getModifiedDate()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentRequest request, Long loginUserId, Long scheduleId) {
        Comment comment = commentRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if(!comment.getUser().getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.COMMENT_FORBIDDEN);
        }

        comment.setContent(request.getContent());

        return new UpdateCommentResponse(comment.getId(), comment.getContent());
    }

    @Transactional
    public void deleteComment(Long loginUserId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if(comment.getUser().getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.COMMENT_FORBIDDEN);
        }

        commentRepository.delete(comment);
    }
}

