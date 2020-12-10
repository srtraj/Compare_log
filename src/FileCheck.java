import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileCheck {
    void checkFile_fun(String f1,String f2,int timelimit) {
        try {
            File current_day_File = new File(f1);
            File previous_day_File = new File(f2);

            Scanner currentReader = new Scanner(current_day_File);
            Scanner previousReader = new Scanner(previous_day_File);

            File inpFile1 = File.createTempFile("file1",null);
            File inpFile2 = File.createTempFile("file2",null);

            String home = System.getProperty("user.home");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            File outPutFile = new File(home+"/Downloads/" + "Compare_report_"+dtf.format(now)+ ".csv");

            write_to_file(currentReader,inpFile1);
            write_to_file(previousReader,inpFile2);

            compareFile(inpFile1,inpFile2,outPutFile,timelimit);

            currentReader.close();
            previousReader.close();

            inpFile1.deleteOnExit();
            inpFile2.deleteOnExit();
            openfile(outPutFile);

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    void write_to_file(Scanner reader,File outFile) {
        try {
            FileWriter myWriter = new FileWriter(outFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String time, qry;
                if (data.contains("Time taken:")) {
                    time = data.substring(data.indexOf("Time taken: ") + "Time taken: ".length(), data.indexOf(" seconds"));
                    qry = data.substring(data.indexOf("queryId"), data.indexOf(");"));
                    myWriter.write("<qry_s>" + qry + "<qry_e>" + "<time_s>" + time + "<time_e>\n");
                }
            }
            myWriter.close();
        }catch (Exception e){}
    }

    void compareFile(File f1,File f2,File f3,int timelimit) {
        try {
            FileWriter myWriter = new FileWriter(f3);
            Scanner currentReader = new Scanner(f1);
            Scanner previousReader = new Scanner(f2);
            PrintWriter out = new PrintWriter(myWriter);
            out.print("Current day Qry");
            out.print(",");
            out.print("Previous day Qry");
            out.print(",");
            out.println("Time diff in Minutes");
            out.println();


            while (currentReader.hasNextLine()) {
                String data1 = currentReader.nextLine();
                String data2 = previousReader.nextLine();
                double time1 = Double.parseDouble(data1.substring(data1.indexOf("<time_s>")+8, data1.indexOf("<time_e>")));
                double time2 = Double.parseDouble(data2.substring(data2.indexOf("<time_s>")+8, data2.indexOf("<time_e>")));
                if ((time1-time2)>timelimit) {
                    out.print(data1.substring(data1.indexOf("<qry_s>")+7, data1.indexOf("<qry_e>")));
                    out.print(",");
                    out.print(data2.substring(data2.indexOf("<qry_s>")+7, data2.indexOf("<qry_e>")));
                    out.print(",");
                    out.print((time1-time2)/60);
                    out.println();
                }
            }
            currentReader.close();
            previousReader.close();
           myWriter.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    void openfile(File file){
    try
    {
        if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
        {
            System.out.println("not supported");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        if(file.exists())         //checks file exists or not
            desktop.open(file);//opens the specified file
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}
}
