package com.example.portfolio;

import com.example.portfolio.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final UserService userService;

    // コンストラクタでUserServiceを注入
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    // 登録画面の表示
    @GetMapping("/register")
    public String registerForm() {
        return "register"; // templates/register.html を呼び出す
    }

    // 登録処理の実行
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {
        // ユーザー情報を保存
        userService.register(username, email, password);

        // 登録完了後はログイン画面へリダイレクト
        return "redirect:/login";
    }
}