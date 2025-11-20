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

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> create(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request) {
        if (sessionUser == null) {
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(request, sessionUser.getId(), scheduleId));
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getAllComments(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(scheduleId));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request
    ) {
        if (sessionUser == null) {
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(request, sessionUser.getId(), commentId, request.getPassword()));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteCommentRequest request
    ) {
        if (sessionUser == null) {
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }

        commentService.deleteComment(sessionUser.getId(), commentId, request.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
