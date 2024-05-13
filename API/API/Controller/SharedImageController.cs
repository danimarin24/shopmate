using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SharedImageController : ControllerBase
    {
        private readonly string _pathServer = "wwwroot/api/images/";
        private readonly string _pathClean = "/api/images/";
        private string _pathImatge = "user/";
        private string _categoryUrl = "user/";


        private readonly string _googleClientId =
            "544701638538-ccp0cp41t4r10ofpl8biku4ckh457hm8.apps.googleusercontent.com";


        // POST: api/SharedImage
        [HttpPost]
        public async Task<ActionResult> PostSharedImage(IFormFile file, string categoryImage)
        {
            try
            {
                if (file == null || file.Length == 0)
                {
                    return BadRequest("No se proporcionó una imagen.");
                }

                // Generar un nombre de archivo único
                string fileName = Guid.NewGuid().ToString() + Path.GetExtension(file.FileName);

                switch (categoryImage.ToLower())
                {
                    case "user":
                        _categoryUrl = "us_";
                        _pathImatge = "user/";
                        break;
                    case "category":
                        _categoryUrl = "cat_";
                        _pathImatge = "category/";
                        break;
                    case "ticket":
                        _categoryUrl = "tick_";
                        _pathImatge = "ticket/";
                        break;
                    case "board":
                        _categoryUrl = "board_";
                        _pathImatge = "board/";
                        break;

                    default:
                        return BadRequest("No se proporcionó una categoria");
                        break;
                }
                    

                // Guardar la imagen en la carpeta local
                string filePath = Path.Combine(_pathClean,_pathImatge, fileName);
                using (var stream = new FileStream(filePath, FileMode.Create))
                {
                    await file.CopyToAsync(stream);
                }

                // Generar un hash único para imageUrl usando SHA256
                string imageUrlHash = GetSha256Hash(fileName);

                // Construir la URL final
                string finalUrl = $"{_pathImatge}{imageUrlHash}";

                // Retornar la URL final
                return Ok(new { finalUrl });
            }
            catch (Exception ex)
            {
                return StatusCode(StatusCodes.Status500InternalServerError, $"Error interno del servidor: {ex.Message}");
            }
        }

        // Generar un hash SHA256 único
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
