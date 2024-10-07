package br.com.elotech.mapper;


import br.com.elotech.entity.User;
import br.com.elotech.request.UserRequest;
import br.com.elotech.response.UserResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest userRequest);

    UserResponse toResponse(User user);

    List<UserResponse> toResponse(List<User> user);

    default Page<UserResponse> toPageUserResponse(Page<User> page) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : page.getContent()) {
            userResponses.add(toResponse(user));
        }
        return new PageImpl<>(userResponses, page.getPageable(), page.getTotalElements());
    }
}
