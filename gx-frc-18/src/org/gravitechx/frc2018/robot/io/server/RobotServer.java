package org.gravitechx.frc2018.robot.io.server;

import org.gravitechx.frc2018.frames.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ExportException;
import java.util.Stack;

public class RobotServer implements Runnable {
    private boolean mIsRunning;
    private final int UPDATE_TIME;
    private final int PORT;
    private Stack<Frame> frameStack = new Stack<>();

    public RobotServer(int port, int update){
        this.PORT = port;
        this.UPDATE_TIME = update;
        mIsRunning = true;
    }

    public synchronized void add(Frame frame){
        frameStack.push(frame);
    }

    class FramesSocket implements Runnable{
        BufferedReader in;
        PrintWriter out;
        String inputLine;
        Socket clientSocket;


        public FramesSocket(Socket clientSocket){

            try {
               in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               out = new PrintWriter(clientSocket.getOutputStream());

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (clientSocket.isConnected()) {
                try {
                    inputLine = in.readLine();
                } catch (IOException ioe) {
                    continue;
                }
                System.out.println(inputLine);
                Frame frame = Frame.toFrame(inputLine);
                switch (frame.getType()) {
                    case AMP:
                        double amp = ((AmpFrame) frame).getAmps();
                        System.out.println("AMP: " + amp);
                        break;
                    case VISION:
                        double boxOffset = ((VisionFrame) frame).getBoxOffset();
                        double boxDistance = ((VisionFrame) frame).getBoxDistance();
                        double tapeOffset = ((VisionFrame) frame).getTapeOffset();
                        double tapeDistance = ((VisionFrame) frame).getTapeDistance();
                        System.out.printlniukj("BOX_OFFSET:" + boxOffset + ", BOX_DISTANCE:" + boxDistance + ", TAPE_OFFSET:" + tapeOffset + ", TAPE_DISTANCE:" + tapeDistance);
                        break;
                    case STATUS:
                        String msg = ((StatusFrame) frame).getmStatusCode().getMessage();
                        System.out.println("Message: " + msg);
                        break;

                }
            }
            try {
                clientSocket.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void run() {
        System.out.println("You've gotten here!");
        try(
                ServerSocket server = new ServerSocket(PORT);
        ){

            /*
                    ---- s e n d i n g ----
            System.out.println("Starting up the server...");

            (new StatusFrame(new LocalTimestamp(), StatusCode.START)).send(out);

            while(mIsRunning){
                if(!frameStack.isEmpty()){
                    frameStack.pop().send(out);
                }
                System.out.printf("Stack is empty: %b\n", frameStack.isEmpty());
                Thread.sleep(UPDATE_TIME);
            }
            (new StatusFrame(new LocalTimestamp(), StatusCode.STOP)).send(out);
            System.out.println("Connection with client ended.");

            */

            while (mIsRunning) {
                System.out.println("wait for client connection...");
                Socket clientSocket = server.accept();
                System.out.println("before input!");
                System.out.println(clientSocket.isConnected());

                // spawn a new thread to handle this client session
                new Thread(new FramesSocket(clientSocket)).run();
            }


        }catch(Exception e) {
            e.printStackTrace();
        }finally{

        }
    }

    public synchronized void setIsRunning(boolean isRunning){
        this.mIsRunning = isRunning;
    }
}