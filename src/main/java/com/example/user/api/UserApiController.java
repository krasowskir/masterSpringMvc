package com.example.user.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.User;
import com.example.user.UserRepository;

@RestController
@RequestMapping("/api")
public class UserApiController {

    private UserRepository userRepository;

    @Autowired
    public UserApiController(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> findAll() {
	return userRepository.findAll();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
	HttpStatus httpStatus = HttpStatus.OK;
	if (!userRepository.exists(user.getEmail())) {
	    httpStatus = HttpStatus.CREATED;
	}
	User saved = userRepository.save(user);
	return new ResponseEntity<>(saved, httpStatus);
    }

    @RequestMapping(value = "/user/{email}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
	if (!userRepository.exists(email)) {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	User saved = userRepository.save(email, user);
	return new ResponseEntity<User>(saved, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable String email) {
	if (!userRepository.exists(email)) {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	userRepository.remove(email);
	return new ResponseEntity<>(HttpStatus.OK);
    }

}
