package com.tk.tliaswebmanagment.Service;

import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Student;
import com.tk.tliaswebmanagment.pojo.StudentQuerryParam;

public interface StudentService {

    PageResult getStudentsPage(StudentQuerryParam studentQuerryParam);

    void insertStudent(Student student);

    Student getStudentById(int id);

    void updateStudent(Student student);
}
