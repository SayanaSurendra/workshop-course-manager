package se.lexicon.course_manager.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager.data.dao.CourseDao;
import se.lexicon.course_manager.data.dao.StudentDao;
import se.lexicon.course_manager.data.service.converter.Converters;
import se.lexicon.course_manager.dto.forms.CreateCourseForm;
import se.lexicon.course_manager.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager.dto.views.CourseView;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
      Course course= courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration());
       return converters.courseToCourseView(course);
    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        Course course=courseDao.findById(form.getId());
        if(course !=null){
            course.setCourseName(form.getCourseName());
            course.setStartDate(form.getStartDate());
            course.setWeekDuration(form.getWeekDuration());
        }
        return converters.courseToCourseView(course);
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
         Collection<Course> courseList=courseDao.findByNameContains(courseName);
         return converters.coursesToCourseViews(courseList);

    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        Collection<Course> courseList=courseDao.findByDateBefore(end);
        return converters.coursesToCourseViews(courseList);
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        Collection<Course> courseList=courseDao.findByDateAfter(start);
        return converters.coursesToCourseViews(courseList);
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
         Student student=studentDao.findById(studentId);
         Course course=courseDao.findById(courseId);
         return course.enrollStudent(student);
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
       Course course=courseDao.findById(courseId);
       boolean unEnrolled=course.unenrollStudent(studentDao.findById(studentId));
       return unEnrolled;
    }

    @Override
    public CourseView findById(int id) {
       Course course= courseDao.findById(id);
       return converters.courseToCourseView(course);
    }

    @Override
    public List<CourseView> findAll() {
        Collection<Course> courseList=courseDao.findAll();
        return converters.coursesToCourseViews(courseList);
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        Collection<Course> courseList=courseDao.findByStudentId(studentId);
        return converters.coursesToCourseViews(courseList);
    }

    @Override
    public boolean deleteCourse(int id) {
        Course course=courseDao.findById(id);
        return courseDao.removeCourse(course);
    }
}
