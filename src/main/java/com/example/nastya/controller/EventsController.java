package com.example.nastya.controller;

import com.example.nastya.entity.Events;
import com.example.nastya.entity.EventsAthletes;
import com.example.nastya.entity.Reviews;
import com.example.nastya.service.*;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Event controller")
@RestController
@Transactional
@RequestMapping("/api/events")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Get all events", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all events") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Events> getAllEvents() {
        return eventsService.getAll();
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Get event by id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get event by id") })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Events getEventById(@PathVariable("id") long id) {
        return eventsService.getById(id);
    }

    @Hidden
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Events> searchEvents(@RequestParam("search") String search) {
        return eventsService.searchEvents(search);
    }
}
