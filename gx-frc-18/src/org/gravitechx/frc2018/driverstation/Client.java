package org.gravitechx.frc2018.driverstation;

import org.gravitechx.frc2018.frames.AmpFrame;
import org.gravitechx.frc2018.frames.Frame;
import org.gravitechx.frc2018.frames.StatusFrame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This is the client that runs on the driver station and receives information about amperage updates, robot status, and errors.
 */
public class Client {
    public static void main(String[] args){
        int PORT = 5800;
        String host = "localhost"; // For testing
        try(
                Socket server = new Socket(host, PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        ){
            String fromServer;
            while(server.isConnected()){
                if((fromServer = in.readLine()) != null){
                    System.out.println(fromServer);
                    Frame frame = Frame.toFrame(fromServer);
                    switch (frame.getType()){
                        case AMP:
                            double amp = ((AmpFrame) frame).getAmps();
                            System.out.println("AMP: " + amp);
                            break;
                        case STATUS:
                            String msg = ((StatusFrame) frame).getmStatusCode().getMessage();
                            System.out.println("Message: " + msg);
                            break;
                    }

                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
