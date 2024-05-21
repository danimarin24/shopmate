using Microsoft.AspNetCore.Mvc;
using API.Services;
using API.Model;
using System.Collections.Generic;
using API.DTOs;
using API.Services.Interfaces;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CardController : ControllerBase
    {
        private readonly ICardService _cardService;
        private readonly IUserService _userService;

        public CardController(ICardService cardService, IUserService userService)
        {
            _cardService = cardService;
            _userService = userService;
        }

        [HttpGet("{cardId}/members")]
        public async Task<IActionResult> GetMembersByCard(int cardId)
        {
            var members = await _userService.GetMembersByCardId(cardId);
            if (members == null)
                return NotFound();
            return Ok(members);
        }
        
        [HttpPost("/api/Board/{boardId}/card")]
        public async Task<IActionResult> AddCardToBoard(uint boardId, [FromBody] Card card)
        {
            var result = await _cardService.AddCardToBoard(boardId, card);
            if (result == null)
            {
                return BadRequest("Unable to add card to board");
            }
            return Ok(result);
        }
        
        /*
         * [HttpPost("{cardId}/Share")]
           public async Task<IActionResult> ShareCard(uint cardId, [FromBody] Card card)
           {
               var result = await _cardService.ShareCard(cardId, card);
               if (!result)
               {
                   return BadRequest("Unable to share card");
               }
               return Ok();
           }
         */
        
        [HttpGet("users/{userId}/cards")]
        public async Task<IActionResult> GetCardsByUserId(uint userId)
        {
            var cards = await _cardService.GetCardsByUserId(userId);
            if (cards == null)
                return NotFound();
            return Ok(cards);
        }


        [HttpGet("{cardId}")]
        public async Task<IActionResult> GetCardById(uint cardId)
        {
            var card = await _cardService.GetCardById(cardId);
            if (card == null)
                return NotFound();
            return Ok(card);
        }

        [HttpPut("{cardId}")]
        public async Task<IActionResult> UpdateCard(int cardId, [FromBody] Card card)
        {
            if (cardId != card.CardId)
            {
                return BadRequest("Card ID mismatch");
            }

            var updatedCard = await _cardService.UpdateCard(card);
            if (updatedCard == null)
            {
                return NotFound();
            }

            return NoContent();
        }

        [HttpDelete("{cardId}")]
        public async Task<IActionResult> DeleteCard(uint cardId)
        {
            var result = await _cardService.DeleteCard(cardId);
            if (!result)
            {
                return NotFound();
            }

            return NoContent();
        }
    }
}
