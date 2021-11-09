package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utils.Scanner;

public class RemoveStudentCommand implements BaseCommand {
    StudentService studentService;
    Scanner sc =new Scanner();

    public RemoveStudentCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() {
        System.out.println("enter student id:");
        studentService.deleteById(sc.getInt());
    }
}
