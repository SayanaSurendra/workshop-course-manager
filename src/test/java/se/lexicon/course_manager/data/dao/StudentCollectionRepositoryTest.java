package se.lexicon.course_manager.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.data.sequencers.StudentSequencer;
import se.lexicon.course_manager.model.Student;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {

    @Autowired
    private StudentDao testObject;

    private Student student;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    @BeforeEach
    void setUp() {
         student=testObject.createStudent("Erik Svennson","erik@gmail.com","Storgatan13 Stockholm");
    }

    @Test
    void createStudent(){
        Student newStudent=testObject.createStudent("Frederik Svennson","fedrrik@gmail.com","Storgatan33 Stockholm");
        assertNotNull(newStudent);
    }

    @Test
    void findById() {
        Student found = testObject.findById(student.getId());
        assertEquals(student, found);
    }

    @Test
    void testFindByEmailIgnoreCase() {
        Student found=testObject.findByEmailIgnoreCase(student.getEmail());
        assertEquals(student,found);
    }

    @Test
    void testFindByNameContains() {
        Collection<Student> found=testObject.findByNameContains(student.getName());
        assertTrue(found.contains(student));
    }

    @Test
    void testFindAll() {
         Student student1=testObject.createStudent("Fredrik","fedrik@gmail.com","Stockholm");
        Collection<Student> found=testObject.findAll();
        assertEquals(2,found.size());
    }

    @Test
    void testRemoveStudent() {
        Student student1=testObject.createStudent("Fredrik","fedrik@gmail.com","Stockholm");
        Collection<Student> found=testObject.findAll();
        assertTrue(testObject.removeStudent(student));
        assertFalse(found.contains(student));
    }

    @Test
    void clear() {
        testObject.clear();
        assertEquals(0,testObject.findAll().size());
    }


    @AfterEach
    void tearDown() {
        testObject.clear();
        StudentSequencer.setStudentSequencer(0);
    }


}
