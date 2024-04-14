package com.example.TaskManager.DTO;

import com.example.TaskManager.Enum.Priority;
import com.example.TaskManager.Enum.Status;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Date deadline;
    private Priority priority;
    private Status status;
    private Date createdAt;
    private Date updatedAt;
    private String email;



}

