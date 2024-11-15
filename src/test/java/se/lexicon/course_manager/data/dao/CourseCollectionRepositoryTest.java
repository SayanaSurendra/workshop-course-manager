package se.lexicon.course_manager.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.data.sequencers.CourseSequencer;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {

    @Autowired
    private CourseDao testObject;

    private Course javascriptCourse;

    private Course pythonCourse;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }


    @BeforeEach
    void setUp() {
        javascriptCourse = testObject.createCourse("Javascript", LocalDate.of(2024, Month.NOVEMBER, 25), 20);
        pythonCourse = testObject.createCourse("Python", LocalDate.of(2024, Month.DECEMBER, 11), 10);
    }

    @Test
    void createCourse() {
        Course course = testObject.createCourse("Java", LocalDate.of(2024, Month.DECEMBER, 1), 20);
        assertNotNull(course);
    }

    @Test
    void findById() {
        Course course = testObject.createCourse("Python", LocalDate.of(2024, Month.DECEMBER, 11), 10);
        Course found = testObject.findById(course.getId());
        assertEquals(course, found);
    }

    @Test
    void findByNameContains() {
        Collection<Course> foundCourses = testObject.findByNameContains(javascriptCourse.getCourseName());
        assertTrue(foundCourses.contains(javascriptCourse));
    }

    @Test
    void findByDateBefore() {
        Collection<Course> foundCourses = testObject.findByDateBefore(LocalDate.of(2025, Month.JANUARY, 1));
        assertEquals(2, foundCourses.size());
    }

    @Test
    void findByDateAfter() {
        Collection<Course> foundCourses = testObject.findByDateAfter(LocalDate.of(2025, Month.JANUARY, 1));
        assertEquals(0, foundCourses.size());
    }

    @Test
    void findAll() {
        Collection<Course> foundCourses = testObject.findAll();
        assertEquals(2, foundCourses.size());
        assertTrue(foundCourses.contains(javascriptCourse));
    }

    @Test
    void findByStudentId() {
        Student student = new Student(1, "Erik Svennson", "erik@gmail.com", "Storgatan13 Stockholm");
        pythonCourse.setStudents(new HashSet<>(Collections.singleton(student)));
        Collection<Course> courseList = testObject.findByStudentId(student.getId());
        assertTrue(courseList.contains(pythonCourse));
    }

    @Test
    void removeCourse() {
        Collection<Course> foundCourses = testObject.findAll();
        assertTrue(testObject.removeCourse(javascriptCourse));
        assertFalse(foundCourses.contains(javascriptCourse));
    }

    @Test
    void clear() {
        testObject.clear();
        assertEquals(0, testObject.findAll().size());
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
