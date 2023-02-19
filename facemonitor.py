
import cv2 
import numpy as np 

# Capture camera frames from the front camera
camera = cv2.VideoCapture(0) 

# Load the owner's face image and store it in a numpy array
owner_image = cv2.imread('owner_image.jpg') 
owner_image = cv2.cvtColor(owner_image, cv2.COLOR_BGR2GRAY) 

# Create a haar cascade for face detection
face_detector = cv2.CascadeClassifier('haarcascade_frontalface_default.xml') 

while(True): 

	# Capture frame-by-frame 
	ret, frame = camera.read() 
	frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
	
	# Detect faces in the frame 
	faces = face_detector.detectMultiScale(frame, 1.3, 5) 

	for (x,y,w,h) in faces: 
		frame_face = frame[y:y+h, x:x+w] 

		# Resize the face to match the size of the owner's face
		frame_face = cv2.resize(frame_face, (owner_image.shape[1], owner_image.shape[0])) 

		# Compare the faces 
		result = np.subtract(frame_face, owner_image) 
		result = np.sum(np.abs(result)) 

		# If the faces don't match, restrict the user to the calling app only
		if result > 15000: 
			print("Unauthorized user detected. Restricting user to the calling app only...") 

	# Break the loop 
	if cv2.waitKey(1) & 0xFF == ord('q'): 
		break 

# Release the camera and close all windows 
camera.release() 
cv2.destroyAllWindows()
