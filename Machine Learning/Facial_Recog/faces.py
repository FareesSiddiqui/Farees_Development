import numpy as np
import cv2
import pickle

face_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_frontalface_alt2.xml') #try profile face

eye_casacde = cv2.CascadeClassifier("cascades/data/haarcascade_eye.xml")

recognizer = cv2.face.LBPHFaceRecognizer_create()

recognizer.read("trainner.yml")

labels = {}

with open("labels.pickle", "rb") as f:
    og_labels = pickle.load(f)
    labels = {v:k for k,v in og_labels.items()}



cap = cv2.VideoCapture(0)

while(True):
    #Capture Frame by Frame
    ret, frame = cap.read()

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.5, minNeighbors=2)

    for (x, y, w, h) in faces:
        # print(x,y,w,h)
        roi_gray = gray[y:y+h, x:x+w] #roi = region of interest for gray scale
        roi_color = frame[y:y+h, x:x+w] #roi = region of interest for color frame

        id_, conf = recognizer.predict(roi_gray)
        if conf >= 45:
            print(id_)
            print(labels[id_])
            font = cv2.FONT_HERSHEY_SIMPLEX
            name = labels[id_]
            color = (0, 255, 0)
            stroke = 2
            cv2.putText(frame, name, (x,y), font, 1, color, stroke, cv2.LINE_AA)

        img_item = "my-img.png"
        cv2.imwrite(img_item, roi_gray)

        color = (255, 0, 0) # BGR Blue Green Red not RGB 0-255

        stroke = 1

        end_cord_x = x + w

        end_cord_y = y + h

        cv2.rectangle(frame, (x, y), (end_cord_x, end_cord_y), color, stroke)

        eyes = eye_casacde.detectMultiScale(roi_gray)

        # for (ex, ey, ew, eh) in eyes:
        #     cv2.rectangle(roi_color, (ex, ey), (ex+ew, ey+eh), (0, 0, 255), 2)
        #TODO: Adjust algorithm to detect using eyes as well

    #Display the resulting Frame
    cv2.imshow('Facial Recognition', frame)

    if cv2.waitKey(20) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
