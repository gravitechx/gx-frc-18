import threading
import socket
import numpy as np
import cv2
import math
import time
import os.path
info_lock=threading.Lock()
pic_lock=threading.Lock()
global box_offset
global box_senddistance
global tape_offset
global tape_senddistance
box_offset=0
box_senddistance=10
tape_senddistance=10
tape_offset=0
def comms():
	global tape_offset
	global box_senddistance
	global box_offset
	global tape_senddistance
	connecting=False
	sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
	sock.setblocking(0)
	while not connecting:
		try:
			print("About to try to connect to the RoboRIO.")
			sock.connect(("roboRIO-6619-FRC.local",5800))
			connecting=True
			print("Connected!")
		except:
			connecting=False
			print("Failed to connect. Retrying...")			
	while True:
		try:
			time.sleep(1)
			info_lock.acquire()
			sock.send('{"FRAME_ID":2,"TIME":' + str(time.time()-start) + ',"BOX_OFFSET":' + str(box_offset) + ',"BOX_DISTANCE":' + str(box_senddistance) + ',"TAPE_OFFSET":' + str(tape_offset) + ',"TAPE_DISTANCE":' + str(tape_senddistance) + '}\n')
			print('{"FRAME_ID":2,"TIME":' + str(time.time()-start) + ',"BOX_OFFSET":' + str(box_offset) + ',"BOX_DISTANCE":' + str(box_senddistance) + ',"TAPE_OFFSET":' + str(tape_offset) + ',"TAPE_DISTANCE":' + str(tape_senddistance) + '}\n')
			info_lock.release()
		except:
			print("There was a problem with the connection with the RoboRIO. Restarting with comms function.")
			sock.close()
			comms()
def camera_to_image():
	cap=cv2.VideoCapture(0)
	print("Camera initiated.")
	while True:
		print("in while loop")
		pic_lock.acquire()
		print("Have pic lock")
		ret,im=cap.read()
		print("Got camera frame")
		cv2.imwrite("/home/nvidia/latest_camera_pic/pic.jpg",im)
		print("Wrote camera frame to file.")
		pic_lock.release()
		cv2.waitKey(1)
		time.sleep(0.05)
def tape_vision():
	global tape_offset
	global tape_senddistance
	print("tape_vision started")
	#white color ranges
	upperwhite = np.array([255, 255, 255])
	lowerwhite= np.array([250, 250, 250])
	while True:
		if os.path.isfile("/home/nvidia/latest_camera_pic/pic.jpg"):
			print("Tape: image found.")
			#image used in program
			pic_lock.acquire()
			im=cv2.imread("/home/nvidia/latest_camera_pic/pic.jpg")
			print("Tape: image loaded.")
			pic_lock.release()
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
	
			#variables to be used in finding the average point of the box
			if contours != []:
				cnt = contours[y]
				M = cv2.moments(cnt)
				if M['m00'] != 0.0:
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
					info_lock.acquire()
					tape_offset=realcx - middleline
					info_lock.release()
					distancefrom = realcx - middleline
					hiyaval = int(math.ceil(hiyaval))
					lowaval = int(math.floor(lowaval))
					yhiyaval = yhiyaval * resize
					yhiyaval = int(math.ceil(yhiyaval))
					ylowaval = ylowaval * resize
					ylowaval = int(math.floor(ylowaval))

					#sets up variables for corners
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


                                        print ("Degree = %s" %degree)
                                        print ("Distance = %s" %distance)



					#draws the good circles
					cv2.circle(im, (hiyaval, yhiyaval), 1, (0,255,0), thickness=10, lineType=8, shift=0)
					cv2.circle(im, (lowaval, ylowaval), 1, (0,255,0), thickness=10, lineType=8, shift=0)
					cv2.circle(final, (hiyaval, yhiyaval), 1, (0,255,0), thickness=10, lineType=8, shift=0)
					cv2.circle(final, (lowaval, ylowaval), 1, (0,255,0), thickness=10, lineType=8, shift=0)

					#DO NOT DELETE essential string
					#print("I chose to sat there -Nira 1/21/18")
					#nonessential strings
					#print(hiya, hiyaval)
					#print(lowa, lowaval)
					#print(yhiyaval, ylowaval)

					#displays the images
					cv2.imshow('Start', im)
					cv2.imshow('Middle', intermed)
					cv2.imshow('Finish', final)
	#kills the windows when a key is pressed
	cv2.waitKey(0)
	cv2.destroyAllWindows()
def box_vision():
	global box_offset
	global box_senddistance
	CLOSE_AREA  = 233452.5
	print("box_vision started")
	#yellow color ranges
	lower_yellow = np.array([27, 60, 145]) #CHANGE TO 50 (2nd value)
	upper_yellow = np.array([40, 255, 255])
	while True:
		if os.path.isfile("/home/nvidia/latest_camera_pic/pic.jpg"):
			print("Box: image found.")
			#image used in program
			pic_lock.acquire()
			im=cv2.imread("/home/nvidia/latest_camera_pic/pic.jpg")
			print("Box: image loaded.")
			pic_lock.release()
			#changing image colorspace from BmetersGR to HSV
			hsv = cv2.cvtColor(im, cv2.COLOR_BGR2HSV)

			#resizes the images to 1/4 the size
			resize = 1
			hsv = cv2.resize(hsv, (0,0), fx=resize, fy=resize)
			blite = cv2.resize(hsv, (0,0), fx=resize, fy=resize)
			im = cv2.resize(im, (0,0), fx=resize, fy=resize)

			#gets the height, width and channels of the image. (I have no idea what the channels are) 
			height, width, channels = im.shape
			#print ("Height %s of image" %height)
			#print ("Width %s of image" %width)

			#blurs the image
			hsv = cv2.medianBlur (hsv, 5)
			 
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
			if contours != []:
				cnt = contours[y]
				M = cv2.moments(cnt)
				if M['m00'] != 0.0:
					cx = int(M['m10']/M['m00']) #Average x point of contour box
					cy = int(M['m01']/M['m00']) #Average y point of contour box


					final = cv2.drawContours(final, contours[y], -1, (0, 0, 255), 5)

					#distances from centroid to edges of screen
					distance_from_left = cx
					distance_from_right = width - cx
					distance_from_top = cy
					distance_from_bottom = height - cy

					#draws box contour onto hsv and im
					#hsv = cv2.drawContours(hsv, contours[y], -1, (0, 0, 255), 5)
					im = cv2.drawContours(im, contours[y], -1, (0, 0, 255), 5)

					#finds height, width, and other of contour
					oneBox = False
					x,y,w,h = cv2.boundingRect(contours[y])
					cv2.rectangle(im,(x,y),(x+w,y+h),(0,255,0),2)
					centroid_x = int(x+(w/2))
					centroid_y = int(y+(h/2))


					#draws the centroid onto final
					cv2.circle(final, (centroid_x, centroid_y), 1, (0,255,0), thickness=1, lineType=8, shift=0)


					#print (w, h)
					#print (w/h)

					if (1.1 > w/h > 0.95):
					    #print ("One box")
					    oneBox = True
					#else:
					    #print("several boxes")

					#draws center point of image
					xCenter = (int(width/2))
					yCenter = (int(height/2))
					cv2.circle(im, (xCenter, centroid_y), 1, (255,0,0), thickness=10, lineType=8, shift=0)

					distance_to_center = xCenter - centroid_x

					#draws the centroid onto im
					cv2.circle(im, (centroid_x, centroid_y), 1, (0,255,0), thickness=10, lineType=8, shift=0)

					ratio = width/w


					#Using Width Ratio (width = image width; w = box width)
					Wfactor = 1.012239583                               #Factor used to find distance
					Wdistance = (width/w) * Wfactor                     #Calculators Distance
					Wdistance *= 0.3048                                 #Converts feet to meter
					info_lock.acquire()
					box_senddistance=Wdistance
					box_offset=distance_to_center
					info_lock.release()
					WdistToCent = (distance_to_center * ((13/12)/w))    #Calculates Distance to center in feet 
					WdistToCent *= 0.3048                               #Converts feet to meter

					Wdegree = (WdistToCent/(2*math.pi*Wdistance))*360   #Finds Degree needed to turn for the box to be in center



					#displays the images
					cv2.imshow('hsv', hsv)
					cv2.imshow('blite', blite)
					cv2.imshow('final', final)
					cv2.imshow('im', im)
					cv2.waitKey(1)
	#kills the windows when a key is pressed
	cv2.waitKey(0)
	cv2.destroyAllWindows()
start=time.time()
t1=threading.Thread(target=comms)
t2=threading.Thread(target=camera_to_image)
t3=threading.Thread(target=tape_vision)
t4=threading.Thread(target=box_vision)
t1.daemon=True
t2.daemon=True
t3.daemon=True
t4.daemon=True
t1.start()
t2.start()
t3.start()
t4.start()
t1.join()
t2.join()
t3.join()
t4.join()
