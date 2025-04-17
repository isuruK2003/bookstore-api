package com.example.dao;

import com.example.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private static final List<Teacher> teachers = new ArrayList<Teacher>();

    static {
        teachers.add(new Teacher(1, "Mr. Smith"));
        teachers.add(new Teacher(2, "Mr. Jones"));
    }

    public List<Teacher> getAllTeachers() {
        return teachers;
    }

    public Teacher getTeacherById(int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void updateTeacher(Teacher teacher) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId() == teacher.getId()) {
                teachers.set(i, teacher);
            }
        }
    }

    public void deleteTeacher(int id) {
        teachers.removeIf(teacher -> teacher.getId() == id);
    }

    public int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;
        for (Teacher teacher : teachers) {
            if (maxUserId < teacher.getId()) {
                maxUserId = teacher.getId();
            }
        }
        return maxUserId + 1;
    }
}
