package com.sparta.msa_exam.auth.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInReqDto {
    private String userId;
    private String password;
}
