package com.example.CrudServiceUCX.service;
import com.example.CrudServiceUCX.model.User;
import com.example.CrudServiceUCX.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser (User user) {
        user.setSalt(new String(getSalt(), StandardCharsets.UTF_8));
        user.setPassword(hashPassword(user.getPassword(), user.getSalt().getBytes()));
        try {
            return (userRepository.save(user));
        } catch (Exception ex) {
            return null;
        }
    }

    public List<User> getUsers () {
        return userRepository.findAll();
    }

    public User updateUser (User user, Long userId) {
        if (checkIfUserExists(userId)) {
            user.setSalt(new String(getSalt(), StandardCharsets.UTF_8));
            return updateUserInfo(userRepository.findById(userId).get(), user);
        }else {
            return null;
        }
    }

    public User deleteUser (Long userId) {
        if (checkIfUserExists(userId)) {
            User x = userRepository.findById(userId).get();
            userRepository.delete(x);
            return x;
        }else {
            return null;
        }
    }
    public List<User> getUserByName (String firstName) {
        List<User> results = new ArrayList<>();
        for(User u : userRepository.findAll()) {
            if (u.getFirstName().equalsIgnoreCase(firstName)) {
                results.add(u);
            }
        }
        return results;
    }

    private byte[] getSalt () {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private String hashPassword (String password, byte [] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    private boolean checkIfUserExists (Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return true;
        }else {
            return false;
        }
    }
    private User updateUserInfo (User old, User newUser) {
        old.setEmail(newUser.getEmail());
        old.setFirstName(newUser.getFirstName());
        old.setLastName(newUser.getLastName());
        old.setPassword(hashPassword(newUser.getPassword(), newUser.getSalt().getBytes()));
        userRepository.save(old);
        return old;
    }
}
