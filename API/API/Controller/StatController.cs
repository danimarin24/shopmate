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
    public class StatController : ControllerBase
    {
        private readonly ShopMateContext _context;

        public StatController(ShopMateContext context)
        {
            _context = context;
        }

        // GET: api/Stat
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Stat>>> GetStats()
        {
            return await _context.Stats.ToListAsync();
        }

        // GET: api/Stat/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Stat>> GetStat(uint id)
        {
            var stat = await _context.Stats.FindAsync(id);

            if (stat == null)
            {
                return NotFound();
            }

            return stat;
        }

        // PUT: api/Stat/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutStat(uint id, Stat stat)
        {
            if (id != stat.StatId)
            {
                return BadRequest();
            }

            _context.Entry(stat).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!StatExists(id))
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

        // POST: api/Stat
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Stat>> PostStat(Stat stat)
        {
            _context.Stats.Add(stat);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetStat", new { id = stat.StatId }, stat);
        }

        // DELETE: api/Stat/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteStat(uint id)
        {
            var stat = await _context.Stats.FindAsync(id);
            if (stat == null)
            {
                return NotFound();
            }

            _context.Stats.Remove(stat);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool StatExists(uint id)
        {
            return _context.Stats.Any(e => e.StatId == id);
        }
    }
}
