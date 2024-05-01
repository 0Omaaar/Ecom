package com.example.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AnalyticsResponse {

    private Long placed;
    private Long delivered;
    private Long shipped;
    private Long currentMonthOrders;
    private Long previousMonthOrders;
    private Long currentMonthEarnings;
    private Long previousMonthEarnings;

}
