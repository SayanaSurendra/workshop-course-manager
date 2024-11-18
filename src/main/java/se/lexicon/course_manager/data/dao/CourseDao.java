package se.lexicon.course_manager.data.dao;




import se.lexicon.course_manager.dto.views.StudentView;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CourseDao {

    Course createCourse(String courseName, LocalDate startDate, int weekDuration);
    Course findById(int id);
    Collection<Course> findByNameContains(String name);
    Collection<Course> findByDateBefore(LocalDate end);
    Collection<Course> findByDateAfter(LocalDate start);
    Collection<Course> findAll();
    Collection<Course> findByStudentId(int studentId);
    boolean removeCourse(Course course);
    void clear();

}
