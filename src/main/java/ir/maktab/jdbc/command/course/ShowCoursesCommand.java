package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.List;
import java.util.Optional;

public class ShowCoursesCommand implements BaseCommand {
    CourseService courseService;
    Scanner sc =new Scanner();

    public ShowCoursesCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute() {
        Optional<List<Course>> optionalCourseList = courseService.loadAall();
        if (optionalCourseList.isPresent()){
            optionalCourseList.get().forEach(System.out::println);
        }
    }
}
