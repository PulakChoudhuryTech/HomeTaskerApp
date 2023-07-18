package com.smarthome.hometasker.dao.repository.expenseTracker;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarthome.hometasker.dao.models.expenseTracker.Category;
import com.smarthome.hometasker.dao.models.expenseTracker.Expense;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    List<Expense> findExpensesById(Long id);
}
