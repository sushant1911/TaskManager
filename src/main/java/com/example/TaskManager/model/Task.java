package com.example.TaskManager.model;

import com.example.TaskManager.Enum.Priority;
import com.example.TaskManager.Enum.Status;
import com.example.TaskManager.anotation.DateValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "tasks")
public class Task  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @NotNull(message = "title name cannot be null")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull(message = "deadline name cannot be null")
    @Column(nullable = false)
//    @JsonFormat(pattern = "MMM d, yyyy hh:mm:ss a")
    @DateValidation
    private Date deadline;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull(message = "createdAt name cannot be null")
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
