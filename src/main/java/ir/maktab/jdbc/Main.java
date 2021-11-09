package ir.maktab.jdbc;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.command.course.CourseBaseCommand;
import ir.maktab.jdbc.command.major.MajorBaseCommand;
import ir.maktab.jdbc.command.student.StudentBaseCommand;
import ir.maktab.jdbc.service.CourseService;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner();

        MajorService majorService = new MajorService();
        CourseService courseService = new CourseService();
        StudentService studentService = new StudentService();

        Map<Integer , BaseCommand> commandsMap = new HashMap<>();
        commandsMap.put(1 , new MajorBaseCommand(majorService));
        commandsMap.put(2, new CourseBaseCommand(courseService));
        commandsMap.put(3, new StudentBaseCommand(studentService));

        int command=0;
        while (command!=4){
            System.out.println("1)major operations 2)course operations 3)student operations 4)exit");
            command=sc.getInt();
            if (command>4){
                System.out.println("invalid command");
            }else if (command<4){
                commandsMap.get(command).execute();
            }else{
                System.out.println("bye!");
            }
        }
    }
}
