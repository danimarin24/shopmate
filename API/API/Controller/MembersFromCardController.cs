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
    public class MembersFromCardController : ControllerBase
    {
        private readonly ShopMateContext _context;

        public MembersFromCardController(ShopMateContext context)
        {
            _context = context;
        }

        // GET: api/MembersFromCard
        [HttpGet]
        public async Task<ActionResult<IEnumerable<MembersFromCard>>> GetMembersFromCards()
        {
            return await _context.MembersFromCards.ToListAsync();
        }

        // GET: api/MembersFromCard/5
        [HttpGet("{id}")]
        public async Task<ActionResult<MembersFromCard>> GetMembersFromCard(uint id)
        {
            var membersFromCard = await _context.MembersFromCards.FindAsync(id);

            if (membersFromCard == null)
            {
                return NotFound();
            }

            return membersFromCard;
        }

        // PUT: api/MembersFromCard/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutMembersFromCard(uint id, MembersFromCard membersFromCard)
        {
            if (id != membersFromCard.CardId)
            {
                return BadRequest();
            }

            _context.Entry(membersFromCard).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MembersFromCardExists(id))
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

        // POST: api/MembersFromCard
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<MembersFromCard>> PostMembersFromCard(MembersFromCard membersFromCard)
        {
            _context.MembersFromCards.Add(membersFromCard);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetMembersFromCard", new { id = membersFromCard.CardId }, membersFromCard);
        }

        // DELETE: api/MembersFromCard/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteMembersFromCard(uint id)
        {
            var membersFromCard = await _context.MembersFromCards.FindAsync(id);
            if (membersFromCard == null)
            {
                return NotFound();
            }

            _context.MembersFromCards.Remove(membersFromCard);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool MembersFromCardExists(uint id)
        {
            return _context.MembersFromCards.Any(e => e.CardId == id);
        }
    }
}
