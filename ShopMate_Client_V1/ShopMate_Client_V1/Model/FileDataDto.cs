using Microsoft.AspNetCore.Http;

namespace ShopMate_Client_V1.Model
{
    internal class FileDataDto
    {
        public IFormFile FileToUpload1 { get; set; }
        public object Data { get; set; }
    }
}