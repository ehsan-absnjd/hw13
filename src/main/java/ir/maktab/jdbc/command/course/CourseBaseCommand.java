package ir.maktab.jdbc.command.course;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.HashMap;
import java.util.Map;

public class CourseBaseCommand implements BaseCommand {
    Scanner sc = new Scanner();
    Map<Integer , BaseCommand> commandsMap = new HashMap<>();
    public CourseBaseCommand(CourseService courseService) {
        commandsMap.put(1, new ShowCoursesCommand(courseService));
        commandsMap.put(2, new GetCourseCommand(courseService));
        commandsMap.put(3, new AddCourseCommand(courseService));
        commandsMap.put(4, new UpdateCourseCommand(courseService));
        commandsMap.put(5, new RemoveCourseCommand(courseService));
    }
    @Override
    public void execute() {
        int command=0;
        while(command!=6) {
            System.out.println("1)show all courses 2)show course by id 3)add course 4)update course 5)remove course 6)back");
            command=sc.getInt();
            if (command>6){
                System.out.println("invalid command");
            }else if (command<6){
                commandsMap.get(command).execute();
            }
        }

    }
}
