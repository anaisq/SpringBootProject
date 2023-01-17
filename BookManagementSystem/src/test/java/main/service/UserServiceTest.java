package main.service;

import main.dto.UserDto;
import main.entity.StatusType;
import main.entity.User;
import main.exception.AlreadyOnDbException;
import main.exception.NotFoundException;
import main.mapper.UserMapper;
import main.repository.UserRepository;
import main.utils.UsersMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private User user;
    private UserDto userDto;

    @Test
    public void whenUserDoesntExist_create_saveTheUser(){
        // Arrange
        user = UsersMocks.mockUser();
        userDto = UsersMocks.mockUserDto();

        // Act
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        when(userMapper.mapToUser(userDto)).thenReturn(user);

        UserDto result = userService.addUser(userDto);

        // Assert

        assertTrue(result.getUsername().equals(userDto.getUsername()));
        assertNotNull(result);
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    public void whenUserExist_throwException(){
        // Arrange
        user = UsersMocks.mockUser();
        userDto = UsersMocks.mockUserDto();

        // Act
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(user));

        AlreadyOnDbException alreadyOnDbException =
                assertThrows(AlreadyOnDbException.class , () -> userService.addUser(userDto));

        // Assert
        assertEquals("An user with same username " + userDto.getUsername() + " already exists", alreadyOnDbException.getMessage());
    }

    @Test
    public void whenUserExist_updateStatus(){
        // Arrange
        user = UsersMocks.mockUser();
        // userDto = UsersMocks.mockUserDto();
        Long id = 1L;
        String status = "FLAKE";

        // Act
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

         userService.updateStatus(id, status);

        // Assert

        assertTrue(user.getStatus().equals(StatusType.FLAKE));
    }

    @Test
    public void whenUserExist_deleteUser(){
        // Arrange
        user = UsersMocks.mockUser();
        // userDto = UsersMocks.mockUserDto();
        String username = "admin";

        // Act
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(username);
        // Assert

        assertEquals(result, "User: " + username + " was deleted");
    }

    @Test
    public void whenUserDoesntExist_deleteUser(){
        // Arrange
        user = UsersMocks.mockUser();
        // userDto = UsersMocks.mockUserDto();
        String username = "admin";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        // Act
        NotFoundException notFoundException =
                assertThrows(NotFoundException.class , () -> userService.deleteUser(username));

        // Assert
        assertEquals(String.format("No user with username: %s was found", username), notFoundException.getMessage());
    }

}
