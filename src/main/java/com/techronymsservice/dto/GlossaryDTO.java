package com.techronymsservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlossaryDTO {

    @NotNull
    @NotBlank(message = "This field is required")
    @Size
    private String title;

    @NotNull
    @NotBlank(message = "This field is required")
    private String definition;

    private String furtherReading;

}

