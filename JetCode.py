import numpy as np
import cv2
import math
from time import sleep

cap = cv2.VideoCapture(0)
if (cap.isOpened()== False): 
  print("Error opening video stream or file")
CLOSE_AREA  = 233452.5

#yellow color ranges
lower_yellow = np.array([15, 140, 20])
upper_yellow = np.array([30, 255, 255])
while(cap.isOpened()):
	#image used for this loop
	ret1, im = cap.read()
	#changing image colorspace from BGR to HSV
	hsv = cv2.cvtColor(im, cv2.COLOR_BGR2HSV)
	
	#resizes the images to 1/4 the size
	resize = 1
	hsv = cv2.resize(hsv, (0,0), fx=resize, fy=resize)
	blite = cv2.resize(hsv, (0,0), fx=resize, fy=resize)
	im = cv2.resize(im, (0,0), fx=resize, fy=resize)
	
	#gets the height, width and channels of the image. (I have no idea what the channels are) 
	height, width, channels = im.shape
	
	#blurs the image
	hsv = cv2.medianBlur (hsv, 5)
	
	#locates all "yellow" and filters everything else out.  Results in a black and white image
	blite = cv2.inRange(hsv, lower_yellow, upper_yellow)
	
	#changes the colorspace of the filtered image to BGR allowing for the contours to be displayed in color
	final = cv2.cvtColor(blite, cv2.COLOR_GRAY2BGR)
	
	#sets up contour stuff
	ret,thresh = cv2.threshold(blite, 255, 255, 255)
	blite, contours, hierarchy = cv2.findContours(thresh,cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE)
	
	if contours != []:
		#variables to be used in for loop
		biggest_contour = 0
		other_contour = 0
		y = 0
		
		#loop that finds the contour with the biggest area.
		#Remeber that everything but yellow has been filtered out reducing the contour count greatly
		for x in range(0, len(contours)):
			if x == 0:
				biggest_contour = cv2.contourArea(contours[x])
				y = x
			elif cv2.contourArea(contours[x]) > cv2.contourArea(contours[y]):
				biggest_contour = cv2.contourArea(contours[x])
				y = x
				x += 1
		
		#draws box contour onto hsv and im
		hsv = cv2.drawContours(hsv, contours[y], -1, (0, 0, 255), 5)
		im = cv2.drawContours(im, contours[y], -1, (0, 0, 255), 5)
		
		#draws the box contour onto final
		final = cv2.drawContours(final, contours[y], -1, (0, 0, 255), 5)
		#variables to be used in finding the average point of the box
		cnt = contours[y]
		M = cv2.moments(cnt)
		
		
		if M['m00'] != 0:
			cx = int(M['m10']/M['m00']) #Average x point of contour box
			cy = int(M['m01']/M['m00']) #Average y point of contour box
			
			#variables to be used in for loop below
			av_len = 0
			total_lengths = 1
			#puts the box contour in a multidimensional array including all points on the contour
			contour = np.vstack(contours[y]).squeeze()
			#loops through the box contour and finds the average distance from the centroid to the perimeter
			for x in range(0, len(contour)):
			    #distance formula
			    av_len += ((contour[x][0] - cx)**2 + (contour[x][1] - cy)**2)**(1/2)
			    total_lengths +=1
			    x += 1
			#finds the average
			av_len = av_len/total_lengths
			
			
			#distances from centroid to edges of screen
			distance_from_left = cx
			distance_from_right = width - cx
			distance_from_top = cy
			distance_from_bottom = height - cy
			
			
			
			#draws the centroid onto final
			cv2.circle(final, (cx, cy), 1, (0,255,0), thickness=1, lineType=8, shift=0)
			
			#draws the centroid onto im
			cv2.circle(im, (cx, cy), 1, (0,255,0), thickness=20, lineType=8, shift=0)
		else:
			print("Moment 'm00' equals zero and will run an error; skipping centroid calculations...")
	else:
		print("There are no contours; skipping drawing them on images and centroid calculations...")
	#displays the images
	cv2.imshow('hsv', hsv)
	cv2.imshow('blite', blite)
	cv2.imshow('final', final)
	cv2.imshow('im', im)
	cv2.waitKey(1)

#kills the windows when a key is pressed
cap.release()
cv2.destroyAllWindows()

#this is necessary for the instantiation of the vision DO NOT DELETE OR ELSE EVERYTHING WILL BREAK
#print("I chose to sat there - 1/21/18")
