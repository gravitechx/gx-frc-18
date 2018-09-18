import numpy as np
import cv2
name="/home/nvidia/compvids/qual1.jpeg"
cap = cv2.VideoCapture(0)

# Define the codec and create VideoWriter object
out = cv2.VideoWriter(name, cv2.VideoWriter_fourcc('M','J','P','G'), 30, (1280,720))

while(cap.isOpened()):
    ret, frame = cap.read()
    if ret==True:
        frame = cv2.flip(frame,0)

        # write the flipped frame
        out.write(frame)

        #cv2.imshow('frame',frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    else:
        break

# Release everything if job is finished
cap.release()
out.release()
cv2.destroyAllWindows()