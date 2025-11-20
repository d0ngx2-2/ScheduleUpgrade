package com.scheduleupgrade.comment.service;

import com.scheduleupgrade.comment.dto.*;
import com.scheduleupgrade.common.entity.Comment;
import com.scheduleupgrade.comment.repository.CommentRepository;
import com.scheduleupgrade.common.config.PasswordEncoder;
import com.scheduleupgrade.common.exception.CustomException;
import com.scheduleupgrade.common.exception.ErrorCode;
import com.scheduleupgrade.common.entity.Schedule;
import com.scheduleupgrade.schedule.repository.ScheduleRepository;
import com.scheduleupgrade.common.entity.User;
import com.scheduleupgrade.user.repository.UserRepository;
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
    private final PasswordEncoder passwordEncoder;

    //댓글 생성
    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest request,
                                               Long loginUserId,
                                               Long scheduleId) {
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

    //한 일정 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {
        scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);
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

    //댓글 수정
    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentRequest request,
                                               Long loginUserId,
                                               Long scheduleId,
                                               String password) {
        Comment comment = commentRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if (!comment.getUser().getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.COMMENT_FORBIDDEN);
        }

        //비밀번호 암호화 검증을 위한 유저 데이터 끌고오기
        User user = comment.getUser();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        comment.setContent(request.getContent());

        return new UpdateCommentResponse(comment.getId(), comment.getContent());
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long loginUserId, Long commentId, String password) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if (comment.getUser().getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.COMMENT_FORBIDDEN);
        }

        //비밀번호 암호화 검증을 위한 유저 데이터 끌고오기
        User user = comment.getUser();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        commentRepository.delete(comment);
    }
}

