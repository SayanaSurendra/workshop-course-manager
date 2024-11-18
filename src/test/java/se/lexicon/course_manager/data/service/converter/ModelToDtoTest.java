package se.lexicon.course_manager.data.service.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.dto.views.CourseView;
import se.lexicon.course_manager.dto.views.StudentView;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ModelToDto.class})
public class ModelToDtoTest {

    @Autowired
    private Converters testObject;

    private Student student;
    private Course course;
    private Collection<Student> students;
    private Collection<Course> courses;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
    }


    @BeforeEach
    void setUp() {
        student=new Student(1,"Erik","erik@gmail.com","Stockholm");
        course=new Course(1,"Java", LocalDate.of(2024, Month.DECEMBER,10),10);
        course.enrollStudent(student);
        students=new ArrayList<>();
        courses=new ArrayList<>();
        courses.add(course);

    }

    @Test
    void studentToStudentView() {
        StudentView expectedStudentView =new StudentView(student.getId(), student.getName(),student.getEmail(),student.getAddress());
        assertEquals(expectedStudentView,testObject.studentToStudentView(student));

    }

    @Test
    void courseToCourseView() {
        StudentView expectedStudentView =new StudentView(student.getId(), student.getName(),student.getEmail(),student.getAddress());
        List<StudentView> studentViewList=new ArrayList<>();
        studentViewList.add(expectedStudentView);
        CourseView expectedCourseView=new CourseView(course.getId(),course.getCourseName(),course.getStartDate(),course.getWeekDuration(),studentViewList);
        assertEquals(expectedCourseView,testObject.courseToCourseView(course));
    }
    @Test
    void coursesToCourseViews() {
        StudentView expectedStudentView =new StudentView(student.getId(), student.getName(),student.getEmail(),student.getAddress());
        List<StudentView> studentViewList=new ArrayList<>();
        studentViewList.add(expectedStudentView);
        List<CourseView> expectedCourseViewList= new ArrayList<>();
        CourseView expectedCourseView=new CourseView(course.getId(),course.getCourseName(),course.getStartDate(),course.getWeekDuration(),studentViewList);
        expectedCourseViewList.add(expectedCourseView);
        System.out.println(course);
        assertEquals(1,testObject.coursesToCourseViews(courses).size());
    }

    @Test
    void studentsToStudentViews() {
        Student student1=new Student(2,"Frederik Svennson","fedrrik@gmail.com","Storgatan33 Stockholm");
        StudentView expectedStudentView =new StudentView(student.getId(), student.getName(),student.getEmail(),student.getAddress());
        StudentView expectedStudentView1=new StudentView(student1.getId(), student1.getName(),student1.getEmail(),student1.getAddress());
        students.add(student1);
        students.add(student);
        List<StudentView> expectedStudentViewList= new ArrayList<>();
        expectedStudentViewList.add(expectedStudentView);
        expectedStudentViewList.add(expectedStudentView1);
        assertEquals(2,testObject.studentsToStudentViews(students).size());
    }

}
