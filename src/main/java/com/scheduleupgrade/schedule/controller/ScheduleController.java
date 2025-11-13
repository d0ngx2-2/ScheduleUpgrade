package com.scheduleupgrade.schedule.controller;

import com.scheduleupgrade.schedule.dto.*;
import com.scheduleupgrade.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ScheduleCreateResponse> create(@RequestBody ScheduleCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }
    //전체 일정 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetAllScheduleResponse>> getSchedules() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll());
    }
    //선택 일정 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(scheduleId));
    }
    //일정 수정
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
    }
    //일정 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId){
        scheduleService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
