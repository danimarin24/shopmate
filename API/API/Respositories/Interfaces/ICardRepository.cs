using API.DTOs;
using API.Model;
using API.Model.Extra;

namespace API.Respositories.Interfaces;

public interface ICardRepository
{
    Task<CardDto> GetByIdAsync(uint cardId);
    Task<IEnumerable<CardDto>> GetCardsByUserId(uint userId);
    Task<IEnumerable<CardDto>> GetCardsContainsName(string name);
    Task<GenerateShareCardLinkResponse> GenerateShareCardLink(GenerateShareCardLinkRequest cardLinkRequest);
    Task<ValidateShareCardLinkResponse> ValidateShareCardLink(ValidateShareCardLinkRequest validateCardLinkRequest);
    Task<CardDto> Update(Card card);
    Task<CardDto> Modify(Card card);
    Task<bool> Delete(uint cardId);
    Task UpdateAsync(Card card);
}