using System.Security.Cryptography;
using API.Context;
using API.DTOs;
using API.Model;
using API.Model.Extra;
using API.Respositories.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace API.Respositories.Implementations;

public class CardRepository : ICardRepository
{
    private readonly ShopMateContext _context;
    private readonly IConfiguration _configuration;
    
    public CardRepository(ShopMateContext context, IConfiguration configuration)
    {
        _context = context;
        _configuration = configuration;
    }

    public async Task<CardDto> GetByIdAsync(uint cardId)
    {
        return await _context.Cards
            .Include(c => c.Color)
            .Select(c => new CardDto
            {
                CardId = c.CardId,
                CardName = c.CardName,
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
                CardName = c.CardName,
                IsPublic = c.IsPublic,
                IsTemplate = c.IsTemplate,
                IsArchived = c.IsArchived,
                EstimatedPrice = c.EstimatedPrice,
                ColorId = c.ColorId
            })
            .ToListAsync();
    }
    
    public async Task<IEnumerable<CardDto>> GetCardsContainsName(string name)
    {
        return await _context.Cards
            .Include(c => c.Color)
            .Where(c => c.CardName.ToLower().Contains(name.ToLower()))
            .Select(c => new CardDto
            {
                CardId = c.CardId,
                CardName = c.CardName,
                IsPublic = c.IsPublic,
                IsTemplate = c.IsTemplate,
                IsArchived = c.IsArchived,
                EstimatedPrice = c.EstimatedPrice,
                ColorId = c.ColorId
            })
            .ToListAsync();
    }

    public async Task<GenerateShareCardLinkResponse> GenerateShareCardLink(GenerateShareCardLinkRequest cardLinkRequest)
        {
            // Generate a unique token for the share link
            var token = GenerateToken();

            // Set an expiration time for the share link
            var expiration = DateTime.UtcNow.AddHours(24); // Link expires in 24 hours

            // Construct the share link
            var shareLink = $"{_configuration["BaseUrl"]}/api/cards/share/{token}";

            // Save the share link details in the database
            var shareCardLink = new CardShareLink
            {
                CardId = cardLinkRequest.CardId,
                RoleId = cardLinkRequest.RoleId,
                Token = token,
                Expiration = expiration
            };
            _context.CardShareLinks.Add(shareCardLink);
            await _context.SaveChangesAsync();

            return new GenerateShareCardLinkResponse
            {
                ShareLink = shareLink,
                Expiration = expiration
            };
        }

    public async Task<ValidateShareCardLinkResponse> ValidateShareCardLink(ValidateShareCardLinkRequest request)
    {
        var shareCardLink = await _context.CardShareLinks
            .FirstOrDefaultAsync(scl => scl.Token == request.Token);

        if (shareCardLink == null)
        {
            return new ValidateShareCardLinkResponse
            {
                IsValid = false,
                Message = "Invalid share link."
            };
        }

        if (shareCardLink.Expiration < DateTime.UtcNow)
        {
            return new ValidateShareCardLinkResponse
            {
                IsValid = false,
                Message = "Share link has expired."
            };
        }

        // Create a new member in the MembersFromCard table
        var member = new MembersFromCard
        {
            CardId = shareCardLink.CardId,
            UserId = request.UserId,
            RoleId = shareCardLink.RoleId
        };

        _context.MembersFromCards.Add(member);
        await _context.SaveChangesAsync();

        return new ValidateShareCardLinkResponse
        {
            IsValid = true,
            Message = "Share link is valid.",
            CardId = shareCardLink.CardId,
            UserId = request.UserId
        };
    }

    private string GenerateToken()
    {
        using (var rng = new RNGCryptoServiceProvider())
        {
            var byteArray = new byte[20];
            rng.GetBytes(byteArray);
            return Convert.ToBase64String(byteArray).Replace("+", "-").Replace("/", "_").TrimEnd('=');
        }
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
            CardName = existingCard.CardName,
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