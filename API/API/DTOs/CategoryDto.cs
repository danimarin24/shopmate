using API.Model;

namespace API.DTOs;

public class CategoryDto
{
    public uint CategoryId { get; set; }

    public string Name { get; set; } = null!;

    public string Icon { get; set; } = null!;

    public DateTime UpdatedAt { get; set; }

    public DateTime CreatedAt { get; set; }
    
    public List<ItemDto> Items { get; set; }

    public CategoryDto()
    {
    }
    
    public CategoryDto(Category c)
    {
        CategoryId = c.CategoryId;
        Name = c.Name;
        Icon = c.Icon;
        UpdatedAt = c.UpdatedAt;
        CreatedAt = c.CreatedAt;
        Items = c.Items.Select(i => new ItemDto(i)).ToList();
    }

    public CategoryDto(uint categoryId, string name, string icon, DateTime updatedAt, DateTime createdAt)
    {
        CategoryId = categoryId;
        Name = name;
        Icon = icon;
        UpdatedAt = updatedAt;
        CreatedAt = createdAt;
    }
}