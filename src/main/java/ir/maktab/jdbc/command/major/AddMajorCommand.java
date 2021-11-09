package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utils.Scanner;

public class AddMajorCommand implements BaseCommand {
    MajorService majorService;
    Scanner sc =new Scanner();

    public AddMajorCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        System.out.println("enter major name:");
        String name = sc.getString();
        majorService.saveOrUpdate(new Major(name));
    }
}
