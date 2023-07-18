package com.smarthome.hometasker.dto.expenseTracker;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ExpenseDTO {
    
    private String item;
    private String description;
    private Long categoryId;
    private String userId;
    private Long cost;
    private List<AttachmentDTO> attachments = new ArrayList<>();
    
 }
    
