# Farees_Development

**Project Status: Work in progress (Usable needs more images)**

This is a facial recognition, it uses the images in the images folder to train a neural network and then tries to recognize the faces it sees in the camera window. This program was made using Pillow and opencv to open the camera and record the video feed that will be used for the facial recognition. The program is complete but it still needs a far larger data set in different types of lighting, distances, angles, and camera qualities to be more reliable and consistent.

To run the program you must first cd into the working directory of this project and run ```python faces-train.py````
This will train the model to recognize between the faces designated in the `images` folder to change the program to detect the faces **you** would like change the navigate to the *images* folder and change the folder names to the name of the person whos photo's will be stored in that folder. For example by default there are a few images pre loaded in the program, so if you navigate to *images* and view the folder named *Farees* it will consist of pictures of me (Farees).

To actually run the program, **make sure you have trained the model by following the above step first to make sure you get the most accurate results possible** and then cd into the projects working directory and run the command ```python faces.py``` to exit the program press the `q` key on your keyboard, clicking the *x* in the program will not work and just reopen the program

To install the required dependencies for this project, download this Project, change directory into the project's directory and run the command ```pip install -r requirements.txt``` it should install three extra dependencies (neat-python, opencv-python, opencv-contrib-python, and Pillow)
