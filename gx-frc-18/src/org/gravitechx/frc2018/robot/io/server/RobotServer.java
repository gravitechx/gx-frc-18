package org.gravitechx.frc2018.robot.io.server;

import org.gravitechx.frc2018.frames.AmpFrame;
import org.gravitechx.frc2018.frames.Frame;
import org.gravitechx.frc2018.frames.StatusFrame;
import org.gravitechx.frc2018.frames.VisionFrame;
import org.gravitechx.frc2018.utils.visionhelpers.VisionInfo;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class RobotServer implements Runnable {
    private boolean ServerRunning;
    private final int UPDATE_TIME;
    private final int PORT;
    private Stack<Frame> frameStack;
    public FramesSocket visionsocket;
    private ArrayList<FramesSocket> unsorted; //List of unsorted frames


    public RobotServer(int port, int update){
        this.PORT = port;
        this.UPDATE_TIME = update;
        ServerRunning = true;
        frameStack = new Stack<>();
        unsorted = new ArrayList<>();
    }

    public synchronized void add(Frame frame){
        frameStack.push(frame);
    }

    
    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            server.setSoTimeout(100);
            while (ServerRunning) {
                //System.out.println("Server running!");
                for(int i = unsorted.size()-1;i>=0;i--) {
                    if (unsorted.get(i).getType()=="VISION") {
                        if(visionsocket!=null) {
                            visionsocket.close();
                        }
                        visionsocket = unsorted.get(i);
                        unsorted.remove(i);
                    }
                }
                try {
                    //System.out.println("Trying to accept new connection");
                    Socket newClientSocket = server.accept();
                    FramesSocket testsocket = new FramesSocket(newClientSocket);
                    Thread testthread = new Thread(testsocket);
                    testthread.start();
                    unsorted.add(testsocket);
                } catch (SocketTimeoutException e) {
                    //Do nothing, this is normal.
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            server.close();
            System.out.println("Server stopped");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getnumber() {
        return 5;
    }

    public VisionInfo getVisionInfo() { //((VisionFrame) frame).getVisionInfo()
        if (visionsocket != null && visionsocket.frame != null) {
            return ((VisionFrame) visionsocket.frame).getVisionInfo();
        }
        return null;
    }

    public synchronized void setIsRunning(boolean isRunning){
        this.ServerRunning = isRunning;
    }
    
    class FramesSocket implements Runnable {
        BufferedReader in;
        PrintWriter out;
        boolean mClientIsRunning;
        String inputLine;
        Socket clientSocket;
        boolean recff; //Recieved first frame
        String type; //Holds what type of socket this is
        public Frame frame; //Is the most recent frame recieved

        public FramesSocket(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            type="UNKNOWN";
            mClientIsRunning = true;
	        recff=false;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void close() { mClientIsRunning=false; }
        public String getType() { return type; }
        @Override
        public void run() { //Called from other run() in this class
           while (mClientIsRunning) {
                try { //Try to call the next line of information
                    //System.out.println(in);
                    inputLine = in.readLine();
                } catch (IOException ioe) {
                    //mClientIsRunning=false;
                    ioe.printStackTrace();
                }
                if (inputLine != null) { //If the line isn't nothing (null)
                    recff=true;
                    frame = Frame.toFrame(inputLine); //Create new frame with the line
                    switch (frame.getType()) { //See what type of frame the line is (now inside of a frame object) and take action accordingly
                        case AMP:
                            double amp = ((AmpFrame) frame).getAmps();
                            System.out.println("AMP: " + amp);
                            break;
                        case VISION: //If the frame is for vision
                            //Possibly comment out the below because it can be derived from the class field frame
                            double boxOffset = ((VisionFrame) frame).getBoxOffset(); //Set all of the frame's information to local variables
                            double boxDistance = ((VisionFrame) frame).getBoxDistance();
                            double boxAngle = ((VisionFrame) frame).getBoxAngle();
                            double tapeOffset = ((VisionFrame) frame).getTapeOffset();
                            double tapeDistance = ((VisionFrame) frame).getTapeDistance();
                            double tapeAngle = ((VisionFrame) frame).getTapeAngle();
                            VisionInfo visionInfo = ((VisionFrame) frame).getVisionInfo(); //Have a local version of the VisionInfo object with all of the above information inside of it
                            System.out.println("BOX_OFFSET:" + visionInfo.getBoxOffset() + ", BOX_DISTANCE:" + boxDistance + ", BOX_ANGLE: " + boxAngle + ", TAPE_OFFSET: " + tapeOffset + ", TAPE_DISTANCE: " + tapeDistance + ", TAPE_ANGLE: " + tapeAngle );
                            type="VISION";
                            break;
                        case STATUS:
                            String msg = ((StatusFrame) frame).getmStatusCode().getMessage();
                            System.out.println("Message: " + msg);
                            break;
                    } //End switch statement
                } //End if statement
                    else if (recff==true) { //Close down and break out of the infinite while loop if the connection is closed
                    System.out.println("Client Thread : Bye bye now. (Connection Dropped)");
                    mClientIsRunning = false;
                    break;
                }
            } //End of infinite while loop
            try {
                clientSocket.close();
                System.out.println("Client Socket closed.");
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
