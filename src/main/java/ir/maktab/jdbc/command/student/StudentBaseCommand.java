package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.HashMap;
import java.util.Map;

public class StudentBaseCommand implements BaseCommand {
    Scanner sc = new Scanner();
    Map<Integer , BaseCommand> commandsMap = new HashMap<>();
    public StudentBaseCommand(StudentService studentService) {
        commandsMap.put(1 , new ShowStudentsCommand(studentService));
        commandsMap.put(2 , new GetStudentsCommand(studentService));
        commandsMap.put(3 , new AddStudentCommand(studentService));
        commandsMap.put(4 , new UpdateStudentCommand(studentService));
        commandsMap.put(5 , new RemoveStudentCommand(studentService));
    }

    @Override
    public void execute() {
        int command=0;
        while(command!=6) {
            System.out.println("1)show all students 2)show student by id 3)add student 4)update student 5)remove student 6)back");
            command=sc.getInt();
            if (command>6){
                System.out.println("invalid command");
            }else if (command<6){
                commandsMap.get(command).execute();
            }
        }

    }
}
