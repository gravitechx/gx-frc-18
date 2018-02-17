import numpy as np
import cv2
import math


#white color ranges
upperwhite = np.array([255, 255, 255])
lowerwhite= np.array([250, 250, 250])

#image used in program
im = cv2.imread('C:/Users/GravitechX/Desktop/MoreTape.jpg', 1)

#set up image
image = cv2.medianBlur(im, 5)

#gets the height, width and channels of the image. (I have no idea what the channels are) 
height, width, channels = im.shape

#do the raaaange
intermed = cv2.inRange(image, lowerwhite, upperwhite)

#change the colorspaaaace
final = cv2.cvtColor(intermed, cv2.COLOR_GRAY2BGR)

#sets up contour stuff
ret,thresh = cv2.threshold(intermed, 255, 255, 255)
intermed, contours, hierarchy = cv2.findContours(intermed,cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE)

#finds the biggest contour number and area
biggest_contour = 0
y = 0

for x in range(0, len(contours)):
    if x == 0:
        biggest_contour = cv2.contourArea(contours[x])
        y = x
    elif cv2.contourArea(contours[x]) > cv2.contourArea(contours[y]):
        biggest_contour = cv2.contourArea(contours[x])
        y = x    
    x += 1    

#variables to be used in finding the average point of the box
cnt = contours[y]
M = cv2.moments(cnt)

cx = int(M['m10']/M['m00']) #Average x point of contour box
cy = int(M['m01']/M['m00']) #Average y point of contour box

#finds the biggest and lowest x values and the goo memes
hiya = 0
hiyaval = 0
lowa = len(contours[y])
lowaval = 0

for num in range(0, len(contours[y])):
    if num == 0:
        hiya = num
        lowa = len(contours[y]) - 1
    elif contours[y][num][0][0] < contours[y][lowa][0][0]:
        lowa = num
        lowaval = contours[y][num][0][0] * 0.4
    elif contours[y][num][0][0] > contours[y][hiya][0][0]:
        hiya = num
        hiyaval = contours[y][num][0][0] * 0.4
    x += 1

#yhiyaval = contours[y][hiyaval][][0]

#distances from centroid to edges of screen
#distance_from_left = cx
#distance_from_right = width - cx
#distance_from_top = cy
#distance_from_bottom = height - cy\

#DRAWINGS#
#draws the box contour onto final
final = cv2.drawContours(final, contours[y], -1, (0, 0, 255), 50)

#draws the box contour onto final
final = cv2.drawContours(final, contours[y], -1, (0, 0, 255), 5)

#draws the centroid onto final
cv2.circle(final, (cx, cy), 1, (0,255,0), thickness=100, lineType=8, shift=0)

#draws box contour onto hsv and im
im = cv2.drawContours(im, contours[y], -1, (0, 0, 255), 5)

#draws the centroid onto im
cv2.circle(im, (cx, cy), 1, (0,255,0), thickness=100, lineType=8, shift=0)

#draws center point of image
cv2.circle(im, (int(width/2), cy), 1, (255,0,0), thickness=100, lineType=8, shift=0)
distance = cx - int(width/2)

#END PROCESSES#
#resizes the images
resize = .4
im = cv2.resize(im, (0,0), fx=resize, fy=resize)
intermed = cv2.resize(intermed, (0,0), fx=resize, fy=resize)
final = cv2.resize(final, (0,0), fx=resize, fy=resize)
height, width, channels = im.shape
realcx = cx * resize
middleline = width / 2
distancefrom = realcx - middleline
hiyaval = math.ceil(hiyaval)
lowaval = math.floor(lowaval)
cv2.circle(im, (hiyaval, 0), 1, (0,255,0), thickness=10, lineType=8, shift=0)
cv2.circle(im, (lowaval, 0), 1, (0,255,0), thickness=10, lineType=8, shift=0)
#DO NOT DELETE essential string
print("I chose to sat there -Nira 1/21/18")
print("HEIGHT / WIDTH")
print(height, width)
print("MIDDLE X VALUE")
print(middleline)
print("DISTANCE FROM X VALUE")
print(distancefrom)
print("HIYA")
print(hiya, hiyaval)
print("LOWA")
print(lowa, lowaval)

#displays the images
cv2.imshow('Start', im)
cv2.imshow('Middle', intermed)
cv2.imshow('Finish', final)

#kills the windows when a key is pressed
cv2.waitKey(0)
cv2.destroyAllWindows()
