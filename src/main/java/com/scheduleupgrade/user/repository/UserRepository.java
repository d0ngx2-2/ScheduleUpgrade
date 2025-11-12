package com.scheduleupgrade.user.repository;

import com.scheduleupgrade.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
