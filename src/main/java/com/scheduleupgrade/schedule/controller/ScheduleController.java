package com.scheduleupgrade.schedule.controller;

import com.scheduleupgrade.common.exception.CustomException;
import com.scheduleupgrade.common.exception.ErrorCode;
import com.scheduleupgrade.schedule.dto.*;
import com.scheduleupgrade.schedule.service.ScheduleService;
import com.scheduleupgrade.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    //일정 생성
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> create(@SessionAttribute(name = "loginUser") SessionUser sessionUser,
                                                         @Valid @RequestBody CreateScheduleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request, sessionUser.getId()));
    }

    //전체 일정 조회
    @GetMapping("/schedules/all")
    public ResponseEntity<List<GetAllScheduleResponse>> getSchedules() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll());
    }

    //선택 일정 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(scheduleId));
    }

    //일정 페이징 조회
    @GetMapping("/schedules")
    public ResponseEntity<Page<GetPageScheduleResponse>> getPage(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getPage(pageable));
    }

    //일정 수정
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @SessionAttribute(name = "loginUser") SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request, sessionUser.getId(), request.getPassword()));
    }

    //일정 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> delete(@SessionAttribute(name = "loginUser") SessionUser sessionUser,
                                       @PathVariable Long scheduleId,
                                       @RequestBody DeleteScheduleRequest request) {

        scheduleService.delete(scheduleId, sessionUser.getId(), request.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
