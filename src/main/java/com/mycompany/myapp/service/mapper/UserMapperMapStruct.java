package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.AuthorityRepository;
import com.mycompany.myapp.service.dto.AdminUserDTO;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapperMapStruct {
    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "StringsToAuthorities")
    @Mapping(target = "langKey", source = "langKey", defaultValue = Constants.DEFAULT_LANGUAGE)
    @Mapping(target = "email", expression = "java(adminUserDTO.getEmail().toLowerCase())")
    User toEntity(AdminUserDTO adminUserDTO);

    List<User> toEntities(List<AdminUserDTO> UserDto);
    AdminUserDTO toDto(User user);
    List<AdminUserDTO> toDtos(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "StringsToAuthorities")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    User partialUpdate(AdminUserDTO adminUserDTO, @MappingTarget User user);

    default Set<String> map(Set<Authority> value) {
        return value.stream().map(Authority::getName).collect(Collectors.toSet());
    }

    @Named("StringsToAuthorities")
    default Set<Authority> StringsToAuthorities(Set<String> DtoAuthorities) {
        if (DtoAuthorities != null) {
            return DtoAuthorities.stream().map(authority -> new Authority().name(authority)).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
