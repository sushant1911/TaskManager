package com.example.TaskManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskManagerResponse {
    private int statusCode;
    private String message;
    private Object data;
}
