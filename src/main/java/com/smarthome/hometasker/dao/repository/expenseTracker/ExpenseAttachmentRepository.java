package com.smarthome.hometasker.dao.repository.expenseTracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smarthome.hometasker.dao.models.expenseTracker.ExpenseAttachment;

public interface ExpenseAttachmentRepository extends JpaRepository<ExpenseAttachment, String>{
    
    @Query("SELECT u from ExpenseAttachment u where u.id = :attachmentId")
    ExpenseAttachment findAttachmentById(String attachmentId);
}
