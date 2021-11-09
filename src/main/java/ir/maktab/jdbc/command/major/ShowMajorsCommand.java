package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.entity.Major;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.List;
import java.util.Optional;

public class ShowMajorsCommand implements BaseCommand {
    MajorService majorService;
    Scanner sc =new Scanner();

    public ShowMajorsCommand(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void execute() {
        Optional<List<Major>> majorListOptional = majorService.loadAall();
        if (majorListOptional.isPresent()){
            majorListOptional.get().forEach(System.out::println);
        }
    }
}
