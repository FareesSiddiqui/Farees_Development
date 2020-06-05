import cv2
import numpy as np
from sklearn.externals import joblib
from sklearn import datasets
from skimage.feature import hog
from sklearn.svm import LinearSVC


board = cv2.imread("board.jpg")

gray = cv2.cvtColor(board, cv2.COLOR_BGR2GRAY)

blur = cv2.GaussianBlur(gray, (5,5), 0)

thresh = cv2.adaptiveThreshold(blur, 255, 1, 1, 11, 2)

contours,h = cv2.findContours(thresh,cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)

max_area = 0
c = 0
for i in contours:
        area = cv2.contourArea(i)
        if area > 1000:
                if area > max_area:
                    max_area = area
                    best_cnt = i
                    board = cv2.drawContours(board, contours, c, (0, 255, 0), 3)
        c+=1


mask = np.zeros((gray.shape),np.uint8)
cv2.drawContours(mask,[best_cnt],0,255,-1)
cv2.drawContours(mask,[best_cnt],0,0,2)
# cv2.imshow("mask", mask)

out = np.zeros_like(gray)
out[mask == 255] = gray[mask == 255]
# cv2.imshow("New image", out)

blur = cv2.GaussianBlur(out, (5,5), 0)
# cv2.imshow("blur1", blur)

thresh = cv2.adaptiveThreshold(blur, 255, 1, 1, 11, 2)
# cv2.imshow("thresh1", thresh)


contours1,h = cv2.findContours(thresh, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

c = 0
for i in contours1:
        area = cv2.contourArea(i)
        if area > 1000/2:
            cv2.drawContours(board, contours1, c, (0, 255, 0), 3)
        c+=1


cv2.imshow("Final Image", board)
cv2.waitKey(0)
cv2.destroyAllWindows()
