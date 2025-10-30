package com.tk.tliaswebmanagment.Mapper;
import com.tk.tliaswebmanagment.pojo.Student;
import com.tk.tliaswebmanagment.pojo.StudentQuerryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    List<Student> selectPage(StudentQuerryParam studentQuerryParam);

    @Insert("insert into student" +
            "(name, no, gender, phone, degree, clazz_id,id_card, is_college, address, graduation_date, violation_count, violation_score, create_time, update_time) " +
            "values" +
            "(#{name}, #{no}, #{gender}, #{phone}, #{degree}, #{clazzId}, #{idCard}, #{isCollege}, #{address}, #{graduationDate}, #{violationCount}, #{violationScore}, #{createTime}, #{updateTime})")
    void insert(Student student);

    @Select("select * from student where id=#{id}")
    Student selectById(int id);

    void updateById(Student student);

    void deleteBatchIds(List<Integer> ids);

    void updateViolationScore(int id, int score);

    @MapKey("clazz_name")
    List<Map<String, Object>> getStudentCountData();

    @MapKey("name")
    List<Map<String, Object>> getStudentDegreeData();
}
