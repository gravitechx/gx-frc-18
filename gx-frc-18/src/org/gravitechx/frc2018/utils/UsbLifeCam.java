package org.gravitechx.frc2018.utils;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.gravitechx.frc2018.robot.Constants;

public class UsbLifeCam {
    UsbCamera camera;

    public UsbCamera getCamera(){
        return camera;
    }

    public UsbLifeCam(int port){
        camera = CameraServer.getInstance().startAutomaticCapture(port);
        camera.setResolution(640, 480);
        camera.setFPS(Constants.CAMERA_FPS);
    }
}
