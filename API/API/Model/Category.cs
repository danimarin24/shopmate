using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Category
{
    public uint CategoryId { get; set; }

    public string Name { get; set; } = null!;

    public string Icon { get; set; } = null!;

    public DateTime UpdatedAt { get; set; }

    public DateTime CreatedAt { get; set; }

    public virtual ICollection<Item> Items { get; set; } = new List<Item>();

    public virtual ICollection<UserItem> UserItems { get; set; } = new List<UserItem>();
}
