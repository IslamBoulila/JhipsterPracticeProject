package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.dto.AdminUserDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserMapperMapStructTest {

    private User user;
    private AdminUserDTO userDto;

    private UserMapperMapStruct userMapper;

    private static final String DEFAULT_LOGIN = "IslamB";
    private static final Long DEFAULT_ID = 4L;

    @BeforeEach
    public void init() {
        userMapper = new UserMapperMapStructImpl();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setFirstName("islam");
        user.setLastName("Bo");
        user.setImageUrl("image_url");
        user.setLangKey("en");

        userDto = new AdminUserDTO(
            user.getLogin(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getImageUrl(),
            user.isActivated(),
            user.getLangKey(),
            Collections.emptySet()
        );
    }

    @Test
    @DisplayName("Map UserDto With String Authorities To Entity Should Return User With <Authorities>")
    void UserDtoWithStringAuthoritiesToEntityShouldReturnUserWithAuthorities() {
        Set<String> stringAuthorities = new HashSet<>(Arrays.asList(AuthoritiesConstants.USER));
        userDto.setAuthorities(stringAuthorities);

        User user = userMapper.toEntity(userDto);

        assertThat(user).isNotNull();
        assertThat(user.getAuthorities()).isNotNull();
        assertThat(user.getAuthorities()).isNotEmpty();
        assertArrayEquals(user.getAuthorities().stream().map(Authority::getName).toArray(), stringAuthorities.toArray());
        assertEquals(user.getAuthorities().stream().findFirst().get().getName(), stringAuthorities.stream().findFirst().get());
    }
}
