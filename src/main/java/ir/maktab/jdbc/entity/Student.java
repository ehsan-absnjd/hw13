package ir.maktab.jdbc.entity;

import ir.maktab.jdbc.entity.base.BaseEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Student implements BaseEntity<Integer> {
    private Integer id;
    private String name;
    private String familyName;
    private Major major;
    private Set<Course> courses;

    public Student(Integer id, String name, String familyName, Major major, Set<Course> courses) {
        this.id = id;
        this.name = name;
        this.familyName = familyName;
        this.major = major;
        this.courses = courses;
    }

    public Student(String name, String familyName, Major major, Set<Course> courses) {
        this.name = name;
        this.familyName = familyName;
        this.major = major;
        this.courses = courses;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        if (courses == null) {
            courses = new HashSet<>();
        }
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", familyName='" + familyName + '\'' +
                ", major=" + major +
                ", courses=" + courses +
                '}';
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    public static class StudentBuilder {
        private Integer id;
        private String name;
        private String familyName;
        private Major major;
        private Set<Course> courses;

        public StudentBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public StudentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public StudentBuilder major(Major major) {
            this.major = major;
            return this;
        }

        public StudentBuilder courses(Set<Course> courses) {
            this.courses = courses;
            return this;
        }

        public Student build() {
            return new Student(id, name, familyName, major, courses);
        }
    }
}
