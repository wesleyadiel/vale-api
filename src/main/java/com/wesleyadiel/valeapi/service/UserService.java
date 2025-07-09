package com.wesleyadiel.valeapi.service;

import com.wesleyadiel.valeapi.dto.request.UserRequest;
import com.wesleyadiel.valeapi.exception.EmailInUseException;
import com.wesleyadiel.valeapi.exception.UserNotFoundException;
import com.wesleyadiel.valeapi.mapper.UserMapper;
import com.wesleyadiel.valeapi.model.User;
import com.wesleyadiel.valeapi.repository.UserRepository;
import com.wesleyadiel.valeapi.util.IdUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordService passwordService;
    private final UserMapper userMapper;

    public UserService(UserRepository repository, UserMapper userMapper, PasswordService passwordService) {
        this.repository = repository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
    }

    public void createUser(UserRequest user) {
        User newUser = userMapper.toEntity(user);
        newUser.setId(IdUtil.generateId());
        newUser.setSenha(passwordService.hashPassword(user.getSenha()));

        if (repository.findByEmail(newUser.getEmail()).isPresent())
            throw new EmailInUseException("Usuário com email " + newUser.getEmail() + " já existe.");

        repository.save(newUser);
    }

    public User getUserById(String id) {
        User user = repository.findById(id);
        if (user == null)
            throw new UserNotFoundException("Usuário com ID " + id + " não encontrado.");

        return user;
    }

    public void deleteUserById(String id) {
        User existing = repository.findById(id);
        if (existing == null)
            throw new UserNotFoundException("Usuário com ID " + id + " não encontrado.");

        repository.delete(id);
    }
}
