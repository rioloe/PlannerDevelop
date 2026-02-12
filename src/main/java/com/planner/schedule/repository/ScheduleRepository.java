package com.planner.schedule.repository;


import com.planner.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 수정일 기준 내림차순
    List<Schedule> findAllByAuthorOrderByModifiedAt(String author);

    List<Schedule> findAllByOrderByModifiedAt();
}
