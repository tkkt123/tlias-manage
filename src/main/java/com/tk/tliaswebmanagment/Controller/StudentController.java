package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.ClazzService;
import com.tk.tliaswebmanagment.Service.StudentService;
import com.tk.tliaswebmanagment.anno.Log;
import com.tk.tliaswebmanagment.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public Result getStudentsPage(StudentQuerryParam studentQuerryParam) {
        PageResult pageResult = studentService.getStudentsPage(studentQuerryParam);
        return Result.success(pageResult);
    }

    @Log
    @PostMapping("/students")
    public Result insertStudent(@RequestBody Student student) {
        System.out.println(student);
        studentService.insertStudent(student);
        return Result.success();
    }

    @GetMapping("/students/{id}")
    public Result getStudentById(@PathVariable int id) {
        Student student = studentService.getStudentById(id);
        return Result.success(student);
    }

    @Log
    @PutMapping("/students")
    public Result updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
        return Result.success();
    }

    @Log
    @DeleteMapping("/students/{ids}")
    public Result deleteStudent(@PathVariable String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        studentService.deleteStudents(idList);
        return Result.success();
    }

    @PutMapping("/students/violation/{id}/{score}")
    public Result updateViolationScore(@PathVariable int id, @PathVariable int score) {
        studentService.updateViolationScore(id, score);
        return Result.success();
    }
}
