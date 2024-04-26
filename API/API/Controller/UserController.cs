using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using API.Context;
using API.Model;
using API.Model.ViewModel;
using System.Net.Http;
using Newtonsoft.Json.Linq;


namespace API.Controller
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly ShopMateContext _context;
        private readonly string _pathServer = "/var/www/html";
        private readonly string _pathImatge = "api/user/images";

        private readonly string _googleClientId =
            "544701638538-ccp0cp41t4r10ofpl8biku4ckh457hm8.apps.googleusercontent.com";

        public UserController(ShopMateContext context)
        {
            _context = context;
        }

        // GET: api/User
        [HttpGet]
        public async Task<ActionResult<IEnumerable<User>>> GetUsers()
        {
            return await _context.Users.Include(u => u.Setting).Include(u => u.Stat).ToListAsync();
        }

        // GET: api/User/5
        [HttpGet("{id:int}")]
        public async Task<ActionResult<User>> GetUser(uint id)
        {
            var user = await _context.Users
                .Include(u => u.Setting) // Carga la entidad Setting relacionada
                .Include(u => u.Stat) // Carga la entidad Stat relacionada
                .FirstOrDefaultAsync(u => u.UserId == id);

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

        // GET: api/User/checkusername/danimarin24
        [HttpGet("checkusername/{username}")]
        public async Task<ActionResult<User>> GetUserByUsername(string username)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Username.Equals(username));

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

        // GET: api/User/checkemail/dani4marin@gmail.com
        [HttpGet("checkemail/{email}")]
        public async Task<ActionResult<User>> GetUserByEmail(string email)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Email.Equals(email));

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }
        
        // GET: api/User/checkgooglesub/1231542341342413
        [HttpGet("checkgooglesub/{sub}")]
        public async Task<ActionResult<User>> GetUserByGoogleSub(string sub)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.GoogleToken != null && u.GoogleToken.Equals(sub));

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

        // GET: api/User/generate/dani
        [HttpGet("generate/{name}")]
        public async Task<ActionResult<string>> GetGenerateUniqueUsername(string name)
        {
            string username = name.Replace(" ", String.Empty);
            username = username.Replace(".", String.Empty);
            username = username.ToLower();

            Random random = new Random();
            while (true)
            {
                string candidateUsername = username + random.Next(100000, 999999);

                var user = await _context.Users.FirstOrDefaultAsync(u => u.Username.Equals(candidateUsername));

                if (user == null)
                {
                    return candidateUsername;
                }
            }
        }



        // PUT: api/User/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUser(uint id, User user)
        {
            if (id != user.UserId)
            {
                return BadRequest();
            }

            _context.Entry(user).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/User
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<User>> PostUser(User user)
        {
            _context.Users.Add(user);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetUser", new { id = user.UserId }, user);
        }

        // POST: api/User/profileImage
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("profileImage")]
        public async Task<ActionResult<User>> PostUserImage([FromForm] UserViewModel model)
        {
            if (model.ProfileImage == null)
            {
                return BadRequest("No se proporcionó una imagen.");
            }

            try
            {
                var file = model.ProfileImage;
                var fileSize = file.Length;

                if (fileSize > 0)
                {
                    var fileName = Path.GetFileName(file.FileName);
                    var filePath = Path.Combine(_pathServer, _pathImatge, fileName);

                    await using (var stream = new FileStream(filePath, FileMode.Create))
                    {
                        await file.CopyToAsync(stream);
                    }

                    var profileImageUrl = $"{_pathImatge}/{fileName}";

                    var user = new User
                    {
                        Name = model.Name,
                        Username = model.Username,
                        Password = model.Password,
                        Email = model.Email,
                        PhoneNumber = model.PhoneNumber,
                        ProfileImage = profileImageUrl,
                        RegisterDate = model.RegisterDate,
                        LastConnection = model.LastConnection,
                        SettingId = model.SettingId,
                        StatId = model.StatId
                    };

                    _context.Users.Add(user);
                    await _context.SaveChangesAsync();

                    return CreatedAtAction("GetUser", new { id = user.UserId }, user);
                }
                else
                {
                    return BadRequest("La imagen está vacía.");
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error interno del servidor: {ex.Message}");
            }
        }


        // DELETE: api/User/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUser(uint id)
        {
            var user = await _context.Users.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }

            _context.Users.Remove(user);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool UserExists(uint id)
        {
            return _context.Users.Any(e => e.UserId == id);
        }

        [HttpPost("validate-google-token")]
        public async Task<ActionResult<String>> ValidateGoogleToken([FromBody] string idTokenString)
        {
            using (var client = new HttpClient())
            {
                var response =
                    await client.GetAsync($"https://oauth2.googleapis.com/tokeninfo?id_token={idTokenString}");
                if (!response.IsSuccessStatusCode)
                {
                    return BadRequest("El token no es correcto");
                }

                var jsonString = await response.Content.ReadAsStringAsync();
                var tokenInfo = JObject.Parse(jsonString);

                // Verificar que el token provenga de tu cliente de Google
                if (!tokenInfo["aud"].ToString().Equals(_googleClientId))
                {
                    return BadRequest($"El token no proviene de mi aplicación, {tokenInfo["aud"]}");
                }

                // Verificar que el token no haya caducado
                var expirationTimeSeconds = long.Parse(tokenInfo["exp"].ToString());
                if (expirationTimeSeconds <= DateTimeOffset.UtcNow.ToUnixTimeSeconds())
                {
                    return BadRequest("El token ha caducado");
                }

                // Token válido
                return tokenInfo["sub"].ToString();
            }
        }
    }

}
