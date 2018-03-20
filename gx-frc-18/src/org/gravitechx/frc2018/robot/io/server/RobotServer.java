package org.gravitechx.frc2018.robot.io.server;

import org.gravitechx.frc2018.frames.AmpFrame;
import org.gravitechx.frc2018.frames.Frame;
import org.gravitechx.frc2018.frames.StatusFrame;
import org.gravitechx.frc2018.frames.VisionFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class RobotServer implements Runnable {
    private boolean mIsRunning;
    private final int UPDATE_TIME;
    private final int PORT;
    private Stack<Frame> frameStack;

    public RobotServer(int port, int update){
        this.PORT = port;
        this.UPDATE_TIME = update;
        mIsRunning = true;
        frameStack = new Stack<>();
    }

    public synchronized void add(Frame frame){
        frameStack.push(frame);
    }

    class FramesSocket implements Runnable {
        BufferedReader in;
        PrintWriter out;
        String inputLine;
        Socket clientSocket;


        public FramesSocket(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {

            while (true) {
                try {
                    inputLine = in.readLine();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                if (inputLine != null) {
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
                            double boxAngle = ((VisionFrame) frame).getBoxAngle();
                            double tapeOffset = ((VisionFrame) frame).getTapeOffset();
                            double tapeDistance = ((VisionFrame) frame).getTapeDistance();
                            double tapeAngle = ((VisionFrame) frame).getTapeAngle();
                            System.out.println("BOX_OFFSET:" + boxOffset + ", BOX_DISTANCE:" + boxDistance + ", BOX_ANGLE: " + boxAngle + ", TAPE_OFFSET: " + tapeOffset + ", TAPE_DISTANCE: " + tapeDistance + ", TAPE_ANGLE: " + tapeAngle );

                            break;
                        case STATUS:
                            String msg = ((StatusFrame) frame).getmStatusCode().getMessage();
                            System.out.println("Message: " + msg);
                            break;
                    }
                }

                if (clientSocket.isClosed()) {
                    System.out.println("Server : Bye bye now. (Connection Dropped)");
                    mIsRunning = false;
                    break;
                }


            }
            System.out.println("Haha Katie, I've bypassed your puny statements. :D Hahahahahahahaha............");
            try {
                clientSocket.close();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        System.out.println("You've gotten here!");
        try {
            ServerSocket server = new ServerSocket(PORT);
            while (mIsRunning) {
                try (
                        Socket clientSocket = server.accept();
                ) {
                    System.out.println("before input!");
                    (new Thread(new FramesSocket(clientSocket))).run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void setIsRunning(boolean isRunning){
        this.mIsRunning = isRunning;
    }

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
}