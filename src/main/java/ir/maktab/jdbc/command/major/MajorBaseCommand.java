package ir.maktab.jdbc.command.major;

import ir.maktab.jdbc.command.base.BaseCommand;
import ir.maktab.jdbc.service.MajorService;
import ir.maktab.jdbc.utils.Scanner;

import java.util.HashMap;
import java.util.Map;

public class MajorBaseCommand implements BaseCommand {
    Scanner sc =new Scanner();
    Map<Integer , BaseCommand> commandsMap = new HashMap<>();
    public MajorBaseCommand(MajorService majorService) {
        commandsMap.put(1, new ShowMajorsCommand(majorService));
        commandsMap.put(2, new GetMajorCommand(majorService));
        commandsMap.put(3, new AddMajorCommand(majorService));
        commandsMap.put(4, new UpdateMajorCommand(majorService));
        commandsMap.put(5, new RemoveMajorCommand(majorService));
    }
    @Override
    public void execute() {
        int command=0;
        while(command!=6) {
            System.out.println("1)show all majors 2)show major by id 3)add major 4)update major 5)remove major 6)back");
            command=sc.getInt();
            if (command>6){
                System.out.println("invalid command");
            }else if (command<6){
                commandsMap.get(command).execute();
            }
        }
    }
}
