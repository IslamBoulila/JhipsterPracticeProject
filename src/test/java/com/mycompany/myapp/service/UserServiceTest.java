package com.mycompany.myapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.dto.AdminUserDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import com.mycompany.myapp.service.mapper.UserMapper;
import java.time.Instant;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.jhipster.security.RandomUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserMapper userMapper;

    @Mock
    CacheManager cacheManager;

    @Mock
    Cache cacheMock;

    @InjectMocks
    UserService userService;

    AdminUserDTO adminUserDTO;

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
        adminUserDTO = new AdminUserDTO();
    }

    @AfterEach
    void tearDown() {}

    @Test
    void registerUser() {}

    @DisplayName("Happy path test: createUser test")
    @ParameterizedTest
    @ValueSource(strings = { "Afaf", "Fatima Zohra" }) //@CsvSource,  @MethodSource, @EnumSource,
    void createUser(String login) {
        //create userDTO

        adminUserDTO.setLogin(login);
        User usertoInsert = new User();
        usertoInsert.setLogin(login);

        User insertedUser;
        //expected values
        User expectedInsUser = new User();
        expectedInsUser.setLogin(login);
        expectedInsUser.setActivated(true);
        expectedInsUser.setPassword("password");
        expectedInsUser.setResetDate(Instant.now());

        when(userMapper.userDTOToUser(adminUserDTO)).thenReturn(usertoInsert);
        // doReturn(usertoInsert).when(userMapper).userDTOToUser(any());
        doReturn("password").when(passwordEncoder).encode(any());
        when(userRepository.save(usertoInsert)).thenReturn(usertoInsert);
        doReturn(cacheMock).when(cacheManager).getCache(any());

        insertedUser = userService.createUser(adminUserDTO);
        //Assert
        assertEquals(expectedInsUser.isActivated(), insertedUser.isActivated(), "Testing if user isAcivated");
        assertEquals(expectedInsUser.getPassword(), insertedUser.getPassword());
        assertNotNull(insertedUser.getResetKey());
        assertEquals(Instant.class, insertedUser.getResetDate().getClass());

        //verify
        verify(userRepository).save(usertoInsert);
    }

    @Test
    void updateUser() {}

    @Test
    void deleteUser() {}

    @Test
    void changePassword() {}
}
