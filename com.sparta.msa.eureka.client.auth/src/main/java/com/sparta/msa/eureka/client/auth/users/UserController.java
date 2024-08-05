package com.sparta.msa.eureka.client.auth.users;

import com.sparta.msa.eureka.client.auth.core.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody SignInReqDto requestDto){
        SignInResDto res = userService.signIn(requestDto);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserReqDto requestDto) {
        UserResDto res = userService.signUp(requestDto);
        return ResponseEntity.ok(res);
    }
}
