package com.helvetia.m295.libraryserver.service;

import com.helvetia.m295.libraryserver.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/session")
public class SessionController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public @ResponseBody String getRole() {
        try {
            return userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName()).get().getRoles();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
