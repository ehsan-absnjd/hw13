package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utils.Scanner;

public class AddCourseCommand implements BaseCommand {
    CourseService courseService;
    Scanner sc =new Scanner();

    public AddCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        System.out.println("enter course name:");
        String name = sc.getString();
        System.out.println("enter course units:");
        int units = sc.getInt();
        courseService.saveOrUpdate(new Course(name,units));
    }
}
