package se.lexicon.course_manager.data.dao;

import se.lexicon.course_manager.model.Student;

import java.util.Collection;

public interface StudentDao {

    Student createStudent(String name, String email, String address);
    Student findByEmailIgnoreCase(String email);
    Collection<Student> findByNameContains(String name);
    Student findById(int id);
    Collection<Student> findAll();
    boolean removeStudent(Student student);
    void clear();

}
