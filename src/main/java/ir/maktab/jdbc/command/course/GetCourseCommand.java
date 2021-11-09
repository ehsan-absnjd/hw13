package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.Optional;

public class GetCourseCommand implements BaseCommand {
    CourseService courseService;
    Scanner sc =new Scanner();

    public GetCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        System.out.println("enter course id:");
        Optional<Course> courseOptional = courseService.loadById(sc.getInt());
        if(courseOptional.isPresent()){
            System.out.println(courseOptional.get());
        }
    }
}
