# AndroidWcfImageUpload
This application is a sample which shows how to upload images from android phone to Wcf server

It contains two part 
1- Android application which is take a picture from camera and sent it to wcf server as a json data
(it is used Gson to convert to Photo class to json object)
2-wcf service which is get the images from the android phone as a json object  and
save this image to the folder named LoadedPhotos in wcf service.


