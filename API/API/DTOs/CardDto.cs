using API.Model;

namespace API.DTOs;

public class CardDto
{
    
    public uint CardId { get; set; }
    public string CardName { get; set; }
    
    public uint OwnerId { get; set; }

    public ulong IsPublic { get; set; }

    public ulong IsTemplate { get; set; }

    public ulong IsArchived { get; set; }

    public double? EstimatedPrice { get; set; }

    public ColorDto Color { get; set; }

    public CardDto()
    {
    }
    
    public CardDto(Card c)
    {
        CardId = c.CardId;
        CardName = c.CardName;
        OwnerId = c.OwnerId;
        IsPublic = c.IsPublic;
        IsTemplate = c.IsTemplate;
        IsArchived = c.IsArchived;
        EstimatedPrice = c.EstimatedPrice;
        Color = new ColorDto(c.Color);
    }

    public CardDto(uint cardId, string cardName, uint ownerId, ulong isPublic, ulong isTemplate, ulong isArchived, double? estimatedPrice, ColorDto color)
    {
        CardId = cardId;
        CardName = cardName;
        OwnerId = ownerId;
        IsPublic = isPublic;
        IsTemplate = isTemplate;
        IsArchived = isArchived;
        EstimatedPrice = estimatedPrice;
        Color = color;
    }
}