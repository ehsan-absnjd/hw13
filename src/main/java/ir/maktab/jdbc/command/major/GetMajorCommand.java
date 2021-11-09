package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.Optional;

public class GetMajorCommand implements BaseCommand {
    MajorService majorService;
    Scanner sc =new Scanner();

    public GetMajorCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        System.out.println("enter major id:");
        Optional<Major> majorOptional = majorService.loadById(sc.getInt());
        if (majorOptional.isPresent()){
            System.out.println(majorOptional.get());
        }
    }
}
