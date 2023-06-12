package com.nerzur.demos.contacts.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * This class contains the necessary data to display the thrown exceptions in a response.
 * @author Gabriel
 * @version 1.0.0
 */
@Data
@Builder
public class ErrorMessage {

    private String code;
    private List<Map<String, String>> messages;
}