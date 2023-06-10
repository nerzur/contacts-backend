package com.nerzur.demos.contacts.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class contains the functions to handle exceptions during request execution. To do this, it is necessary to
 * previously create an object of type {@link BindingResult} which contains the details of the exception thrown and then
 * a String is obtained with the formatted results. <br/>
 * Different exceptions can be caught that are cataloged in two fundamental groups organized by the code: </br>
 * <ul>
 * <li><strong>Code 1:</strong> Exceptions thrown at the entity level. These exceptions are caught at the time of parsing
 * the data provided in the request.</li>
 * <li><strong>Code 2:</strong> Exceptions thrown at execution level. These exceptions are thrown at the time of searching the database and executing business logic.</li>
 * </ul>
 * @author Gabriel
 * @version 1.0.0
 */
public class ExceptionsBuilder {

    /**
     * This function allows you to format an exception message from a {@link BindingResult}. A {@link String} is
     * obtained with the formatted text. This function is used to format type 1 exceptions.
     * @param result {@link BindingResult} containing the details of the exception to be thrown.
     * @return Returns a {@link String} with the details of the exception thrown.
     */
    public static String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream().map(fieldError -> {
            Map<String, String> error = new HashMap<>();
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
            return error;
        }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try{
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     * This function allows you to format an exception message from a {@link BindingResult}. A {@link String} is
     * obtained with the formatted text. This function is used to format type 2 exceptions.
     * @param result {@link BindingResult} containing the details of the exception to be thrown.
     * @return Returns a {@link String} with the details of the exception thrown.
     */
    public static String formatCustomMessage(BindingResult result){
        List<ObjectError> errors = result.getAllErrors();
        List<Map<String, String>> errorsList = new ArrayList<>();
        errors.forEach(objectError -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(objectError.getObjectName(), objectError.getDefaultMessage());
            errorsList.add(hashMap);
        });
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("02")
                .messages(errorsList)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try{
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     *
     * @param result {@link BindingResult} object used to store the exception.
     * @param className Name of the class that throws the exception.
     * @param exceptionMessage Exception message to be thrown.
     */
    public static void launchException(BindingResult result, String className, String exceptionMessage) {
        result.addError(new ObjectError(className, exceptionMessage));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatCustomMessage(result));
    }
}
