using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace WcfAndroidPhotoServis
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWcfAndroidImageService" in both code and config file together.
    [ServiceContract]
    public interface IWcfAndroidImageService
    {
        [OperationContract]
        [WebInvoke(Method = "POST",
                RequestFormat = WebMessageFormat.Json,
                ResponseFormat = WebMessageFormat.Json,
            // BodyStyle = WebMessageBodyStyle.Wrapped,
                UriTemplate = "LoadPhoto")]
        string LoadPhoto(Photo photo);
    }
}
