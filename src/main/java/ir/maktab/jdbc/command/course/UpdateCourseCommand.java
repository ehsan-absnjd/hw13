package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.Optional;

public class UpdateCourseCommand implements BaseCommand {
    CourseService courseService;
    Scanner sc =new Scanner();

    public UpdateCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        System.out.println("enter course id to update:");
        Optional<Course> courseOptional = courseService.loadById(sc.getInt());
        if (courseOptional.isPresent()){
            Course course = courseOptional.get();
            System.out.println("enter new name:");
            String name = sc.getString();
            System.out.println("enter new units:");
            int units = sc.getInt();
            course.setName(name);
            course.setUnit(units);
            courseService.saveOrUpdate(course);
        }
    }
}