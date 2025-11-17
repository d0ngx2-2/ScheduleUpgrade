package com.scheduleupgrade.schedule.service;

import com.scheduleupgrade.exception.CustomException;
import com.scheduleupgrade.exception.ErrorCode;
import com.scheduleupgrade.schedule.dto.*;
import com.scheduleupgrade.schedule.entity.Schedule;
import com.scheduleupgrade.schedule.repository.ScheduleRepository;
import com.scheduleupgrade.user.entity.User;
import com.scheduleupgrade.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    //일정 생성
    @Transactional
    public ScheduleCreateResponse save(ScheduleCreateRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Schedule schedule = new Schedule(user, request.getTitle(), request.getContent());
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new ScheduleCreateResponse(
                saveSchedule.getId(),
                saveSchedule.getUser().getUserName(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getCreatedDate(),
                saveSchedule.getLastModifiedDate()
        );
    }

    //전체 조회
    @Transactional(readOnly = true)
    public List<GetAllScheduleResponse> getAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetAllScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetAllScheduleResponse dto = new GetAllScheduleResponse(
                    schedule.getUser().getUserName(),
                    schedule.getTitle(),
                    schedule.getCreatedDate(),
                    schedule.getLastModifiedDate()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    //선택 조회
    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getUserName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedDate(),
                schedule.getLastModifiedDate()
        );
    }
    //일정 수정
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request, Long loginUserId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.SCHEDULE_FORBIDDEN);
        }

        schedule.update(request.getTitle(), request.getContent());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent()
        );
    }
    //일정 삭제
    @Transactional
    public void delete(Long scheduleId, Long loginUserId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.SCHEDULE_FORBIDDEN);
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
