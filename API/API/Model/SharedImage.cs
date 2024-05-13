using Microsoft.AspNetCore.Mvc;
using System.Drawing;
using System.Security.Cryptography;
using System.Text;

namespace API.Model
{
    public partial class SharedImage
    {

        public Image image { get; set; }
        public uint ImageUrl { get; set; }
        public string CategoryImage { get; set; }
        public string FinalUrl { get; set; }

        public void GenerateFinalUrl()
        {
            // Generar un hash único para imageUrl usando SHA256
            string imageUrlHash = GetSha256Hash(ImageUrl.ToString());

            // Combinar categoryImage y el hash de imageUrl para crear finalUrl
            FinalUrl = $"{CategoryImage}-us-{imageUrlHash}.jpg";
        }

        private static string GetSha256Hash(string input)
        {
            using (SHA256 sha256Hash = SHA256.Create())
            {
                
                byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(input));

                
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bytes.Length; i++)
                {
                    builder.Append(bytes[i].ToString("x2"));
                }
                return builder.ToString();
            }
        }
    }
}