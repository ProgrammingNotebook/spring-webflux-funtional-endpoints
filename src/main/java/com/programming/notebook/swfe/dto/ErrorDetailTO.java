package com.programming.notebook.swfe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class ErrorDetailTO {
    
    private String field;
    private String message;
}
