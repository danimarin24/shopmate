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
    public class ItemCardLineController : ControllerBase
    {
        private readonly ShopMateContext _context;

        public ItemCardLineController(ShopMateContext context)
        {
            _context = context;
        }

        // GET: api/ItemCardLine
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ItemCardLine>>> GetItemCardLines()
        {
            return await _context.ItemCardLines.ToListAsync();
        }

        // GET: api/ItemCardLine/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ItemCardLine>> GetItemCardLine(uint id)
        {
            var itemCardLine = await _context.ItemCardLines.FindAsync(id);

            if (itemCardLine == null)
            {
                return NotFound();
            }

            return itemCardLine;
        }

        // PUT: api/ItemCardLine/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutItemCardLine(uint id, ItemCardLine itemCardLine)
        {
            if (id != itemCardLine.ItemCardLineId)
            {
                return BadRequest();
            }

            _context.Entry(itemCardLine).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ItemCardLineExists(id))
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

        // POST: api/ItemCardLine
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<ItemCardLine>> PostItemCardLine(ItemCardLine itemCardLine)
        {
            _context.ItemCardLines.Add(itemCardLine);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetItemCardLine", new { id = itemCardLine.ItemCardLineId }, itemCardLine);
        }

        // DELETE: api/ItemCardLine/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteItemCardLine(uint id)
        {
            var itemCardLine = await _context.ItemCardLines.FindAsync(id);
            if (itemCardLine == null)
            {
                return NotFound();
            }

            _context.ItemCardLines.Remove(itemCardLine);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool ItemCardLineExists(uint id)
        {
            return _context.ItemCardLines.Any(e => e.ItemCardLineId == id);
        }
    }
}
