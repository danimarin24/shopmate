namespace API.DTOs;

public class ItemCardDto
{
    public uint ItemCardLineId { get; set; }

    public uint CardId { get; set; }

    public uint CreatedBy { get; set; }

    public uint AssignedTo { get; set; }

    public int Amount { get; set; }

    public UnitDto Unit { get; set; }

    public double Price { get; set; }

    public ItemDto Item { get; set; }
}