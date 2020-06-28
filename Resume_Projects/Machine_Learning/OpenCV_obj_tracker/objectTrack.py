import cv2


# define a video capture object
cap = cv2.VideoCapture(0)

tracker = cv2.TrackerMOSSE_create()
success, img = cap.read()

boundingBox = cv2.selectROI("Tracking", img, False)

tracker.init(img, boundingBox)

def drawBox(img, boundingBox):
    #bounding box is a tuple with 4 values (x, y, width, height)

    x, y, w, h = int(boundingBox[0]), int(boundingBox[1]), int(boundingBox[2]), int(boundingBox[3])
    cv2.rectangle(img, (x, y), ((x+w), (y+h)), (255, 255, 0), 3, 1)
    cv2.putText(img, "Tracking", (75,70), cv2.FONT_HERSHEY_SIMPLEX, 0.7,(0,255,163), 2)


while(True):
    timer = cv2.getTickCount()
    success, img = cap.read()

    success,boundingBox = tracker.update(img)

    if success:
        drawBox(img, boundingBox)
    else:
        cv2.putText(img, "lost", (75,70), cv2.FONT_HERSHEY_SIMPLEX, 0.7,(255,0,163), 2)


    fps = cv2.getTickFrequency()/(cv2.getTickCount()-timer)
    cv2.putText(img, str(int(fps)), (75,50), cv2.FONT_HERSHEY_SIMPLEX, 0.7,(255,0,163), 2)
    cv2.imshow("Tracking", img)

    if cv2.waitKey(1) & 0xff==ord('q'):
        break;
