using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using WcfAndroidPhotoServis.Helper;

namespace WcfAndroidPhotoServis
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WcfAndroidImageService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WcfAndroidImageService.svc or WcfAndroidImageService.svc.cs at the Solution Explorer and start debugging.
    public class WcfAndroidImageService : IWcfAndroidImageService
    {

        public string LoadPhoto(Photo photo)
        {


            


            //get photofolder path
            string photofolderName = "LoadedPhotos";
            string photopath = "";
            photopath = System.Web.Hosting.HostingEnvironment.MapPath("~/"+photofolderName);
            //convert byte array to image
            Image _photo = ImageHelper.Base64ToImage(photo.photoasBase64);
            photopath = photopath + "/" + photo.photoName;
            //save photo to folder

            _photo.Save(photopath);
            //chech if photo saved correctlly into folder
            bool result = File.Exists(photopath);

            return result.ToString();
        }
    }

    [DataContract]
    public class Photo
    {
        //device id
        [DataMember]
        public string photoasBase64 { get; set; }

        [DataMember]
        public string photoName { get; set; }

    }
}
