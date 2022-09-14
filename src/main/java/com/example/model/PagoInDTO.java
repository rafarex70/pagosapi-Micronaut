package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoInDTO {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "56.08")
    private BigDecimal importe;

    @Size(max = 255)
    private String concepto;

    @NotNull
    private Long idPersona;

}
