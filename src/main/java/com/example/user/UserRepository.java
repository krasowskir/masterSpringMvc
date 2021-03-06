package com.example.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final Map<String, User> userMap = new ConcurrentHashMap<String, User>();

    public User save(String email, User user) {
	user.setEmail(email);
	return userMap.put(email, user);
    }

    public User save(User user) {
	return save(user.getEmail(), user);
    }

    public User findOne(String email) {
	return userMap.get(email);
    }

    public List<User> findAll() {
	return new ArrayList<User>(userMap.values());
    }

    public void remove(String email) {
	userMap.remove(email);
    }

    public boolean exists(String email) {
	return userMap.containsKey(email);
    }
}
