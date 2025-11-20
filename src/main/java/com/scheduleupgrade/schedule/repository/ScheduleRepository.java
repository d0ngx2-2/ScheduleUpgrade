package com.scheduleupgrade.schedule.repository;

import com.scheduleupgrade.common.entity.Schedule;
import com.scheduleupgrade.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    void deleteAllByUser(User user);
}
