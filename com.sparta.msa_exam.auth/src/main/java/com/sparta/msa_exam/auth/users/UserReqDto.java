package com.sparta.msa_exam.auth.users;

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
