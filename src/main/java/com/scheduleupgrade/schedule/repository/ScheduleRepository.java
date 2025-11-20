package com.scheduleupgrade.schedule.repository;

import com.scheduleupgrade.common.entity.Schedule;
import com.scheduleupgrade.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    //해당 유저에 있는 모든 일정을 삭제하는 메서드
    void deleteAllByUser(User user);
}
