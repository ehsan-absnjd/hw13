package ir.maktab.jdbc.utils;


public class Scanner {
    java.util.Scanner sc = new java.util.Scanner(System.in );
    public int getInt(){
        int a;
        while(true){
            try{
                String st = sc.next();
                a = Integer.valueOf(st);
                break;
            }catch (Exception e){
                System.out.println("invalid input, try again");
            }
        }
        return a;
    }
    public double getDouble(){
        double a;
        while(true){
            try{
                String st = sc.next();
                a = Double.valueOf(st);
                break;
            }catch (Exception e){
                System.out.println("invalid input, try again");
            }
        }
        return a;
    }
    public String getString(){
        return sc.next();
    }
    public String getPassword(){
        String input=getString();
        while(input.length()<8){
            System.out.println("password must be at least 8 characters");
            input=getString();
        }
        return input;
    }
}
