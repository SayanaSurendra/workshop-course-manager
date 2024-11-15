package se.lexicon.course_manager.data.dao;



import se.lexicon.course_manager.data.sequencers.CourseSequencer;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;

import java.time.LocalDate;
import java.util.*;

public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
       Course course= new Course(CourseSequencer.nextCourseId(),courseName,startDate,weekDuration);
        if(courses.add(course)) {
            return course;
        }
        return null;
    }

    @Override
    public Course findById(int id) {

        for(Course course:courses){
            if(course.getId()==id){
                return course;
            }
        }
        return null;
    }


    @Override
    public Collection<Course> findByNameContains(String name) {
        Collection<Course> courseList=new HashSet<>();
        for(Course course:courses){
            if(course.getCourseName()==name){
                courseList.add(course);
            }
        }
        return courseList;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {
        Collection<Course> courseList=new HashSet<>();
        for(Course course:courses){
           if(course.getStartDate().isBefore(end)){
               courseList.add(course);
           }
        }
        return courseList;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        Collection<Course> courseList=new HashSet<>();
        for(Course course:courses){
            if(course.getStartDate().isAfter(start)){
                courseList.add(course);
            }
        }
        return courseList;

    }

    @Override
    public Collection<Course> findAll() {
       // return new HashSet<>(courses);
        return Collections.unmodifiableCollection(courses);
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        Collection<Course> courseList=new HashSet<>();
        for(Course course:courses){
            for(Student student:course.getStudents()){
                { if(student.getId()==studentId)
                    courseList.add(course);
                }
            }
        }
        return courseList;

    }

    @Override
    public boolean removeCourse(Course course) {
         return courses.remove(course);
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}
