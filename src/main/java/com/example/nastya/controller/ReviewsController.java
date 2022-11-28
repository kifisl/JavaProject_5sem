package com.example.nastya.controller;

import com.example.nastya.entity.Reviews;
import com.example.nastya.entity.model.Error;
import com.example.nastya.exceptions.InvalidFormsException;
import com.example.nastya.service.ReviewsService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Review controller")
@RestController
@Transactional
@RequestMapping("/api/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Get all reviews", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all reviews") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reviews> getAllReviews() {
        return reviewsService.getAll();
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Get reviews by event id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get reviews by event id") })
    @GetMapping(value = "/event/{idE}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reviews> getReviewsEvent(@PathVariable("idE") long idE) {
        return reviewsService.getReviewsByIdEvent(idE);
    }

    @Parameters(value = {
            @Parameter(name = "Authorization", required = true,
                    example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYwNzIwMjAwMH0.Dq5g7R0BACYrHgN7yxDMj94WQamTT2VP0vvE-tT_6Vekl28YtWKWWqI8eb6fj3eM5pQuuAsu6Kp_VeUO68H1xA",
                    content = @Content(schema = @Schema(type = "string")),
                    in = ParameterIn.HEADER, style = ParameterStyle.FORM)})
    @Operation(summary = "Add review to event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add review to event") })
    @PostMapping(value = "/events/{idE}/users/{idU}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addReview(@PathVariable("idE") long idE, @PathVariable("idU") long idU, @Valid @RequestBody Reviews review,  BindingResult bindingResult) throws InvalidFormsException {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getField() + ": " + bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidFormsException(errorMessage);
        }
        return new ResponseEntity<Reviews>(reviewsService.addReview(idE, idU, review), HttpStatus.OK);
    }


}
