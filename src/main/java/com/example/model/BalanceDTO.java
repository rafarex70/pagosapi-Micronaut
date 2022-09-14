package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Getter
@Setter
public class BalanceDTO {

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "56.08")
    private BigDecimal importe;

    @NotNull
    private String name;

    public BalanceDTO(BigDecimal importe, String name) {
        this.importe = importe;
        this.name = name;
    }
}
