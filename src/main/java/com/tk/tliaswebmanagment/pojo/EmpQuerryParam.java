package com.tk.tliaswebmanagment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpQuerryParam {
    private Integer page;
    private Integer pageSize;
    private String name;
    private Integer gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryEndDate;
}
