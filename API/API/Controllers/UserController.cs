using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using API.Context;
using API.Model;
using API.Model.ViewModel;
using System.Net.Http;
using Newtonsoft.Json.Linq;
using API.DTOs;


namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly ShopMateContext _context;
        private readonly string _pathServer = "wwwroot/api/images/";
        private readonly string _pathClean = "/api/images/";
        private readonly string _pathImatge = "user/";

        private readonly string _googleClientId =
            "544701638538-ccp0cp41t4r10ofpl8biku4ckh457hm8.apps.googleusercontent.com";

        public UserController(ShopMateContext context)
        {
            _context = context;
        }

        // GET: api/User
        [HttpGet]
        public async Task<ActionResult<IEnumerable<UserDto>>> GetUsers()
        {
            var users = await _context.Users.ToListAsync();
            return users.Select(u => new UserDto {
                UserId = u.UserId,
                Username = u.Username,
                Email = u.Email,
                FacebookToken = u.FacebookToken,
                GoogleToken = u.GoogleToken,
                LastConnection = u.LastConnection,
                Name = u.Name,
                Password = u.Password,
                PhoneNumber = u.PhoneNumber,
                ProfileImage = u.ProfileImage,
                SettingId = u.SettingId
            }).ToList();
        }

        // GET: api/User/stats/5
        [HttpGet("stats/{id:int}")]
        public async Task<ActionResult<UserStatistic>> GetUserStats(uint id)
        {
            var userStats = await _context.UserStatistics
                .FirstOrDefaultAsync(u => u.UserId == id);

            if (userStats == null)
            {
                return NotFound();
            }

            return userStats;
        }
        
        // GET: api/User/5/followeds
        [HttpGet("{id:int}/followeds")]
        public async Task<ActionResult<IEnumerable<UserDto>>> GetUserFolloweds(uint id)
        {
            var usersFolloweds = await _context.Users
                .Where(u => u.UserId == id)
                .SelectMany(u => u.UserFolloweds)
                .Select(uf => new UserDto(uf))
                .ToListAsync();
            
            if (usersFolloweds == null)
            {
                return NotFound();
            }

            return usersFolloweds;
        }
        
        // GET: api/User/5/followers
        [HttpGet("{id:int}/followers")]
        public async Task<ActionResult<IEnumerable<UserDto>>> GetUserFollowers(uint id)
        {
            var usersFollowers = await _context.Users
                .Where(u => u.UserFolloweds.Any(uf => uf.UserId == id))
                .Select(u => new UserDto(u))
                .ToListAsync();
            
            if (usersFollowers == null)
            {
                return NotFound();
            }

            return usersFollowers;
        }
        
        // GET: api/User/5/action/16
        [HttpGet("{id:int}/isfollowing/{userActionId:int}")]
        public async Task<ActionResult<Boolean>> IsFollowing(uint id, uint userActionId)
        {
            var userActionResponse = new UserActionResponseDto(id, userActionId);
            var user = await _context.Users.Include(u => u.UserFolloweds).FirstOrDefaultAsync(u => u.UserId == id); 
            var userAction = await _context.Users.Include(u => u.UserFolloweds).FirstOrDefaultAsync(u => u.UserId == userActionId); 
            
            var isFollowingUserAction = _context.Users
                .Where(u => u.UserId == id)
                .SelectMany(u => u.UserFolloweds
                    .Where(uf => uf.UserId == userActionId)
                    .Select(uf => new UserDto(uf))
                ).FirstOrDefault();
            
            if (user == null)
            {
                throw new Exception("User not found");
            }
                
            if (userAction == null)
            {
                throw new Exception("UserAction not found");
            }
            
            return !(isFollowingUserAction == null);

        }
        
        // POST: api/User/5/action/16
        [HttpPost("{id:int}/action/{userActionId:int}")]
        public async Task<ActionResult<UserActionResponseDto>> FollowUnfollowNewUser(uint id, uint userActionId)
        {
            var userActionResponse = new UserActionResponseDto(id, userActionId);
            var user = await _context.Users.Include(u => u.UserFolloweds).FirstOrDefaultAsync(u => u.UserId == id); 
            var userAction = await _context.Users.Include(u => u.UserFolloweds).FirstOrDefaultAsync(u => u.UserId == userActionId); 
            
            var isFollowingUserAction = _context.Users
                .Where(u => u.UserId == id)
                .SelectMany(u => u.UserFolloweds
                    .Where(uf => uf.UserId == userActionId)
                    .Select(uf => new UserDto(uf))
                ).FirstOrDefault();
            
            if (user == null)
            {
                throw new Exception("User not found");
            }
                
            if (userAction == null)
            {
                throw new Exception("UserAction not found");
            }

            try
            {
                if (isFollowingUserAction == null)
                {
                    // action to follow
                    user.UserFolloweds.Add(userAction);
                    await _context.SaveChangesAsync();
                    userActionResponse.isActionPerformed = true;
                    userActionResponse.Message = $"{user.Name} now is following {userAction.Name}";
                }
                else
                {
                    // action to unfollow
                    user.UserFolloweds.Remove(userAction);
                    await _context.SaveChangesAsync();
                    userActionResponse.isActionPerformed = true;
                    userActionResponse.Message = $"{user.Name} now is no longer following  {userAction.Name}";
                }
            }
            catch (Exception e)
            {
                userActionResponse.isActionPerformed = true;
                userActionResponse.Message = $"ERROR while {user.Name} has try to follow/unfollow {userAction.Name}";
            }

            return userActionResponse;
        }
        
        // GET: api/User/5
        [HttpGet("{id:int}")]
        public async Task<ActionResult<UserDto>> GetUser(uint id)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.UserId == id);

            if (user == null)
            {
                return NotFound();
            }

            return new UserDto {
                UserId = user.UserId,
                Username = user.Username,
                Email = user.Email,
                FacebookToken = user.FacebookToken,
                GoogleToken = user.GoogleToken,
                LastConnection = user.LastConnection,
                Name = user.Name,
                Password = user.Password,
                PhoneNumber = user.PhoneNumber,
                ProfileImage = user.ProfileImage,
                SettingId = user.SettingId
            };
        }

        // GET: api/User/checkusername/danimarin24
        [HttpGet("checkusername/{username}")]
        public async Task<ActionResult<UserDto>> GetUserByUsername(string username)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Username.Equals(username));

            if (user == null)
            {
                return NotFound();
            }

            return new UserDto {
                UserId = user.UserId,
                Username = user.Username,
                Email = user.Email,
                FacebookToken = user.FacebookToken,
                GoogleToken = user.GoogleToken,
                LastConnection = user.LastConnection,
                Name = user.Name,
                Password = user.Password,
                PhoneNumber = user.PhoneNumber,
                ProfileImage = user.ProfileImage,
                SettingId = user.SettingId
            };
        }
        
        // GET: api/User/filter/username/danimarin24
        [HttpGet("filter/username/{username}")]
        public async Task<ActionResult<IEnumerable<UserDto>>> GetUserFilterByUsername(string username)
        {
            var users = await _context.Users
                .Where(u => u.Username.ToLower().Contains(username.ToLower()))
                .ToListAsync();

            if (users == null)
            {
                return NotFound();
            }

            return users.Select(u => new UserDto {
                UserId = u.UserId,
                Username = u.Username,
                Email = u.Email,
                FacebookToken = u.FacebookToken,
                GoogleToken = u.GoogleToken,
                LastConnection = u.LastConnection,
                Name = u.Name,
                Password = u.Password,
                PhoneNumber = u.PhoneNumber,
                ProfileImage = u.ProfileImage,
                SettingId = u.SettingId
            }).ToList();
        }

        // GET: api/User/checkemail/dani4marin@gmail.com
        [HttpGet("checkemail/{email}")]
        public async Task<ActionResult<UserDto>> GetUserByEmail(string email)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Email.Equals(email));

            if (user == null)
            {
                return NotFound();
            }

            return new UserDto {
                UserId = user.UserId,
                Username = user.Username,
                Email = user.Email,
                FacebookToken = user.FacebookToken,
                GoogleToken = user.GoogleToken,
                LastConnection = user.LastConnection,
                Name = user.Name,
                Password = user.Password,
                PhoneNumber = user.PhoneNumber,
                ProfileImage = user.ProfileImage,
                SettingId = user.SettingId
            };
        }
        
        // GET: api/User/checkgooglesub/1231542341342413
        [HttpGet("checkgooglesub/{sub}")]
        public async Task<ActionResult<UserDto>> GetUserByGoogleSub(string sub)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.GoogleToken != null && u.GoogleToken.Equals(sub));

            if (user == null)
            {
                return NotFound();
            }


            return new UserDto {
                UserId = user.UserId,
                Username = user.Username,
                Email = user.Email,
                FacebookToken = user.FacebookToken,
                GoogleToken = user.GoogleToken,
                LastConnection = user.LastConnection,
                Name = user.Name,
                Password = user.Password,
                PhoneNumber = user.PhoneNumber,
                ProfileImage = user.ProfileImage,
                SettingId = user.SettingId
            };
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
        public async Task<IActionResult> PutUser(uint id, UserDto UserDto)
        {
            if (id != UserDto.UserId)
            {
                return BadRequest();
            }

            _context.Entry(UserDto).State = EntityState.Modified;

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
        public async Task<ActionResult<UserDto>> PostUser(UserDto UserDto)
        {
            using (var transaction = _context.Database.BeginTransaction())
            {
                try
                {
                    var setting = new Setting(DateTime.Now, 1, 0, 1, 0, DateTime.Now);
                    if (UserDto.Password != null)
                    {
                        setting.LastPasswordHash = UserDto.Password;
                    }
                    else if (UserDto.GoogleToken != null)
                    {
                        setting.LastPasswordHash = UserDto.GoogleToken;
                    }
                    else if (UserDto.FacebookToken != null)
                    {
                        setting.LastPasswordHash = UserDto.FacebookToken;
                    }

                    _context.Settings.Add(setting);

                    await _context.SaveChangesAsync();

                    UserDto.SettingId = setting.SettingId;

                    var newUser = new User
                    {
                        UserId = UserDto.UserId,
                        Username = UserDto.Username,
                        Email = UserDto.Email,
                        FacebookToken = UserDto.FacebookToken,
                        GoogleToken = UserDto.GoogleToken,
                        LastConnection = UserDto.LastConnection,
                        Name = UserDto.Name,
                        Password = UserDto.Password,
                        PhoneNumber = UserDto.PhoneNumber,
                        ProfileImage = UserDto.ProfileImage,
                        SettingId = UserDto.SettingId
                    };
                    _context.Users.Add(newUser);
                    await _context.SaveChangesAsync();

                    UserDto.UserId = newUser.UserId;

                    transaction.Commit(); // Confirma la transacción ya que todo ha ido bien
                    return CreatedAtAction("GetUser", new { id = UserDto.UserId }, UserDto);
                }
                catch (Exception)
                {
                    // Si algo falla, revierte los cambios
                    transaction.Rollback();
                    throw; // Lanza la excepción para manejarla en un nivel superior si es necesario
                }
            }
        }

        // POST: api/User/profileImage
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("profileImage")]
        public async Task<ActionResult<UserDto>> PostUserImage([FromForm] UserViewModel userViewModel)
        {
            if (userViewModel.ProfileImage == null)
            {
                return BadRequest("No se proporcionó una imagen.");
            }

            try
            {
                var file = userViewModel.ProfileImage;
                var fileSize = file.Length;

                if (fileSize > 0)
                {
                    var fileName = Path.GetFileName(file.FileName);
                    string imageUrlHash = $"{DateTime.Now.Ticks}{Path.GetExtension(fileName)}";
                    string filePath = Path.Combine(_pathServer, _pathImatge, imageUrlHash);
                    
                    await using (var stream = new FileStream(filePath, FileMode.Create))
                    {
                        await file.CopyToAsync(stream);
                    }

                    // Construir la URL final
                    string finalUrl = $"{_pathClean}{_pathImatge}{imageUrlHash}";
                    
                    
                    using (var transaction = _context.Database.BeginTransaction())
                    {
                        try
                        {
                            var setting = new Setting(DateTime.Now, 1, 0, 1, 0, DateTime.Now);
                            if (userViewModel.Password != null)
                            {
                                setting.LastPasswordHash = userViewModel.Password;
                            }

                            _context.Settings.Add(setting);

                            await _context.SaveChangesAsync();

                            userViewModel.SettingId = setting.SettingId;

                            var user = new User
                            {
                                UserId = 0,
                                Username = userViewModel.Username,
                                Email = userViewModel.Email,
                                LastConnection = userViewModel.LastConnection,
                                Name = userViewModel.Name,
                                Password = userViewModel.Password,
                                PhoneNumber = userViewModel.PhoneNumber,
                                ProfileImage = finalUrl,
                                SettingId = userViewModel.SettingId
                            };

                            _context.Users.Add(user);
                            await _context.SaveChangesAsync();

                            transaction.Commit(); // Confirma la transacción ya que todo ha ido bien
                            return CreatedAtAction("GetUser", new { id = user.UserId }, new UserDto(user));
                        }
                        catch (Exception)
                        {
                            // Si algo falla, revierte los cambios
                            transaction.Rollback();
                            throw; // Lanza la excepción para manejarla en un nivel superior si es necesario
                        }
                    }
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
