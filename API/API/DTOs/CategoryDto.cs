namespace API.DTOs;

public class CategoryDto
{
    public uint CategoryId { get; set; }

    public string Name { get; set; } = null!;

    public string Icon { get; set; } = null!;

    public DateTime UpdatedAt { get; set; }

    public DateTime CreatedAt { get; set; }

    public CategoryDto()
    {
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