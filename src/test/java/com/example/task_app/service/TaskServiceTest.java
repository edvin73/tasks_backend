package com.example.task_app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.task_app.entity.Task;
import com.example.task_app.error.TaskNotFoundException;
import com.example.task_app.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task1.setTaskId(1L);
        task1.setTitle("Title One");
        task1.setAccomplished("0");
                
        task2 = new Task();
        task2.setTaskId(2L);
        task2.setTitle("Title Two");
        task2.setAccomplished("1");
    }

    @Test
    void getAllTasks_returnsList() {
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertThat(tasks).hasSize(2).containsExactly(task1, task2);
    }

    @Test
    void getTaskById_found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Task result = taskService.getTaskById(1L);

        assertThat(result).isEqualTo(task1);
    }

    @Test
    void getTaskById_notFound_throws() {
        when(taskRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(42L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void createTask_savesAndReturns() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task result = taskService.createTask(new Task(null, "Title One", "0", 0));

        assertThat(result).isEqualTo(task1);
    }

    @Test
    void updateTask_noChanges_doesNotSave() {
        Task incoming = new Task(1L, "Title One", "0", 0);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Task result = taskService.updateTask(1L, incoming);

        assertThat(result).isEqualTo(task1);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_withChanges_saves() {
        Task incoming = new Task(1L, "New Title", "0", 0);
        Task saved = new Task(1L, "New Title", "0", 0);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(saved);

        Task result = taskService.updateTask(1L, incoming);

        assertThat(result.getTitle()).isEqualTo("New Title");
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void deleteTask_callsRepositoryDelete() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        taskService.deleteTask(1L);

        verify(taskRepository).deleteById(1L);
    }
}
