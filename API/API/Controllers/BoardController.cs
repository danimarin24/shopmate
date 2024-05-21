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
        public IActionResult GetBoardsByUser(int userId)
        {
            var boards = _boardService.GetBoardsByUserId(userId);
            if (boards == null)
                return NotFound();
            return Ok(boards);
        }
        
        [HttpGet("{boardId}/cards")]
        public IActionResult GetCardsByBoard(uint boardId)
        {
            var cards = _cardService.GetCardsByBoardId(boardId);
            if (cards == null)
                return NotFound();
            return Ok(cards);
        }

        [HttpPost]
        public IActionResult CreateBoard([FromBody] Board board)
        {
            var createdBoard = _boardService.AddBoard(board);
            return CreatedAtAction(nameof(GetBoardsByUser), new { userId = createdBoard.OwnerId }, createdBoard);
        }

        [HttpPut("{boardId}")]
        public IActionResult UpdateBoard(int boardId, [FromBody] Board board)
        {
            if (boardId != board.BoardId)
            {
                return BadRequest("Board ID mismatch");
            }

            var updatedBoard = _boardService.UpdateBoard(board);
            if (updatedBoard == null)
            {
                return NotFound();
            }

            return NoContent();
        }

        [HttpDelete("{boardId}")]
        public IActionResult DeleteBoard(int boardId)
        {
            var result = _boardService.DeleteBoard(boardId);
            if (!result)
            {
                return NotFound();
            }

            return NoContent();
        }
    }
}