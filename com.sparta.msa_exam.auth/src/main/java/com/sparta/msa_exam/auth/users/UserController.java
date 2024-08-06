package com.sparta.msa_exam.auth.users;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @Value("${server.port}")
    private int serverPort;

    @PostMapping("/signIn")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody SignInReqDto requestDto){
        SignInResDto res = userService.signIn(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Server-Port", String.valueOf(serverPort))
                .body(res);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserReqDto requestDto) {
        UserResDto res = userService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Server-Port", String.valueOf(serverPort))
                .body(res);
    }
}
