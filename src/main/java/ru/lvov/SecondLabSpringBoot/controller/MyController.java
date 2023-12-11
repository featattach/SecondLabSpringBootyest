package ru.lvov.SecondLabSpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.lvov.SecondLabSpringBoot.exception.UnsupportedCodeException;
import ru.lvov.SecondLabSpringBoot.exception.ValidationFailedException;
import ru.lvov.SecondLabSpringBoot.model.Request;
import ru.lvov.SecondLabSpringBoot.model.Response;
import ru.lvov.SecondLabSpringBoot.service.ValidationService;

import java.text.SimpleDateFormat;



@RestController
public class MyController {
    private final ValidationService validationService;
    @Autowired
    public MyController(ValidationService validationService){
        this.validationService = validationService;
    }
    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid  @RequestBody Request request,
                                             BindingResult bindingResult){


        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();
        try {
            if ("123".equals(request.getUid())){
                throw new UnsupportedCodeException("Код 123 не поддерживается");
            }
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
