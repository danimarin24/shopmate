using API.DTOs;
using API.Model;

namespace API.Respositories.Interfaces;

public interface ICardRepository
{
    Task<CardDto> GetByIdAsync(uint cardId);
    Task<CardDto> Update(Card card);
    Task<CardDto> Modify(Card card);
    Task<bool> Delete(uint cardId);
    Task UpdateAsync(Card card);
}