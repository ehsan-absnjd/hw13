package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.HashSet;
import java.util.Set;

public class AddStudentCommand implements BaseCommand {
    StudentService studentService;
    Scanner sc =new Scanner();

    public AddStudentCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() {
        System.out.println("enter student's name:");
        String name = sc.getString();
        System.out.println("enter student's family name:");
        String familyName = sc.getString();
        System.out.println("enter student's major id:");
        int majorId = sc.getInt();
        Major major = new Major(majorId);
        Set<Course> courseList = new HashSet<>();
        boolean continueAdding=true;
        while(continueAdding){
            System.out.println("enter course id or enter non-numeric String to exit adding courses:");
            String id = sc.getString();
            try{
                int courseId = Integer.valueOf(id);
                courseList.add(new Course(courseId));
            }catch (Exception e){
                continueAdding=false;
            }
        }
        Student student = new Student.StudentBuilder()
                .name(name)
                .familyName(familyName)
                .major(major)
                .courses(courseList).build();
        studentService.saveOrUpdate(student);
    }
}
