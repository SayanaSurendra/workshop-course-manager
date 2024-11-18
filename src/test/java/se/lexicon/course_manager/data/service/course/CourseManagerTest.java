package se.lexicon.course_manager.data.service.course;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager.data.dao.CourseDao;
import se.lexicon.course_manager.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager.data.dao.StudentDao;
import se.lexicon.course_manager.data.sequencers.CourseSequencer;
import se.lexicon.course_manager.data.service.converter.Converters;
import se.lexicon.course_manager.data.service.converter.ModelToDto;
import se.lexicon.course_manager.dto.views.CourseView;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CourseManager.class, CourseCollectionRepository.class, ModelToDto.class, StudentCollectionRepository.class})
public class CourseManagerTest {

    @Autowired
    private CourseService testObject;

    @Autowired
    private CourseDao courseDao;


    @Autowired
    private Converters converters;

    private Course course;

    private  Student student;

    @Autowired
    private StudentDao studentDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(courseDao);
    }

    @BeforeEach
    void setUp() {
     course=courseDao.createCourse("java", LocalDate.of(2024,12,8),15);
     //student=new Student(1,"Erik Svennson","erik@gmail.com","Storgatan13 Stockholm");
     student=studentDao.createStudent("Erik Svennson","erik@gmail.com","Storgatan13 Stockholm");

    }


    @Test
    void create() {

    }

    @Test
    void update() {
    }

    @Test
    void searchByCourseName() {
        Collection<Course> expectedCourses=courseDao.findByNameContains(course.getCourseName());
        List<CourseView> courseViewExpected=converters.coursesToCourseViews(expectedCourses);
        List<CourseView> courseViewActual =testObject.searchByCourseName(course.getCourseName());
        assertEquals(courseViewExpected,courseViewActual);
    }

    @Test
    void searchByDateBefore() {
        Collection<Course> expectedCourses=courseDao.findByDateBefore(LocalDate.now());
        List<CourseView> courseViewExpected=converters.coursesToCourseViews(expectedCourses);
        List<CourseView> courseViewActual=testObject.searchByDateBefore(LocalDate.now());
        assertEquals(courseViewExpected,courseViewActual);

    }

    @Test
    void searchByDateAfter() {
        Collection<Course> expectedCourses=courseDao.findByDateAfter(LocalDate.now());
        List<CourseView> courseViewExpected=converters.coursesToCourseViews(expectedCourses);
        List<CourseView> courseViewActual=testObject.searchByDateAfter(LocalDate.now());
        assertEquals(courseViewExpected,courseViewActual);
    }

    @Test
    void addStudentToCourse() {
        boolean added=testObject.addStudentToCourse(course.getId(), student.getId());
        assertTrue(added);

    }

    @Test
    void removeStudentFromCourse() {
        course.enrollStudent(student);
        assertTrue(testObject.removeStudentFromCourse(course.getId(),student.getId()));

    }

    @Test
    void findById() {
        Course course=courseDao.findById(1);
        CourseView expectedCourseView=converters.courseToCourseView(course);
        CourseView actualCourseView=testObject.findById(course.getId());
        assertEquals(expectedCourseView,actualCourseView);
    }

    @Test
    void findAll() {
        courseDao.createCourse("Javascript",LocalDate.now(),20);
        Collection<Course> courses= courseDao.findAll();
        List<CourseView> expected=converters.coursesToCourseViews(courses);
        List<CourseView> actual= testObject.findAll();
        assertEquals(expected,actual);
    }

    @Test
    void findByStudentId() {
        course.enrollStudent(student);
       Course javascriptCourse= courseDao.createCourse("Javascript",LocalDate.now(),20);
        course.enrollStudent(student);
        javascriptCourse.enrollStudent(student);
        Collection<Course> courses=courseDao.findByStudentId(student.getId());
        List<CourseView> expected=converters.coursesToCourseViews(courses);
        List<CourseView> actual=testObject.findByStudentId(student.getId());
    }

    @Test
    void deleteCourse() {
       assertTrue(testObject.deleteCourse(course.getId()));
    }


    @AfterEach
    void tearDown() {
        courseDao.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
