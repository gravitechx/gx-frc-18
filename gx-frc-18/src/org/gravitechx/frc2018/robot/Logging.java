package org.gravitechx.frc2018.robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Logging {
    public static void main(String[] args) {
        Log();
    }

    public static void Log() {
        FileOutputStream TextOutput = null;
        File file;
        String logHeader = "Team Number\tTimestamp";
        String logText = "6619\t[00:00:01]";
        try {

            file = new File("C:\\Users\\Public\\Desktop\\TEAM_6619_FRC_LOG.txt");
            TextOutput = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            byte[] textToBytes = logHeader.getBytes();

            TextOutput.write(textToBytes);
            TextOutput.flush();

            textToBytes = logText.getBytes();

            TextOutput.write(textToBytes);
            TextOutput.flush();

            System.out.println("Log successfully written.");

        }

        catch (IOException ioe) {

            ioe.printStackTrace();

        }

        finally {

            try {

                if (TextOutput != null) {
                    TextOutput.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("An error occurred in logging to the text file.");
            }
        }
    }
}
