
#def average(numbers):
#    return float(sum(numbers)) / max(len(numbers), 1)


lower_yellow = np.array([20, 190, 20])
upper_yellow = np.array([30, 255, 255])
    
#image used in program

im = cv2.imread('C:/Users/GravitechX/Documents/GitHub/gx-frc-18/OpenCV/lit_pictures_7.jpg', 1)
hsv = cv2.cvtColor(im, cv2.COLOR_BGR2HSV)

hsv = cv2.resize(hsv, (0,0), fx=0.25, fy=0.25)

the_window_just_for_nira = hsv
#hsv = cv2.blur (hsv, (50, 50))
hsv = cv2.medianBlur (hsv, 5)
mask = cv2.inRange(hsv, lower_yellow, upper_yellow)
mask = cv2.resize(mask, (0,0), fx=0.8, fy=0.8)

ret,thresh = cv2.threshold(mask,255,255,255)
im2, contours, hierarchy = cv2.findContours(thresh,cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE)

im2 = cv2.drawContours(im2, contours, 3, (0,255,0), 3)

#this is necessary for the instantiation of the vision DO NOT DELETE OR ELSE EVERYTHING WILL BREAK
print("I chose to sat there - nira 1/29/18")
#cv2.boundingRect(
#darkIm = cv2.cvtColor(hsv, cv2.COLOR_BGR2GRAY)
#retval, threshold = cv2.threshold(darkIm, 50, 100, cv2.THRESH_BINARY);
#hsvHist = average(cv2.calcHist([darkIm], [0], None, [256], [0,256]))


#retval, threshold = cv2.threshold(darkIm, (hsvHist-1000)/10, (hsvHist-500)/10, cv2.THRESH_BINARY)
#Black and White Threshold using average lighting (does not work yet)
#https://pythonprogramming.net/thresholding-image-analysis-python-opencv-tutorial/  <-- threshold info

#print (hsvHist)

#displays images
cv2.imshow('image', hsv)
cv2.imshow('mask', mask)
cv2.imshow('the_window_just_for_nira', the_window_just_for_nira)
cv2.imshow('im2', im2)
#cv2.imshow('bw', darkIm)
#cv2.imshow('threshold', threshold)


cv2.waitKey(0)
cv2.destroyAllWindows()
