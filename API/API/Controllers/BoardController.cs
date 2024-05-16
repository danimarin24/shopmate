using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using API.Context;
using API.Model;
using API.Model.DTO;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BoardController : ControllerBase
    {
        private readonly ShopMateContext _context;

        public BoardController(ShopMateContext context)
        {
            _context = context;
        }

        // GET: api/Board
        [HttpGet]
        public async Task<ActionResult<IEnumerable<BoardDTO>>> GetBoards()
        {
            var boards = await _context.Boards.ToListAsync();
            return boards.Select(b => new BoardDTO() {
                BoardId = b.BoardId,
                OwnerId = b.OwnerId,
                Title = b.Title
            }).ToList();
        }
        
        // GET: api/Board/user
        [HttpGet("/user")]
        public async Task<ActionResult<IEnumerable<BoardDTO>>> GetBoards(int id)
        {
            var boards = await _context.Boards.Where(b => b.OwnerId == id).ToListAsync();
            return boards.Select(b => new BoardDTO() {
                BoardId = b.BoardId,
                OwnerId = b.OwnerId,
                Title = b.Title
            }).ToList();
        }

        // GET: api/Board/5
        [HttpGet("{id}")]
        public async Task<ActionResult<BoardDTO>> GetBoard(uint id)
        {
            var board = await _context.Boards.FindAsync(id);

            if (board == null)
            {
                return NotFound();
            }

            return new BoardDTO() {
                BoardId = board.BoardId,
                OwnerId = board.OwnerId,
                Title = board.Title
            };
        }

        // PUT: api/Board/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutBoard(uint id, Board boardDto)
        {
            if (id != boardDto.BoardId)
            {
                return BadRequest();
            }

            _context.Entry(boardDto).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!BoardExists(id))
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

        // POST: api/Board
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<BoardDTO>> PostBoard(Board boardDto)
        {
            _context.Boards.Add(
                new Board() {
                    BoardId = boardDto.BoardId,
                    OwnerId = boardDto.OwnerId,
                    Title = boardDto.Title
                }
                );
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetBoard", new { id = boardDto.BoardId }, boardDto);
        }

        // DELETE: api/Board/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteBoard(uint id)
        {
            var board = await _context.Boards.FindAsync(id);
            if (board == null)
            {
                return NotFound();
            }

            _context.Boards.Remove(board);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool BoardExists(uint id)
        {
            return _context.Boards.Any(e => e.BoardId == id);
        }
    }
}
