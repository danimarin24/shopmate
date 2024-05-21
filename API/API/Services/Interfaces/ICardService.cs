using System.Collections.Generic;
using API.DTOs;
using API.Model;

namespace API.Services.Interfaces{
    public interface ICardService
    {
        Task<IEnumerable<CardDto>> GetCardsByBoardId(uint boardId);
        Task<CardDto> GetCardById(uint cardId);
        Task<CardDto> AddCardToBoard(uint boardId, Card card);
        //Task<bool> ShareCard(uint cardId, Card card);
        Task<CardDto> UpdateCard(Card card);
        Task<bool> DeleteCard(uint cardId);
    }
}