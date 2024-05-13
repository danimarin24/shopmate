using Microsoft.AspNetCore.Mvc;
using System.Drawing;
using System.Security.Cryptography;
using System.Text;

namespace API.Model
{
    public partial class SharedImage
    {

        public IFormFile Image { get; set; }
        public uint ImageUrl { get; set; }
        public string CategoryImage { get; set; }
        public string FinalUrl { get; set; }

       
    }
}