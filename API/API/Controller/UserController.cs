using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using API.Context;
using API.Model;

namespace API.Controller
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly ShopMateContext _context;

        public UserController(ShopMateContext context)
        {
            _context = context;
        }
        
        // GET: api/User
        [HttpGet]
        public async Task<ActionResult<IEnumerable<User>>> GetUsers()
        {
            return await _context.Users.ToListAsync();
        }

        // GET: api/User/5
        [HttpGet("{id:int}")]
        public async Task<ActionResult<User>> GetUser(uint id)
        {
            var user = await _context.Users.FindAsync(id);

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }
        
        // GET: api/User/danimarin24
        [HttpGet("{username}")]
        public async Task<ActionResult<User>> GetUser(string username)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Username.Equals(username));

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }
        
        // GET: api/User/google/53412efadsfa582134e5sfads87f5dasf4asdf8asdfasdf5asf5a4sdf5asdf5asdf52c4xz81234r6fardfa2f3ad5sfa6sdfas9fv5c8127425daf4as2xc.....
        [HttpGet("google/{token}")]
        public async Task<ActionResult<User>> GetGoogleUser(string token)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.GoogleToken.Equals(token));

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }
        
        // GET: api/User/facebook/53412efadsfa582134e5sfads87f5dasf4asdf8asdfasdf5asf5a4sdf5asdf5asdf52c4xz81234r6fardfa2f3ad5sfa6sdfas9fv5c8127425daf4as2xc.....
        [HttpGet("facebook/{token}")]
        public async Task<ActionResult<User>> GetFacebookUser(string token)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.FacebookToken.Equals(token));

            if (user == null)
            {
                return NotFound();
            }

            return user;
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
    }
}
