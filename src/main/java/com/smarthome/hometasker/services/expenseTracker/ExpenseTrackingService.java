package com.smarthome.hometasker.services.expenseTracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smarthome.hometasker.dao.models.expenseTracker.Category;
import com.smarthome.hometasker.dao.models.expenseTracker.Expense;
import com.smarthome.hometasker.dao.models.expenseTracker.ExpenseAttachment;
import com.smarthome.hometasker.dao.models.user.UserRegistration;
import com.smarthome.hometasker.dao.repository.expenseTracker.CategoryRepository;
import com.smarthome.hometasker.dao.repository.expenseTracker.ExpenseAttachmentRepository;
import com.smarthome.hometasker.dao.repository.expenseTracker.ExpenseRepository;
import com.smarthome.hometasker.dao.repository.user.UserRegistrationRepository;
import com.smarthome.hometasker.dto.expenseTracker.CategoryDTO;
import com.smarthome.hometasker.dto.expenseTracker.ExpenseDTO;
import com.smarthome.hometasker.mapper.expenseTracker.CategoryMapper;
import com.smarthome.hometasker.mapper.expenseTracker.ExpenseMapper;

import jakarta.transaction.Transactional;

@Service
public class ExpenseTrackingService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    ExpenseAttachmentRepository expenseAttachmentRepository;

    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    
    public void createCategory(CategoryDTO category) {
        Category categoryEntity = CategoryMapper.mapDtoToEntity(category);
        this.categoryRepository.save(categoryEntity);
    }


    @Transactional
    public List<ExpenseDTO> getAllExpenses(String userId) {
        UserRegistration user = this.userRegistrationRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));;
        List<Expense> expenseEntities = this.expenseRepository.findByUserWithoutAttachments(user);
        List<ExpenseDTO> expenses = ExpenseMapper.mapEntitiesToDTOs(expenseEntities);
        return expenses;
    }

    public void saveExpenses(ExpenseDTO expenseData, List<MultipartFile> attachments) throws Exception {
        Expense expenseEntity = ExpenseMapper.mapDtoToEntity(expenseData, attachments);

            Category categoryEntity = this.categoryRepository.findById(expenseData.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Invalid Category Id"));
            UserRegistration userEntity = this.userRegistrationRepository.findById(expenseData.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid User Id"));
            
            expenseEntity.setCategory(categoryEntity);
            expenseEntity.setUser(userEntity);
            this.expenseRepository.save(expenseEntity);

    }

    public List<ExpenseDTO> getExpensesByCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Invalid Category Id"));
        List<ExpenseDTO> expenses = ExpenseMapper.mapEntitiesToDTOs(category.getExpenses());
        return expenses;
    }

    public ExpenseAttachment getExpenseAttachmentById(String attachmentId) {
        ExpenseAttachment attachment = this.expenseAttachmentRepository.findById(attachmentId).orElseThrow(() -> new IllegalArgumentException("Invalid attachment Id"));
        return attachment;
    }

}
