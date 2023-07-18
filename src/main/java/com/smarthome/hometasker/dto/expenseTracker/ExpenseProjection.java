package com.smarthome.hometasker.dto.expenseTracker;

import java.time.LocalDate;

public interface ExpenseProjection {
    
    String getItem();
    String getDescription();
    Long getCost();
    LocalDate getExpenseDate();
    Long getExpenseTimestamp();
    Integer getExpenseYear();
    String getAccount();
    String getType();
    String getName();
}
