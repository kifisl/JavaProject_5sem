package com.example.nastya.controller;

import com.example.nastya.entity.Users;
import com.example.nastya.service.UsersService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User controller")
@RestController
@RequestMapping("/api/users/{login}")
public class UserController {
    @Autowired
    private UsersService usersService;

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Get user by login", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user by login") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Users getUser(@PathVariable("login") String login) {
        return usersService.findByLogin(login);
    }
}
