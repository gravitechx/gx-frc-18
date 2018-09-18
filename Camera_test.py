import cv2
cap=cv2.VideoCapture(0)
while (True):
	ret,image=cap.read()
	cv2.waitKey(1)
	cv2.imshow('Picture',image)
