using System.Collections.Generic;
using API.DTOs;
using API.Model;
using API.Model.Extra;

namespace API.Services.Interfaces{
    public interface ICardService
    {
        Task<IEnumerable<CardDto>> GetCardsByBoardId(uint boardId);
        Task<IEnumerable<CardDto>> GetCardsByUserId(uint userId);
        Task<GenerateShareCardLinkResponse> GenerateShareCardLink(GenerateShareCardLinkRequest cardLinkRequest);
        Task<ValidateShareCardLinkResponse> ValidateShareCardLink(ValidateShareCardLinkRequest validateCardLinkRequest);
        Task<CardDto> GetCardById(uint cardId);
        Task<CardDto> AddCardToBoard(uint boardId, Card card);
        //Task<bool> ShareCard(uint cardId, Card card);
        Task<CardDto> UpdateCard(Card card);
        Task<bool> DeleteCard(uint cardId);
    }
}