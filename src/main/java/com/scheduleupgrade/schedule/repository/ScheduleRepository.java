package com.scheduleupgrade.schedule.repository;

import com.scheduleupgrade.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
