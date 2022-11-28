package com.example.nastya.controller;

import com.example.nastya.entity.Athletes;
import com.example.nastya.entity.Events;
import com.example.nastya.entity.EventsAthletes;
import com.example.nastya.entity.Users;
import com.example.nastya.entity.model.Event;
import com.example.nastya.exceptions.InvalidFormsException;
import com.example.nastya.service.AthletesService;
import com.example.nastya.service.EventsService;
import com.example.nastya.service.ReviewsService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Tag(name = "Controller for Admin")
@RestController
@Transactional
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger log = Logger.getLogger(AdminController.class);

    @Autowired
    private EventsService eventsService;

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private AthletesService athletesService;

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Check, is it admin?", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check, is it admin?") })
    @GetMapping()
    public ResponseEntity checkAdmin() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Add a new event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add a new event",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Events.class)) }) })
    @PostMapping(value = "/events")
    public ResponseEntity<Events> addEvent(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = Event.class))) @RequestBody Events event, BindingResult bindingResult) throws InvalidFormsException {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        Events ev = new Events(event.getName(), event.getDescription(), event.getRegion(), event.getDate());
        return new ResponseEntity<>(eventsService.addEvent(event), HttpStatus.OK) ;
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Change event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Change event",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Events.class)) }) })
    @PutMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeEvent(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = Event.class))) @RequestBody Events event, BindingResult bindingResult) throws InvalidFormsException {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        return new ResponseEntity<Events>(eventsService.editEvent(event), HttpStatus.OK);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Delete event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete event") })
    @DeleteMapping(value = "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteEvent(@PathVariable("id") long id) {
        eventsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Delete review", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete review") })
    @DeleteMapping(value = "/reviews/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteReview(@PathVariable("id") long id) {
        reviewsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Add athletes to event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add athletes to event") })
    @PostMapping(value = "/athletes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<EventsAthletes> addAthleteToEvent(@RequestBody ArrayList<EventsAthletes> eventsAthletes) {
        eventsAthletes.forEach(elem -> {
            eventsService.addAthleteInEvent(elem.getIdEvent(), elem.getIdAthlete());
        });
        return eventsAthletes;
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Delete athletes from event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete athletes from event") })
    @DeleteMapping(value = "/events/athlete", produces = MediaType.APPLICATION_JSON_VALUE)
    public EventsAthletes deleteAthleteFromEvent(@RequestBody EventsAthletes eventsAthletes) {
        return eventsService.deleteAthleteFromEvent(eventsAthletes.getIdEvent(), eventsAthletes.getIdAthlete());
    }

    //////////////////////////

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Add athletes", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add athletes") })
    @PostMapping(value = "/athlete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addAthlete(@Valid @RequestBody Athletes athlete, BindingResult bindingResult) throws InvalidFormsException {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        return new ResponseEntity<Athletes>(athletesService.addAthlete(athlete), HttpStatus.OK);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Change athlete", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Change athlete") })
    @PutMapping(value = "/athlete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editAthlete(@Valid @RequestBody Athletes athlete, BindingResult bindingResult) throws InvalidFormsException {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        return new ResponseEntity<Athletes>(athletesService.editAthlete(athlete), HttpStatus.OK);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Delete athlete", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete athlete") })
    @DeleteMapping(value = "/athlete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAthlete(@PathVariable("id") long id) {
        athletesService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
