package com.smarthome.hometasker.dao.repository.expenseTracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smarthome.hometasker.dao.models.expenseTracker.Expense;
import com.smarthome.hometasker.dao.models.user.UserRegistration;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    @Query("SELECT e FROM Expense e WHERE e.user = :user")
    List<Expense> findByUserWithoutAttachments(UserRegistration user);

    @Query("SELECT e FROM Expense e")
    List<Expense> findAllWithoutAttachments();
}
