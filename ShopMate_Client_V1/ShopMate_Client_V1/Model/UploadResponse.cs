using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopMate_Client_V1.Model
{
    public class UploadResponse
    {
        [JsonProperty("finalUrl")]
        public string FinalUrl { get; set; }
    }
}
