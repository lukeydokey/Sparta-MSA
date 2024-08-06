package com.sparta.msa_exam.auth.users;

import com.sparta.msa_exam.auth.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
