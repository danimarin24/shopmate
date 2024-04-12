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
    public class SettingController : ControllerBase
    {
        private readonly ShopMateContext _context;

        public SettingController(ShopMateContext context)
        {
            _context = context;
        }

        // GET: api/Setting
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Setting>>> GetSettings()
        {
            return await _context.Settings.ToListAsync();
        }

        // GET: api/Setting/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Setting>> GetSetting(uint id)
        {
            var setting = await _context.Settings.FindAsync(id);

            if (setting == null)
            {
                return NotFound();
            }

            return setting;
        }

        // PUT: api/Setting/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutSetting(uint id, Setting setting)
        {
            if (id != setting.SettingId)
            {
                return BadRequest();
            }

            _context.Entry(setting).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SettingExists(id))
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

        // POST: api/Setting
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Setting>> PostSetting(Setting setting)
        {
            _context.Settings.Add(setting);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetSetting", new { id = setting.SettingId }, setting);
        }

        // DELETE: api/Setting/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSetting(uint id)
        {
            var setting = await _context.Settings.FindAsync(id);
            if (setting == null)
            {
                return NotFound();
            }

            _context.Settings.Remove(setting);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool SettingExists(uint id)
        {
            return _context.Settings.Any(e => e.SettingId == id);
        }
    }
}
