package com.example.portfolio.service;

import com.example.portfolio.entity.User;
import com.example.portfolio.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder; // 追加
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // SecurityConfigから注入される

    // コンストラクタでPasswordEncoderを受け取る
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        
        // 【重要】パスワードをハッシュ化してセット
        user.setPassword(passwordEncoder.encode(password));
        
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + username));
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // ここにはハッシュ化されたパスワードが入っている必要がある
                new ArrayList<>()
        );
    }
}