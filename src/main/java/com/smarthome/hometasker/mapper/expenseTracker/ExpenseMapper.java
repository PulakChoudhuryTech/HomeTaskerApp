package com.smarthome.hometasker.mapper.expenseTracker;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.smarthome.hometasker.dao.models.expenseTracker.Expense;
import com.smarthome.hometasker.dao.models.expenseTracker.ExpenseAttachment;
import com.smarthome.hometasker.dto.expenseTracker.AttachmentDTO;
import com.smarthome.hometasker.dto.expenseTracker.ExpenseDTO;
import com.smarthome.hometasker.utils.ImageUtils;

public class ExpenseMapper {
    
    public static Expense mapDtoToEntity(ExpenseDTO dto, List<MultipartFile> files) throws IOException {
        Expense entity = new Expense();
        LocalDate localDate = LocalDate.now();

        entity.setItem(dto.getItem());
        entity.setDescription(dto.getDescription());
        entity.setCost(dto.getCost());
        entity.setExpenseDate(localDate);
        entity.setExpenseTimestamp(Instant.now().toEpochMilli());
        entity.setExpenseYear(localDate.getYear());
        // entity.setAttachments(ExpenseAttachment.builder()
        // .name(attachments.getOriginalFilename())
        // .type(attachments.getContentType())
        // .data(ImageUtils.compressImage(attachments.getBytes())).build());

        // List<String> attachements = dto.getAttachments();
        List<ExpenseAttachment> expenseAttachments = new ArrayList<>();
        if (!CollectionUtils.isEmpty(files)) {
            files.forEach(item -> {
                // ExpenseAttachment attachment = new ExpenseAttachment();
                // byte[] imageArr = item.getBytes(); 
                // // String imageAsString= Base64.encodeBase64String(imageArr);
                // attachment.setData(imageArr);
                ExpenseAttachment attachment = new ExpenseAttachment();
                try {
                    attachment.setName(item.getOriginalFilename());
                    attachment.setType(item.getContentType());
                    attachment.setData(ImageUtils.compressImage(item.getBytes()));
                    attachment.setExpense(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                expenseAttachments.add(attachment);
            });
            entity.setAttachments(expenseAttachments);
        }
        return entity;
    }

    public static List<ExpenseDTO> mapEntitiesToDTOs(List<Expense> entities) {
        List<ExpenseDTO> dtos = new ArrayList<>();
        for (Expense entity : entities) {
            ExpenseDTO dto = new ExpenseDTO();
            dto.setCost(entity.getCost());
            dto.setDescription(entity.getDescription());
            dto.setItem(entity.getItem());
            dto.setUserId(entity.getUser().getUserId());
            dto.setCategoryId(entity.getCategory().getId());
            List<ExpenseAttachment> attachments = entity.getAttachments();
            List<AttachmentDTO> attachmentsDTOList = new ArrayList<>();
            attachments.forEach((item) -> {
                AttachmentDTO attachmentDto = new AttachmentDTO();
                attachmentDto.setType(item.getType());
                attachmentDto.setUrl("/api/tracker/expense/attachment/" + item.getId());
                attachmentsDTOList.add(attachmentDto);
            });
            dto.setAttachments(attachmentsDTOList);
            dtos.add(dto);
        }
        return dtos;
    }
    
}
