package com.scheduleupgrade.schedule.service;

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
    public ScheduleCreateResponse save(ScheduleCreateRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                ()-> new IllegalArgumentException("존재하지않는 유저입니다.")
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
                () -> new IllegalArgumentException("없는 일정입니다.")
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
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("없는 일정입니다.")
        );

        schedule.update(request.getTitle(), request.getContent());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent()
        );
    }
    //일정 삭제
    @Transactional
    public void delete(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        if (!existence) {
            throw new IllegalArgumentException("없는 일정입니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
