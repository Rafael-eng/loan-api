package br.com.elotech.controller;

import br.com.elotech.entity.User;
import br.com.elotech.mapper.UserMapper;
import br.com.elotech.request.UserRequest;
import br.com.elotech.response.UserResponse;
import br.com.elotech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserRequest request) {
        User createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);
        return user.map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest request) {
        User updatedUser = userService.updateUser(request);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(value = "all", required = false, defaultValue = "false") boolean all,
            @PageableDefault(page = 0, size = 5) Pageable pageable,
            PagedResourcesAssembler<UserResponse> pagedAssembler) {
        if (all) {
            List<User> users = userService.getAllUsersList();
            return ResponseEntity.ok(userMapper.toResponse(users));
        } else {
            Page<User> pageResult = userService.getAllUsersPaged(pageable);
            Page<UserResponse> responsePage = userMapper.toPageUserResponse(pageResult);
            return ResponseEntity.ok(pagedAssembler.toModel(responsePage));
        }

    }
}
