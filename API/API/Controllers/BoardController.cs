using Microsoft.AspNetCore.Mvc;
using API.Services;
using API.DTOs;
using API.Model;
using System.Collections.Generic;
using API.Services.Interfaces;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BoardController : ControllerBase
    {
        private readonly IBoardService _boardService;
        private readonly ICardService _cardService;

        public BoardController(IBoardService boardService, ICardService cardService)
        {
            _boardService = boardService;
            _cardService = cardService;
        }

        [HttpGet("users/{userId}/boards")]
        public async Task<IActionResult> GetBoardsByUser(uint userId)
        {
            var boards = await _boardService.GetBoardsByUserId(userId);
            if (boards == null)
                return NotFound();
            return Ok(boards);
        }
        
        [HttpGet("{boardId}/cards")]
        public async Task<IActionResult> GetCardsByBoard(uint boardId)
        {
            var cards = await _cardService.GetCardsByBoardId(boardId);
            if (cards == null)
                return NotFound();
            return Ok(cards);
        }

        [HttpPost]
        public async Task<IActionResult> CreateBoard([FromBody] Board board)
        {
            var createdBoard = await _boardService.AddBoard(board);
            return CreatedAtAction(nameof(GetBoardsByUser), new { userId = createdBoard.OwnerId }, createdBoard);
        }

        [HttpPut("{boardId}")]
        public async Task<IActionResult> UpdateBoard(int boardId, [FromBody] Board board)
        {
            if (boardId != board.BoardId)
            {
                return BadRequest("Board ID mismatch");
            }

            var updatedBoard = await _boardService.UpdateBoard(board);
            if (updatedBoard == null)
            {
                return NotFound();
            }

            return NoContent();
        }

        [HttpDelete("{boardId}")]
        public async Task<IActionResult> DeleteBoard(uint boardId)
        {
            var result = await _boardService.DeleteBoard(boardId);
            if (!result)
            {
                return NotFound();
            }

            return NoContent();
        }
    }
}