package ir.maktab.jdbc.command.student;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Course;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.entity.Student;
import ir.maktab.jdbc.service.StudentService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UpdateStudentCommand implements BaseCommand {
    StudentService studentService;
    Scanner sc = new Scanner();

    public UpdateStudentCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void execute() {
        System.out.println("enter student id to update:");
        Optional<Student> studentOptional = studentService.loadById(sc.getInt());
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            System.out.println("enter student's name:");
            String name = sc.getString();
            student.setName(name);
            System.out.println("enter student's family name:");
            String familyName = sc.getString();
            student.setFamilyName(familyName);
            System.out.println("enter student's major id:");
            int majorId = sc.getInt();
            student.setMajor(new Major(majorId));
            Set<Course> courseList = new HashSet<>();
            boolean continueAdding = true;
            while (continueAdding) {
                System.out.println("enter course id or enter non-numeric String to exit adding courses:");
                String id = sc.getString();
                try {
                    int courseId = Integer.valueOf(id);
                    courseList.add(new Course(courseId));
                } catch (Exception e) {
                    continueAdding = false;
                }
            }
            student.setCourses(courseList);
            studentService.saveOrUpdate(student);
        }
    }

}
