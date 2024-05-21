using API.Context;
using API.DTOs;
using API.Model;
using API.Respositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace API.Respositories.Implementations;

public class CardRepository : ICardRepository
{
    private readonly ShopMateContext _context;

    public CardRepository(ShopMateContext context)
    {
        _context = context;
    }

    public async Task<CardDto> GetByIdAsync(uint cardId)
    {
        return await _context.Cards
            .Include(c => c.Color)
            .Select(c => new CardDto
            {
                CardId = c.CardId,
                //Name = c.Name,
                IsPublic = c.IsPublic,
                IsTemplate = c.IsTemplate,
                IsArchived = c.IsArchived,
                EstimatedPrice = c.EstimatedPrice,
                ColorId = c.ColorId
            })
            .FirstOrDefaultAsync(c => c.CardId == cardId);
    }
    
    public async Task<IEnumerable<CardDto>> GetCardsByUserId(uint userId)
    {
        return await _context.Cards
            .Include(c => c.Color)
            .Where(c => c.OwnerId == userId)
            .Select(c => new CardDto
            {
                CardId = c.CardId,
                //Name = c.Name,
                IsPublic = c.IsPublic,
                IsTemplate = c.IsTemplate,
                IsArchived = c.IsArchived,
                EstimatedPrice = c.EstimatedPrice,
                ColorId = c.ColorId
            })
            .ToListAsync();
    }



    public async Task<CardDto> Update(Card card)
    {
        var existingCard = await _context.Cards.FindAsync(card.CardId);
        if (existingCard == null)
        {
            return null;
        }
        
        //existingCard.Name = card.Name;
        existingCard.IsPublic = card.IsPublic;
        existingCard.IsTemplate = card.IsTemplate;
        existingCard.IsArchived = card.IsArchived;
        existingCard.EstimatedPrice = card.EstimatedPrice;
        existingCard.ColorId = card.ColorId;

        await UpdateAsync(existingCard);
        return new CardDto
        {
            CardId = existingCard.CardId,
            //Name = existingCard.Name,
            IsPublic = existingCard.IsPublic,
            IsTemplate = existingCard.IsTemplate,
            IsArchived = existingCard.IsArchived,
            EstimatedPrice = existingCard.EstimatedPrice,
            ColorId = existingCard.ColorId
        };
    }

    public Task<CardDto> Modify(Card card)
    {
        throw new NotImplementedException();
    }

    public async Task<bool> Delete(uint cardId)
    {
        var card = await _context.Cards.FindAsync(cardId);
        if (card == null)
        {
            return false;
        }

        _context.Cards.Remove(card);
        await _context.SaveChangesAsync();
        return true;
    }

    public async Task UpdateAsync(Card card)
    {
        _context.Cards.Update(card);
        await _context.SaveChangesAsync();
    }
    
}