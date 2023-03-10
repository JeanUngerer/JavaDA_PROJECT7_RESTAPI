package com.nnk.springboot.mappers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dtos.UserDTO;
import com.nnk.springboot.helpers.CycleAvoidingMappingContext;
import com.nnk.springboot.models.UserModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserDTO modelToDto(UserModel userModel);

    List<UserDTO> modelsToDtos(List<UserModel> userModels);

    UserModel dtoToModel(UserDTO dto);

    List<UserModel> dtosToModels(List<UserDTO> dtos);

    User modelToEntity(UserModel userModel);

    List<User> modelsToEntities(List<UserModel> userModels);

    UserModel entityToModel(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserModelFromDto(UserDTO dto, @MappingTarget UserModel model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
