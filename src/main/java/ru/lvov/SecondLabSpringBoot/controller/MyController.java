package ru.lvov.SecondLabSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.lvov.SecondLabSpringBoot.exception.UnsupportedCodeException;
import ru.lvov.SecondLabSpringBoot.exception.ValidationFailedException;
import ru.lvov.SecondLabSpringBoot.model.*;
import ru.lvov.SecondLabSpringBoot.service.ModifyResponseService;
import ru.lvov.SecondLabSpringBoot.service.ValidationService;
import ru.lvov.SecondLabSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService, @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponceService) {

        this.validationService = validationService;
        this.modifyResponseService = modifyResponceService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        log.info("request: {} ", request);
        log.info("Received request: {}", request);


        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        try {
            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("Код 123 не поддерживается");
            }



            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            log.error("Validation failed: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            if (bindingResult.hasErrors()) {
                for (FieldError error : bindingResult.getFieldErrors()) {
                    log.error("Validation error in field {}: {}", error.getField(), error.getDefaultMessage());
                }
                response.setCode(Codes.FAILED);
                response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
                response.setErrorMessage(ErrorMessages.VALIDATION);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        Response modifiedResponce = modifyResponseService.modify(response);
        log.info("Sending response: {}", modifiedResponce);
        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }


    }


