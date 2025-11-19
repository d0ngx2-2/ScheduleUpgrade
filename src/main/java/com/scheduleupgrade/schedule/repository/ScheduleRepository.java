package com.scheduleupgrade.schedule.repository;

import com.scheduleupgrade.schedule.entity.Schedule;
import com.scheduleupgrade.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    void deleteAllByUser(User user);
}
