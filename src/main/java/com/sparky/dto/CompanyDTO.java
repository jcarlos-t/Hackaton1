package com.sparky.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class CompanyDTO {
    private String name;
    private String ruc;
    private LocalDate affiliationDate;
    private boolean active;
    private Long adminId;
}
