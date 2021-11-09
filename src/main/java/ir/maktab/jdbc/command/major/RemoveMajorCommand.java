package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utils.Scanner;

public class RemoveMajorCommand implements BaseCommand {
    MajorService majorService;
    Scanner sc =new Scanner();

    public RemoveMajorCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        System.out.println("enter major id:");
        majorService.deleteById(sc.getInt());
    }
}
