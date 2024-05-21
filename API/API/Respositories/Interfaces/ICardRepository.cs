using API.DTOs;
using API.Model;

namespace API.Respositories.Interfaces;

public interface ICardRepository
{
    Task<CardDto> GetByIdAsync(uint cardId);
    Task<IEnumerable<CardDto>> GetCardsByUserId(uint userId);
    Task<CardDto> Update(Card card);
    Task<CardDto> Modify(Card card);
    Task<bool> Delete(uint cardId);
    Task UpdateAsync(Card card);
}