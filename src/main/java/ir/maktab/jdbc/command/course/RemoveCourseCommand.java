package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utils.Scanner;

public class RemoveCourseCommand implements BaseCommand {
    CourseService courseService;
    Scanner sc =new Scanner();

    public RemoveCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        System.out.println("enter course id:");
        courseService.deleteById(sc.getInt());
    }
}
