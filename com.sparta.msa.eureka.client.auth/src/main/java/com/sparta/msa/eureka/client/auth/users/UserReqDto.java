package com.sparta.msa.eureka.client.auth.users;

import com.sparta.msa.eureka.client.auth.core.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDto {
    private String userId;
    private String userName;
    private String password;

    // password 암호화 때문에 toEntity 작성 X
}
