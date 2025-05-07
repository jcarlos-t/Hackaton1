package com.sparky.service;

import com.sparky.Domain.User;
import com.sparky.Domain.UserLimit;
import com.sparky.repository.UserLimitRepository;
import com.sparky.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLimitRepository userLimitRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldSaveAndReturnUser() {
        User user = new User();
        user.setUsername("jhon");
        user.setEmail("jhon@example.com");

        when(userRepository.save(any())).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals("jhon", result.getUsername());
        assertEquals("jhon@example.com", result.getEmail());
    }

    @Test
    void getUsersByCompany_shouldReturnList() {
        when(userRepository.findByCompanyId(1L)).thenReturn(List.of(new User(), new User()));

        List<User> users = userService.getUsersByCompany(1L);

        assertEquals(2, users.size());
    }

    @Test
    void getUserById_existingId_shouldReturnUser() {
        User user = new User();
        user.setId(7L);

        when(userRepository.findById(7L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(7L);

        assertTrue(result.isPresent());
        assertEquals(7L, result.get().getId());
    }

    @Test
    void updateUser_shouldUpdateFields() {
        User original = new User();
        original.setUsername("old");
        original.setEmail("old@example.com");

        User updated = new User();
        updated.setUsername("new");
        updated.setEmail("new@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(original));
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.updateUser(1L, updated);

        assertEquals("new", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
    }

    @Test
    void assignUserLimit_shouldBindLimitToUser() {
        User user = new User();
        user.setId(1L);

        UserLimit limit = new UserLimit();
        limit.setModelName("gpt-4");
        limit.setRemainingRequests(100);
        limit.setRemainingTokens(1000);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userLimitRepository.save(any(UserLimit.class))).thenAnswer(inv -> {
            UserLimit arg = inv.getArgument(0);
            arg.setUser(user);  // Asignación simulada
            return arg;
        });

        UserLimit result = userService.assignUserLimit(1L, limit);

        assertNotNull(result.getUser());
        assertEquals(1L, result.getUser().getId());
        assertEquals("gpt-4", result.getModelName());
        assertEquals(100, result.getRemainingRequests());
        assertEquals(1000, result.getRemainingTokens());
    }


    @Test
    void getUserLimits_shouldReturnAllLimits() {
        when(userLimitRepository.findByUserId(1L)).thenReturn(List.of(new UserLimit(), new UserLimit()));

        List<UserLimit> result = userService.getUserLimits(1L);

        assertEquals(2, result.size());
    }
}
