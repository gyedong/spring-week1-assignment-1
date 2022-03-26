package com.codesoom.assignment;

import com.codesoom.assignment.models.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class TaskByIdHandler extends TaskHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        System.out.println(method);
        String path = exchange.getRequestURI().getPath();
        String[] paths = path.split("/");
        int taskId = Integer.parseInt(paths[paths.length - 1]);
        System.out.println("taskId: " + taskId);
        if(Objects.equals(method, "GET")){
            handleGetMethod(exchange, taskId);
        }else if(Objects.equals(method, "PUT") || Objects.equals(method, "PATCH")){
            handlePutOrPatchMethod(exchange, taskId);
        }else if(Objects.equals(method, "DELETE")){
            handleDeleteMethod(exchange, taskId);
        }
    }

    private void handleGetMethod(HttpExchange exchange, int taskId) throws IOException {
        Task task = null;
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).getId() == taskId){
                task = tasks.get(i);
            }
        }
        if(task == null){
            String content = "Task is not found.";
            exchange.sendResponseHeaders(404, content.getBytes().length);
            outputResponse(exchange, content);
        }else{
            TaskSerializer taskSerializer = new TaskSerializer();
            String content = taskSerializer.taskToJson(task);
            exchange.sendResponseHeaders(200, content.getBytes().length);
            outputResponse(exchange, content);
        }
    }

    private void handlePutOrPatchMethod(HttpExchange exchange, int taskId) throws IOException {
        String content = "Task: PUT or PATCH";
        exchange.sendResponseHeaders(200, content.getBytes().length);
        outputResponse(exchange, content);
    }

    private void handleDeleteMethod(HttpExchange exchange, int taskId) throws IOException {
        String content = "Task: DELETE";
        exchange.sendResponseHeaders(200, content.getBytes().length);
        outputResponse(exchange, content);
    }
}
