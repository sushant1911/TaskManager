package com.example.TaskManager.DTO;

import com.example.TaskManager.Enum.Priority;
import com.example.TaskManager.Enum.Status;
import lombok.Getter;

import java.util.Date;

@Getter
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Date deadline;
    private Priority priority;
    private Status status;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

