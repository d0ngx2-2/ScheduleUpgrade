package com.scheduleupgrade.user.repository;

import com.scheduleupgrade.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //중복되는 이메일 검증을 위한 메서드
    boolean existsByEmail(String email);
    //이메일로 User를 조회하는 메서드
    Optional<User> findByEmail(String email);
}
