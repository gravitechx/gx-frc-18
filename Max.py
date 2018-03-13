import numpy as np
import cv2
import math


#white color ranges
upperwhite = np.array([255, 255, 255]) # <-- white 
lowerwhite= np.array([250, 250, 250])   # <-- white
#upperwhite = np.array([215, 255, 125]) #<-- green
#lowerwhite= np.array([80, 150, 0]) #<-- green

#image used in program
im = cv2.imread('C:/Users/GravitechX/Desktop/4ftTape.jpg', 1)

#set up image
image = cv2.medianBlur(im, 5)

#gets the height, width and channels of the image. (I have no idea what the channels are) 
height, width, channels = im.shape

#do the raaaange
intermed = cv2.inRange(image, lowerwhite, upperwhite)

#change the colorspace
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

#variables to be used in finding the average point of the tape
cnt = contours[y]
M = cv2.moments(cnt)

cx = int(M['m10']/M['m00']) #Average x point of contour box
cy = int(M['m01']/M['m00']) #Average y point of contour box

#finds the biggest and lowest x values and the values 
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

yhiyaval = contours[y][hiya][0][1]
ylowaval = contours[y][lowa][0][1]

#cv2.GoodMemes(now)

rect = cv2.minAreaRect(cnt)
box = cv2.boxPoints(rect)
box = np.int0(box)
cv2.drawContours(im, [box], 0, (0,255,255), 5)
cv2.drawContours(final, [box], 0, (0,255,255), 5)

#DRAWINGS#
#draws the box contours
final = cv2.drawContours(final, contours[y], -1, (0, 0, 255), 5)
im = cv2.drawContours(im, contours[y], -1, (0, 0, 255), 5)

#draws the centroids
cv2.circle(final, (cx, cy), 1, (0,255,0), thickness=30, lineType=8, shift=0)
cv2.circle(im, (cx, cy), 1, (0,255,0), thickness=30, lineType=8, shift=0)

#draws center points
cv2.circle(im, (int(width/2), cy), 1, (255,0,0), thickness=30, lineType=8, shift=0)
cv2.circle(final, (int(width/2), cy), 1, (255,0,0), thickness=30, lineType=8, shift=0)

distance = cx - int(width/2)

#END PROCESSES#
#resizes the images
resize = 1
im = cv2.resize(im, (0,0), fx=resize, fy=resize)
intermed = cv2.resize(intermed, (0,0), fx=resize, fy=resize)
final = cv2.resize(final, (0,0), fx=resize, fy=resize)

#finds dimensions of image
height, width, channels = im.shape

#mitochondria is the powerhouse of the cell
realcx = cx * resize
middleline = width / 2
distancefrom = realcx - middleline
hiyaval = math.ceil(hiyaval)
lowaval = math.floor(lowaval)
yhiyaval = yhiyaval * resize
yhiyaval = math.ceil(yhiyaval)
ylowaval = ylowaval * resize
ylowaval = math.floor(ylowaval)

#set up variables for corners
realx = 0
realy = 0
realxx = 0
realyy = 0

#comparing the y values of the rectangle to find bottom
firstheight = box[0][1] - box[1][1]
secondheight = box[0][1] - box[3][1]
#sets the bottom corner (x, y) values to the bottom left corner
if firstheight > secondheight:
    realx = box[0][0]
    realy = box[0][1]
if firstheight < secondheight:
    realx = box[1][0]
    realy = box[1][1]

realx = realx * resize
realy = realy * resize

realx = math.ceil(realx)
realy = math.ceil(realy)

firstthing = box[3][1] - box[2][1]
secondthing = box[0][1] - box[3][1]
#does the same thing as above but with bottom right corner
if firstthing > secondthing:
    realxx = box[3][0]
    realyy = box[3][1]
if firstthing < secondthing:
    realxx = box[0][0]
    realyy = box[0][1]

realxx = realxx * resize
realyy = realyy * resize

realxx = math.ceil(realxx)
realyy = math.ceil(realyy)

#finds the top left corner of the bounding rectangle (x, y) and the width and height (h and w)
x,y,w,h = cv2.boundingRect(cnt)
#adjust to image resize value to conform to social convention
x = x * resize
y = y * resize
w = w * resize
h = h * resize
#round so that it doesn't give you an error due to float value
x = math.ceil(x)
y = math.ceil(y)
w = math.ceil(w)
h = math.ceil(h)
#draws the good rectangles
cv2.rectangle(final,(x,y),(x+w,y+h),(0,255,0),2)
cv2.rectangle(im,(x,y),(x+w,y+h),(0,255,0),2)

factor = 0.3149331777342597     
factor = factor / 2             #factor used in finding distance from tape to robot

firstsquare = math.pow(realxx - realx, 2)   #used to find width of tape in pixels
secondsquare = math.pow(realyy - realy, 2)  #used to find width of tape in pixels

widthoftape = math.sqrt(firstsquare + secondsquare) #Pythagorean Theorem to find width of tape

distance = (width/widthoftape) * factor     #Finds distance from robot to tape
distance_in_meters = distance * 0.3048      #Converts distance to meters

cent_of_img = (width/2)                     #Finds center of image

dist_to_cent = ((cx-cent_of_img) * ((1/6)/widthoftape))     #Finds arc distance (used for finding degrees)

degree = (dist_to_cent/(math.pi * 2 * distance)) * 360      #Finds degree (positive = right)

#draws the good circles
cv2.circle(im, (hiyaval, yhiyaval), 1, (0,255,0), thickness=10, lineType=8, shift=0)
cv2.circle(im, (lowaval, ylowaval), 1, (0,255,0), thickness=10, lineType=8, shift=0)
cv2.circle(final, (hiyaval, yhiyaval), 1, (0,255,0), thickness=10, lineType=8, shift=0)
cv2.circle(final, (lowaval, ylowaval), 1, (0,255,0), thickness=10, lineType=8, shift=0)

cv2.circle(im, (realx, realy), 1, (0,255,0), thickness=10, lineType=8, shift=0)
cv2.circle(final, (realx, realy), 1, (0,255,0), thickness=10, lineType=8, shift=0)
cv2.circle(im, (realxx, realyy), 1, (0,255,0), thickness=10, lineType=8, shift=0)
cv2.circle(final, (realxx, realyy), 1, (0,255,0), thickness=10, lineType=8, shift=0)

#DO NOT DELETE essential string
print("I chose to sat there -Nira 1/21/18")

#displays the images
cv2.imshow('Start', im)
cv2.imshow('Middle', intermed)
cv2.imshow('Finish', final)

#kills the windows when a key is pressed
cv2.waitKey(0)
cv2.destroyAllWindows()
#mitochondria is still the powerhouse of the cell
