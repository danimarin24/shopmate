namespace API.Model.DTO;

public class CardDTO
{
    
    public uint CardId { get; set; }

    public uint OwnerId { get; set; }

    public ulong IsPublic { get; set; }

    public ulong IsTemplate { get; set; }

    public ulong IsArchived { get; set; }

    public double? EstimatedPrice { get; set; }

    public uint ColorId { get; set; }

    public CardDTO()
    {
    }

    public CardDTO(uint cardId, uint ownerId, ulong isPublic, ulong isTemplate, ulong isArchived, double? estimatedPrice, uint colorId)
    {
        CardId = cardId;
        OwnerId = ownerId;
        IsPublic = isPublic;
        IsTemplate = isTemplate;
        IsArchived = isArchived;
        EstimatedPrice = estimatedPrice;
        ColorId = colorId;
    }

    public CardDTO(Card card)
    {
        CardId = card.CardId;
        OwnerId = card.OwnerId;
        IsPublic = card.IsPublic;
        IsTemplate = card.IsTemplate;
        IsArchived = card.IsArchived;
        EstimatedPrice = card.EstimatedPrice;
        ColorId = card.ColorId;
    }
}