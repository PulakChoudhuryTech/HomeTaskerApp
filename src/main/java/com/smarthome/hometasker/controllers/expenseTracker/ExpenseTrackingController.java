package com.smarthome.hometasker.controllers.expenseTracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smarthome.hometasker.dao.models.expenseTracker.Expense;
import com.smarthome.hometasker.dao.models.expenseTracker.ExpenseAttachment;
import com.smarthome.hometasker.dto.common.CommonResponseDTO;
import com.smarthome.hometasker.dto.expenseTracker.CategoryDTO;
import com.smarthome.hometasker.dto.expenseTracker.ExpenseDTO;
import com.smarthome.hometasker.dto.expenseTracker.ExpenseProjection;
import com.smarthome.hometasker.services.expenseTracker.ExpenseTrackingService;
import com.smarthome.hometasker.utils.ImageUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tracker")
public class ExpenseTrackingController {
    
    ExpenseTrackingService expenseTrackingService;
    
    @Autowired
    public ExpenseTrackingController(ExpenseTrackingService expenseTrackingService) {
        this.expenseTrackingService = expenseTrackingService;
    }


    @RequestMapping(value = "/expense/add", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<CommonResponseDTO> saveExpense(HttpServletRequest request, @RequestPart("data")ExpenseDTO expenseData, @RequestPart("attachments")List<MultipartFile> attachments) throws Exception {
        this.expenseTrackingService.saveExpenses(expenseData, attachments);
        CommonResponseDTO response = new CommonResponseDTO();
        response.setMessage("SUCCESS");
        return new ResponseEntity<CommonResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/expense/{userId}/all")
    public List<ExpenseDTO> getAllExpenses(HttpServletRequest request, @PathVariable String userId) {
        List<ExpenseDTO> expenses = this.expenseTrackingService.getAllExpenses(userId);
        return expenses;
    }

    @PostMapping(value = "/category/add")
    public ResponseEntity<CommonResponseDTO> createCategory(HttpServletRequest request, @RequestBody CategoryDTO categoryData) {
        this.expenseTrackingService.createCategory(categoryData);
        CommonResponseDTO response = new CommonResponseDTO();
        response.setMessage("SUCCESS");
        return new ResponseEntity<CommonResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/categories/{categoryId}/expenses")
    public List<ExpenseDTO> getExpensesByCategory(HttpServletRequest request, @PathVariable Long categoryId) {
        List<ExpenseDTO> expenses = this.expenseTrackingService.getExpensesByCategory(categoryId);
        return expenses;
    }

    @GetMapping(value = "/expense/attachment/{attachmentId}")
    public ResponseEntity<?> getExpenseAttachmentById(HttpServletRequest request, @PathVariable String attachmentId) {
        ExpenseAttachment attachement = this.expenseTrackingService.getExpenseAttachmentById(attachmentId);
        byte[] images = ImageUtils.decompressImage(attachement.getData());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(attachement.getType())).body(images);
    }


    // @GetMapping(value = "/expense/attachment/{attachmentId}")
    // public ResponseEntity<?> getExpenseAttachmentDataById(HttpServletRequest request, @PathVariable String attachmentId) {
    //     byte[] images = ImageUtils.decompressImage(this.expenseTrackingService.getExpenseAttachmentDataById(attachmentId));
    //     return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpeg")).body(images);
    // }
}
