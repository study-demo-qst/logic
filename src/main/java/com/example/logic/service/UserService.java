package com.example.logic.service;

import com.example.common.model.User;
import com.example.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ユーザーサービスクラスは、ユーザーに関連する操作を提供します。
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 全てのユーザーを取得します。
     * 
     * @return ユーザーのリスト
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * 指定されたIDのユーザーを取得します。
     * 
     * @param id ユーザーID
     * @return 指定されたIDのユーザー、存在しない場合は空のOptional
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 指定されたユーザー名に一致するユーザーを取得します。
     * 
     * @param username ユーザー名
     * @return ユーザーのリスト
     */
    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * ユーザーを保存します。
     * 
     * @param user 保存するユーザー
     * @return 保存されたユーザー
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * 指定されたIDのユーザーを削除します。
     * 
     * @param id ユーザーID
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
