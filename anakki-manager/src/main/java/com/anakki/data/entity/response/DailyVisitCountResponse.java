package com.anakki.data.entity.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyVisitCountResponse {

    private LocalDate date;
    private Long visitCount;

}
