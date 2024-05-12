package org.kst.storemgmtbackend.mappers;

import org.kst.storemgmtbackend.dtos.RegisterRequest;
import org.kst.storemgmtbackend.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUserModel(RegisterRequest registerRequest);

    RegisterRequest mapToRegisterRequestDto(User user);
}
