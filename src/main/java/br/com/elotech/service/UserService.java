package br.com.elotech.service;

import br.com.elotech.entity.User;
import br.com.elotech.mapper.UserMapper;
import br.com.elotech.repository.UserRepository;
import br.com.elotech.request.UserRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> getAllUsersPaged(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> getAllUsersList() {
        return userRepository.findAll();
    }

    public User createUser(UserRequest createRequest) {
        User user = userMapper.toEntity(createRequest);
        user.setRegistrationDate(LocalDate.now());
        return userRepository.save(user);
    }

    public User updateUser(UserRequest updateRequest) {
        return getUser(updateRequest.id()).map(user -> {
            User updatedUser = userMapper.toEntity(updateRequest);
            return userRepository.save(updatedUser);
        }).orElseThrow(() -> new EntityNotFoundException("Livro com o ID " + updateRequest.id() + " não encontrado"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
