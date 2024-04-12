using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Item
{
    public uint ItemId { get; set; }

    public string? Name { get; set; }

    public uint? CategoryId { get; set; }

    public DateTime? UpdatedAt { get; set; }

    public DateTime? CreatedAt { get; set; }

    public uint? ReferenceItemId { get; set; }

    public ulong? IsDefault { get; set; }

    public virtual Category? Category { get; set; }

    public virtual ICollection<ItemCardLine> ItemCardLines { get; set; } = new List<ItemCardLine>();

    public virtual UserItem? ReferenceItem { get; set; }
}
