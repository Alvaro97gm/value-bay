package com.alvarogm.valuebay.persistence.domain.mapper;

import com.alvarogm.valuebay.persistence.domain.dto.UserDTO;
import com.alvarogm.valuebay.persistence.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserDTO userToUserDTO(User user);
    public abstract User userDTOToUser(UserDTO userDTO);
    public abstract List<UserDTO> usersToUserDTOs(List<User> users);
    public abstract List<User> usersDTOsToUsers(List<UserDTO> userDTOs);
}
