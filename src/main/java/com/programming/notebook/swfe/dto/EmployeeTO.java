package com.programming.notebook.swfe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EmployeeTO {

    @NotBlank(message = "Age is required")
    @Pattern(regexp = "^[0-9]{1,2}$", message = "Age must be a number between 1 and 2 digits")
    private String age;

    @NotBlank(message = "Employee's full name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Employee's full name must contain only letters and spaces")
    @Pattern(regexp = "^[a-zA-Z\\s]{1,50}$", message = "Employee's full name must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]{1,50}$", message = "Employee's full name must start with a capital letter")
    private String fullName;
}
