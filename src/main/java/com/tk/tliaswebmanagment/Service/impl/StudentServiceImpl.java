package com.tk.tliaswebmanagment.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tk.tliaswebmanagment.Mapper.StudentMapper;
import com.tk.tliaswebmanagment.Service.StudentService;
import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Student;
import com.tk.tliaswebmanagment.pojo.StudentQuerryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult getStudentsPage(StudentQuerryParam studentQuerryParam) {
        PageHelper.startPage(studentQuerryParam.getPage(), studentQuerryParam.getPageSize());
        List<Student> studentList = studentMapper.selectPage(studentQuerryParam);
        Page<Student> p=(Page<Student>) studentList;
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void insertStudent(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        student.setViolationCount((short) 0);
        student.setViolationScore((short) 0);
        studentMapper.insert(student);
    }

    @Override
    public Student getStudentById(int id) {
        return studentMapper.selectById(id);
    }

    @Override
    public void updateStudent(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateById(student);
    }

    @Override
    public void deleteStudents(List<Integer> ids) {
        studentMapper.deleteBatchIds(ids);
    }
}
