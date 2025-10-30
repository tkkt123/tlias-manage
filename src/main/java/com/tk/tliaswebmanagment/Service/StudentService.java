package com.tk.tliaswebmanagment.Service;

import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Student;
import com.tk.tliaswebmanagment.pojo.StudentOption;
import com.tk.tliaswebmanagment.pojo.StudentQuerryParam;

import java.util.List;
import java.util.Map;

public interface StudentService {

    PageResult getStudentsPage(StudentQuerryParam studentQuerryParam);

    void insertStudent(Student student);

    Student getStudentById(int id);

    void updateStudent(Student student);

    void deleteStudents(List<Integer> ids);

    void updateViolationScore(int id, int score);

    StudentOption getStudentCountData();

    List<Map<String, Object>> getStudentDegreeData();
}
