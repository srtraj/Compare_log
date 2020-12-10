import java.io.File;
import java.io.FileWriter;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.concurrent.TimeUnit;

public class ReadFile {
    public static void main(String[] args) {
        FileCheck obj=new FileCheck();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter all detailes in detailes.text file:-\ndetailes updated?(y/n)");
        try {

            File inpFile1 = File.createTempFile("file1", ".txt");
            FileWriter myWriter = new FileWriter(inpFile1);
            myWriter.write("Please save and close after update :\nCurrent Log path:\nPrevious Log path:\nTime limit:");
            myWriter.close();
            obj.openfile(inpFile1);

            String inp = sc.nextLine();
            if (inp.equalsIgnoreCase("y")) {
                String f1="",f2="";
                int tl=0;
                Scanner currentReader = new Scanner(inpFile1);
                while (currentReader.hasNextLine()) {
                    String data = currentReader.nextLine();
                    if(data.contains("Current Log path:")){
                         f1 =data.substring(data.indexOf("Current Log path:")+"Current Log path:".length());
                    }
                    if(data.contains("Previous Log path:")){
                         f2 =data.substring(data.indexOf("Previous Log path:")+"Previous Log path:".length());
                    }
                    if(data.contains("Time limit:")){
                        tl =Integer.parseInt(data.substring(data.indexOf("Time limit:")+"Time limit:".length()));
                    }
                }

                obj.checkFile_fun(f1, f2,tl);
                System.out.print("Please check Excel file for output");
            }
            inpFile1.deleteOnExit();
        }catch(Exception e){}

    }
}
