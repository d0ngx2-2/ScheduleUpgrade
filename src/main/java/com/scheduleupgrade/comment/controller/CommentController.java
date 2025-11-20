package com.scheduleupgrade.comment.controller;

import com.scheduleupgrade.comment.dto.*;
import com.scheduleupgrade.comment.service.CommentService;
import com.scheduleupgrade.common.exception.CustomException;
import com.scheduleupgrade.common.exception.ErrorCode;
import com.scheduleupgrade.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 생성 기능
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> create(
            @SessionAttribute(name = "loginUser") SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(request, sessionUser.getId(), scheduleId));
    }

    //전체 댓글 조회 기능
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getAllComments(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(scheduleId));
    }

    //댓글 수정 기능
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @SessionAttribute(name = "loginUser") SessionUser sessionUser,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request
    ) {


        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(request, sessionUser.getId(), commentId, request.getPassword()));
    }

    //댓글 삭제 기능
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "loginUser") SessionUser sessionUser,
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteCommentRequest request
    ) {

        commentService.deleteComment(sessionUser.getId(), commentId, request.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
