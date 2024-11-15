package se.lexicon.course_manager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class CourseTest {

    private Course course;
    private Collection<Student> students;
    private Student student;

    @BeforeEach
    void setUp() {
       student=new Student(1,"Erik","erik@gmail.com","Stockholm");
      students=new HashSet<>();
      students.add(student);
      students.add(new Student(2,"Fredrik","fedrik@gmail.com","Stockholm"));
      course=new Course(1,"Java", LocalDate.of(2024, Month.DECEMBER,10),10);
    }


    @Test
    void testCreateCourse(){
        assertNotNull(course);
    }


    @Test
    void testSetandGetCourseName() {
        course.setCourseName("Python");
        assertEquals("Python",course.getCourseName());
    }

    @Test
    void testSetandGetStudents() {
        Student student1=new Student(1,"Steve Svensson","steve@gmail.com","Street2");
        course.setStudents(new HashSet<>(Collections.singletonList(student1)));
        assertEquals(1,course.getStudents().size());
        assertTrue(course.getStudents().contains(student1));

    }

    @Test
    void testSetandGetWeekDuration() {
        course.setWeekDuration(20);
        assertEquals(20,course.getWeekDuration());
    }

    @Test
    void testSetandGetStartDate() {
        LocalDate expectedDate=LocalDate.of(2024,Month.DECEMBER,12);
        course.setStartDate(expectedDate);
        assertEquals(expectedDate,course.getStartDate());
    }


    @Test
    void enrollStudent() {
        course.enrollStudent(student);
        assertTrue(course.getStudents().contains(student));
    }

    @Test
    void unrollStudent() {
        course.setStudents(students);
        //course.setStudents(new HashSet<>(Collections.singletonList(student)));
        assertTrue(course.unenrollStudent(student));
        assertFalse(course.getStudents().contains(student));

    }

}
