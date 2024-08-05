package com.sparta.msa.eureka.client.auth.users;

import com.sparta.msa.eureka.client.auth.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
