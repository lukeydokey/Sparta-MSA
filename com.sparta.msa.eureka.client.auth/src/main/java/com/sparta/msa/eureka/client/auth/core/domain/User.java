package com.sparta.msa.eureka.client.auth.core.domain;

import com.sparta.msa.eureka.client.auth.core.enums.UserRole;
import com.sparta.msa.eureka.client.auth.users.UserReqDto;
import com.sparta.msa.eureka.client.auth.users.UserResDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String userName;
    private String password;
    private UserRole userRole;

    public UserResDto toResDto(){
        return new UserResDto(this.userId,this.userName);
    }
}
