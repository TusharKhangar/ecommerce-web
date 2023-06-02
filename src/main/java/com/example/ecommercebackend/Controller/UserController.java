package com.example.ecommercebackend.Controller;

import com.example.ecommercebackend.payload.UserDto;
import com.example.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        Date date = new Date();
        userDto.setDate(date);
        return  userService.createUser(userDto);
    }

    @GetMapping("/getUser/{userId}")
    @ResponseStatus(HttpStatus.FOUND)
     public UserDto getUserById(@PathVariable int userId) {
        return  userService.getUser(userId);
    }
    @DeleteMapping("/deleteUser/{userId}")
     public String deleteUser(@PathVariable int userId) {
        return  userService.deleteUser(userId);
    }

    @GetMapping("/getUsers")
    @ResponseBody
    @ResponseStatus(HttpStatus.FOUND)
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/updateUser/{userId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto updateUser(@PathVariable int userId, @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

}
