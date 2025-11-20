package com.scheduleupgrade.schedule.service;

import com.scheduleupgrade.comment.repository.CommentRepository;
import com.scheduleupgrade.common.config.PasswordEncoder;
import com.scheduleupgrade.common.exception.CustomException;
import com.scheduleupgrade.common.exception.ErrorCode;
import com.scheduleupgrade.schedule.dto.*;
import com.scheduleupgrade.common.entity.Schedule;
import com.scheduleupgrade.schedule.repository.ScheduleRepository;
import com.scheduleupgrade.common.entity.User;
import com.scheduleupgrade.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    //일정 생성
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Schedule schedule = new Schedule(user, request.getTitle(), request.getContent());
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                saveSchedule.getId(),
                saveSchedule.getUser().getUserName(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                saveSchedule.getCreatedDate(),
                saveSchedule.getModifiedDate()
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
                    schedule.getModifiedDate()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    //선택 조회
    @Transactional(readOnly = true)
    public GetOneScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getUser().getUserName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedDate(),
                schedule.getModifiedDate()
        );
    }

    //일정 수정
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId,
                                         UpdateScheduleRequest request,
                                         Long loginUserId,
                                         String password
    ) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.SCHEDULE_FORBIDDEN);
        }

        //비밀번호 암호화 검증을 위한 유저 데이터 끌고오기
        User user = schedule.getUser();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
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
    public void delete(Long scheduleId, Long loginUserId, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.SCHEDULE_FORBIDDEN);
        }

        //비밀번호 암호화 검증을 위한 유저 데이터 끌고오기
        User user = schedule.getUser();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        commentRepository.deleteAllByScheduleId(scheduleId);

        scheduleRepository.deleteById(scheduleId);
    }

    //일정 페이징 조회
    @Transactional
    public Page<GetPageScheduleResponse> getPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "lastModifiedDate")
        );

        Page<Schedule> schedules = scheduleRepository.findAll(pageRequest);

        return schedules.map(schedule -> new GetPageScheduleResponse(
                schedule.getUser().getUserName(),
                schedule.getTitle(),
                schedule.getContent(),
                commentRepository.countAllByScheduleId(schedule.getId()),     // 댓글 개수
                schedule.getCreatedDate(),
                schedule.getModifiedDate()
        ));
    }
}
