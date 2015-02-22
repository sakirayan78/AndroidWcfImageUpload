# AndroidWcfImageUpload
This application is a sample which shows how to upload images from android phone to Wcf server

It contains <b>two part<b> 
<ul>
<li>
1- <b>Android application</b> which is take a picture from camera and sent it to wcf server as a json data
(it is used Gson to convert to Photo class to json object)
</li>
<li>
2-<b>wcf service</b> which is get the images from the android phone as a json object  and
save this image to the folder named LoadedPhotos in wcf service.
</li>
</ul>

