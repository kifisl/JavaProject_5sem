package com.example.nastya.controller;

import com.example.nastya.entity.Events;
import com.example.nastya.entity.model.Error;
import com.example.nastya.entity.model.Token;
import com.example.nastya.entity.Users;
import com.example.nastya.entity.model.Security;
import com.example.nastya.exceptions.AuthException;
import com.example.nastya.exceptions.InvalidFormsException;
import com.example.nastya.exceptions.RegisterException;
import com.example.nastya.service.UsersService;
import com.example.nastya.configuration.jwt.JwtProvider;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Authorisation and registration controller")
@RestController
public class AuthController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private JwtProvider jwtProvider;

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Registration", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Users user, BindingResult bindingResult) throws InvalidFormsException, RegisterException {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        if(!usersService.addUser(user)) throw new RegisterException("This login already exists");
        else return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Authorization", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorization",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Token.class)) }) })
    @PostMapping("/auth")
    public ResponseEntity<?> auth(@Valid @RequestBody Security security, BindingResult bindingResult) throws InvalidFormsException, AuthException {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        Users user = usersService.findByLoginAndPassword(security.getLogin(), security.getPassword());
        if(user == null) {
            throw new AuthException("Incorrect login or password");
        }
        else if(user.getActivationCode() != null) {
            throw new AuthException("Please, go to the email indicated during registration and activate your account");
        }
        else {
            String token = jwtProvider.generateToken(user.getLogin());
            return new ResponseEntity<Token>(new Token(token), HttpStatus.OK);
        }
    }
}
