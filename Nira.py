import numpy as np
import cv2
import math

CLOSE_AREA  = 233452.5

#yellow color ranges
lower_yellow = np.array([27, 50, 145])
upper_yellow = np.array([40, 255, 255])
    
#image used in program
im = cv2.imread('C:/Users/GravitechX/Desktop/FiveFeet.jpg', 1)

#changing image colorspace from BmetersGR to HSV
hsv = cv2.cvtColor(im, cv2.COLOR_BGR2HSV)

#resizes the images to 1/4 the size
resize = 1
hsv = cv2.resize(hsv, (0,0), fx=resize, fy=resize)
blite = cv2.resize(hsv, (0,0), fx=resize, fy=resize)
im = cv2.resize(im, (0,0), fx=resize, fy=resize)

#gets the height, width and channels of the image. (I have no idea what the channels are) 
height, width, channels = im.shape
print ("Height %s of image" %height)
print ("Width %s of image" %width)

#blurs the image
#hsv = cv2.medianBlur (hsv, 5)

#locates all "yellow" and filters everything else out.  Results in a black and white image
blite = cv2.inRange(hsv, lower_yellow, upper_yellow)

#changes the colorspace of the filtered image to BGR allowing for the contours to be displayed in color
final = cv2.cvtColor(blite, cv2.COLOR_GRAY2BGR)

#sets up contour stuffmeters
ret,thresh = cv2.threshold(blite, 255, 255, 255)
blite, contours, hierarchy = cv2.findContours(thresh,cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE)


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

#variables to be used in finding the average point of the box
cnt = contours[y]
M = cv2.moments(cnt)

cx = int(M['m10']/M['m00']) #Average x point of contour box
cy = int(M['m01']/M['m00']) #Average y point of contour box


final = cv2.drawContours(final, contours[y], -1, (0, 0, 255), 5)

#distances from centroid to edges of screen
distance_from_left = cx
distance_from_right = width - cx
distance_from_top = cy
distance_from_bottom = height - cy



#draws the centroid onto final
cv2.circle(final, (cx, cy), 1, (0,255,0), thickness=1, lineType=8, shift=0)

#draws box contour onto hsv and im
#hsv = cv2.drawContours(hsv, contours[y], -1, (0, 0, 255), 5)
im = cv2.drawContours(im, contours[y], -1, (0, 0, 255), 5)

#finds height, width, and other of contour
oneBox = False
x,y,w,h = cv2.boundingRect(contours[y])
cv2.rectangle(im,(x,y),(x+w,y+h),(0,255,0),2)

print (w, h)
print (w/h)

if (1.1 > w/h > 0.95):
    print ("One box")
    oneBox = True
else:
    print("several boxes")

#draws center point of image
xCenter = (int(width/2))
yCenter = (int(height/2))
cv2.circle(im, (xCenter, cy), 1, (255,0,0), thickness=10, lineType=8, shift=0)

distance_to_center = xCenter - cx

#draws the centroid onto im
cv2.circle(im, (cx, cy), 1, (0,255,0), thickness=10, lineType=8, shift=0)

ratio = width/w
print("RATIO: %s" %ratio)

#Using Width Ratio
WvarX = 1.575778547
WoriImage = 289
Wfactor = (WoriImage/width) * WvarX
#Wfactor = WvarX
print ("factor: %s" %Wfactor)
Wdistance = (width/w) * Wfactor
#Wdistance *= 0.3048
print ("%s meters" %Wdistance)

WdistToCent = (distance_to_center * ((13/12)/w))

Wdegree = -1*((WdistToCent/(2*math.pi*Wdistance))*360)
print ("Degree: %s" %Wdegree)

#Using Height Ratio
#HvarX = .9287109375
#HoriImage = 512
#Hfactor = (height/HoriImage) * HvarX
#print ("factor: %s" %Hfactor)
#Hdistance = (height/h) * Hfactor
#distance *= 0.3048
#print ("%s meters" %Hdistance)

#HdistToCent = (distance_to_center * ((13/12)/h))

#Hdegree = (HdistToCent/(2*math.pi*Hdistance))*360
#print ("Degree: %s" %Hdegree)



#this is necessary for the instantiation of the vision DO NOT DELETE OR ELSE EVERYTHING WILL BREAK
print("I chose to sat there - 1/21/18")



#displays the images
cv2.imshow('hsv', hsv)
cv2.imshow('blite', blite)
cv2.imshow('final', final)
cv2.imshow('im', im)

#kills the windows when a key is pressed
cv2.waitKey(0)
cv2.destroyAllWindows()
