package se.lexicon.course_manager.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private Student student;

    @BeforeEach
    void setUp() {
      student=student=new Student(1,"Erik","erik@gmail.com","Stockholm");
    }

    @Test
    void testStudentCreate(){
        assertNotNull(student);
    }

    @Test
    void testSetandGetName(){
        student.setName("Steve S");
        assertEquals("Steve S",student.getName());

    }

    @Test
    void testSetandGetEmail(){
     student.setEmail("steve@gmail.com");
     assertEquals("steve@gmail.com",student.getEmail());
    }

    @Test
    void testSetandGetAddress(){
       student.setAddress("Street 21");
       assertEquals("Street 21",student.getAddress());
    }
}
