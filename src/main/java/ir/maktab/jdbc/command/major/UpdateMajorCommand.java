package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.Optional;

public class UpdateMajorCommand implements BaseCommand {
    MajorService majorService;
    Scanner sc =new Scanner();

    public UpdateMajorCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        System.out.println("enter major id to update:");
        Optional<Major> majorOptional = majorService.loadById(sc.getInt());
        if (majorOptional.isPresent()){
            Major major = majorOptional.get();
            System.out.println("enter new name:");
            major.setName(sc.getString());
            majorService.saveOrUpdate(major);
        }

    }
}