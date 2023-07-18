package com.smarthome.hometasker.dao.repository.expenseTracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smarthome.hometasker.dao.models.expenseTracker.Expense;
import com.smarthome.hometasker.dao.models.user.UserRegistration;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // List<ExpenseProjection> findByUser(UserRegistration user);

    // @Query(value = "select e.item, e.description, i.name, i.type from expenses e join expense_attachments i on e.user_id = :userId and i.expense_id = e.id", nativeQuery = true)
    // @Query("SELECT e FROM Expense e JOIN e.attachments i on e.user = :user")
    // List<Expense> findByUser(UserRegistration user);

    @Query("SELECT e FROM Expense e WHERE e.user = :user")
    List<Expense> findByUserWithoutAttachments(UserRegistration user);

    @Query("SELECT e FROM Expense e")
    List<Expense> findAllWithoutAttachments();
}
