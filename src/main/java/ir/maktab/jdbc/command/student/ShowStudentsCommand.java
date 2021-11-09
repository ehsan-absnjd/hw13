package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.service.StudentService;

import java.util.List;
import java.util.Optional;

public class ShowStudentsCommand implements BaseCommand {
    StudentService studentService;

    public ShowStudentsCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() {
        Optional<List<Student>> optionalStudentList = studentService.loadAall();
        if (optionalStudentList.isPresent()){
            optionalStudentList.get().forEach(System.out::println);
        }
    }
}
