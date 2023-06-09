package com.example.ecommercebackend.service;

import com.example.ecommercebackend.Exception.ResourceNotFoundException;
import com.example.ecommercebackend.Model.User;
import com.example.ecommercebackend.Repository.UserRepository;
import com.example.ecommercebackend.payload.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper mapper;

    //userDto to user then saving it into the database
    //after that we are returning the userDto
    public UserDto createUser(UserDto userDto){
        User map = mapper.map(userDto, User.class);
        //getting passowrd
        String pass = map.getPassword();
        //encoding password
        String encode = this.passwordEncoder.encode(pass);
        System.out.println(encode + "encode");
        map.setPassword(encode);

        User save = userRepository.save(map);
        return mapper.map(save, UserDto.class);
    }
    public UserDto getUser(int userId){
        User   byId = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("This id not found"));
        return mapper.map(byId, UserDto.class);
    }

    public String deleteUser(int userId) {
        User user = findUser(userId);
        userRepository.delete(user);
        return "This user is deleted "+ userId;
    }

    private User findUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist"));
    }

    public List<UserDto> getUsers() {
        List<User> all = userRepository.findAll();
        return all.stream().map((each) -> mapper.map(each, UserDto.class)).toList();
    }


    public UserDto updateUser(int userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist"));
        //userDto to user
        User map = mapper.map(userDto, User.class);
        //save the user
        User save = userRepository.save(map);
        //return the user in the userDto form
        return mapper.map(save, UserDto.class, "UserDto");
    }
}
