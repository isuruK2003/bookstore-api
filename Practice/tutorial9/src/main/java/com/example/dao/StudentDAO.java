package com.example.dao;

import com.example.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final List<Student> students = new ArrayList<Student>();

    static {
        students.add(new Student(1, "Alan Walker"));
        students.add(new Student(2, "Bob Builder"));
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student getStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void updateStudent(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
            }
        }
    }

    public void deleteStudent(int id) {
        students.removeIf(student -> student.getId() == id);
    }

    public int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;
        for (Student student : students) {
            if (maxUserId < student.getId()) {
                maxUserId = student.getId();
            }
        }
        return maxUserId + 1;
    }
}
