package ir.maktab.jdbc.entity;

import ir.maktab.jdbc.entity.base.BaseEntity;

import java.util.Objects;

public class Course implements BaseEntity<Integer> {
    private Integer id;
    private String name;
    private Integer unit;

    public Course(Integer id, String name, Integer unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    public Course(Integer id) {
        this.id = id;
    }

    public Course(String name, Integer unit) {
        this.name = name;
        this.unit = unit;
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit=" + unit +
                '}';
    }
}
