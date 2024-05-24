using API.Model;

namespace API.DTOs;

public class ItemDto
{
    
    public uint ItemId { get; set; }

    public string Name { get; set; } = null!;

    public CategoryDto Category { get; set; }

    public DateTime UpdatedAt { get; set; }

    public DateTime CreatedAt { get; set; }

    public ItemDto()
    {
    }

    public ItemDto(uint itemId, string name, CategoryDto category, DateTime updatedAt, DateTime createdAt)
    {
        ItemId = itemId;
        Name = name;
        Category = category;
        UpdatedAt = updatedAt;
        CreatedAt = createdAt;
    }
}