using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Item
{
    public uint ItemId { get; set; }

    public string Name { get; set; } = null!;

    public uint CategoryId { get; set; }

    public DateTime UpdatedAt { get; set; }

    public DateTime CreatedAt { get; set; }

    public virtual Category Category { get; set; } = null!;

    public virtual ICollection<ItemCardLine> ItemCardLines { get; set; } = new List<ItemCardLine>();
}
