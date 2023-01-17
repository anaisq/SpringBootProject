package main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.dto.UserDto;
import main.entity.RoleType;
import main.entity.StatusType;
import main.exception.NotFoundException;
import main.service.UserService;
import main.utils.UsersMocks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void TestAddUser() throws Exception {
        // Arrange
        UserDto userDto = UsersMocks.mockUserDto();
        when(userService.addUser(any())).thenReturn(userDto);

        String userDtoBody = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(post("/users")
                        .content(userDtoBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), userDtoBody);
    }

    @Test
    public void testGetUserById() throws Exception {
        UserDto userDto = UsersMocks.mockUserDto();

        when(userService.getById(1L)).thenReturn(Optional.of(userDto));

        MvcResult result = mockMvc.perform(get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(Optional.of(userDto)));
    }

    @Test
    public void testGetUserByIdWithException() throws Exception {
        when(userService.getById(2L)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/users/{id}", "2L")
                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testGetUserByRoleAndStatus() throws Exception {
        UserDto userDto = UsersMocks.mockUserDto();

        when(userService.getUserByRoleAndStatus(RoleType.ADMIN, StatusType.RELIABLE)).thenReturn(Arrays.asList(userDto));

        MvcResult result = mockMvc.perform(get("/users/filter")
                        .param("role", String.valueOf(RoleType.ADMIN))
                        .param("status", String.valueOf(StatusType.RELIABLE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(Arrays.asList(userDto)));
    }

    @Test
    public void testUpdateStatus() throws Exception {

        UserDto userDto = UsersMocks.mockUserDto();
        userDto.setStatus(StatusType.BANNED);

        when(userService.updateStatus(any(), any())).thenReturn(userDto);
        mockMvc.perform(put("/users/{id}/{status}", 1L, "BANNED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect((ResultMatcher) jsonPath("$.status").value(StatusType.BANNED));
    }

    @Test
    public void testDelete() throws Exception {
        String username = "admin";

        when(userService.deleteUser(username)).thenReturn("User: " + username + " was deleted");

        mockMvc.perform(delete("/users/{username}","admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
}

}
