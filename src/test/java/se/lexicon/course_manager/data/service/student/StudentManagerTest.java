package se.lexicon.course_manager.data.service.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager.data.dao.StudentDao;
import se.lexicon.course_manager.data.sequencers.StudentSequencer;
import se.lexicon.course_manager.data.service.converter.Converters;
import se.lexicon.course_manager.data.service.converter.ModelToDto;
import se.lexicon.course_manager.dto.forms.CreateStudentForm;
import se.lexicon.course_manager.dto.views.StudentView;
import se.lexicon.course_manager.model.Student;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentManager.class, CourseCollectionRepository.class, StudentCollectionRepository.class, ModelToDto.class})
public class StudentManagerTest {

    @Autowired
    private StudentService testObject;
    @Autowired
    private StudentDao studentDao;


    @Autowired
    private Converters converters;

    private Student student;

    private StudentView expectedStudentView;


    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(studentDao);
    }

    @BeforeEach
    void setUp() {
        student=studentDao.createStudent("Erik Svennson","erik@gmail.com","Storgatan13 Stockholm");
        expectedStudentView=new StudentView(1,"Erik Svennson","erik@gmail.com","Storgatan13 Stockholm");

    }

    @Test
    void create() {
        //CreateStudentForm form=new CreateStudentForm(1,"Erik Svennson","erik@gmail.com","Storgatan13 Stockholm");
       /* Student student=studentDao.createStudent(form.getName(), form.getEmail(), form.getAddress());
        System.out.println(student.getId());
        StudentView expectedStudentView=new StudentView(1,"Erik Svennson","erik@gmail.com","Storgatan13 Stockholm");
        StudentView studentViewCreated=converters.studentToStudentView(student);
        StudentView studentView=testObject.create(form);
        assertEquals(expectedStudentView,studentView);*/
    }

    @Test
    void update() {


    }

    @Test
    void findById() {
        assertEquals(expectedStudentView,testObject.findById(student.getId()));

    }

    @Test
    void searchByEmail() {

        assertEquals(expectedStudentView,testObject.searchByEmail(student.getEmail()));
    }

    @Test
    void searchByName() {
        Collection<Student> students = studentDao.findByNameContains(student.getName());
        assertEquals(converters.studentsToStudentViews(students),testObject.searchByName(student.getName()));
    }

    @Test
    void findAll() {
        Student student1=studentDao.createStudent("Fredrik Svennson","Fedrik@gmail.com","Storgatan23 Stockholm");
        Collection<Student> studentList=studentDao.findAll();
        assertEquals(converters.studentsToStudentViews(studentList),testObject.findAll());

    }

    @Test
    void deleteStudent() {
        assertTrue(testObject.deleteStudent(student.getId()));
        assertFalse(studentDao.findAll().contains(student));
    }

    @AfterEach
    void tearDown() {
        StudentSequencer.setStudentSequencer(0);
        studentDao.clear();
    }
}
