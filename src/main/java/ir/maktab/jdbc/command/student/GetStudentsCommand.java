package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.Optional;

public class GetStudentsCommand implements BaseCommand {
    StudentService studentService;
    Scanner sc =new Scanner();

    public GetStudentsCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() {
        System.out.println("enter student id:");
        Optional<Student> studentOptional = studentService.loadById(sc.getInt());
        if (studentOptional.isPresent()){
            System.out.println(studentOptional.get());
        }
    }
}
