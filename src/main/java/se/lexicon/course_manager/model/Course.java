package se.lexicon.course_manager.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;


public class Course implements Serializable {

   private  int id;
   private  String courseName;
   private LocalDate startDate;
   private int weekDuration;
   private Collection<Student> students;

    public Course() {
        this.students=new HashSet<>();
    }

    public Course(int id) {
        this.students=new HashSet<>();
        this.id=id;
    }

    public Course( int id,String courseName, LocalDate startDate, int weekDuration,Collection<Student> students) {
        this.id = id;
        this.courseName = courseName;
        this.startDate = startDate;
        this.weekDuration = weekDuration;
        this.students = students;
    }

    public Course(int id, String courseName, LocalDate startDate, int weekDuration) {
        this.id = id;
        this.courseName = courseName;
        this.startDate = startDate;
        this.weekDuration = weekDuration;
        this.students = new HashSet<>();

    }

    public int getId() {
        return id;
    }



    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getWeekDuration() {
        return weekDuration;
    }

    public void setWeekDuration(int weekDuration) {
        this.weekDuration = weekDuration;
    }

    public boolean enrollStudent(Student student){
        if(student != null && !students.contains(student)) {
            students.add(student);
            return true;
        }

        return false;
    }

    public boolean unenrollStudent(Student student){
        return students.remove(student);

    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", startDate=" + startDate +
                ", weekDuration=" + weekDuration +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return getId() == course.getId() && getWeekDuration() == course.getWeekDuration() && Objects.equals(getCourseName(), course.getCourseName()) && Objects.equals(getStartDate(), course.getStartDate()) && Objects.equals(getStudents(), course.getStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCourseName(), getStartDate(), getWeekDuration(), getStudents());
    }
}
