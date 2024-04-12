using System;
using System.Collections.Generic;

namespace API.Model;

public partial class UserItem
{
    public uint UserItemId { get; set; }

    public string? Name { get; set; }

    public uint? CategoryId { get; set; }

    public uint? CreatorId { get; set; }

    public DateTime? UpdatedAt { get; set; }

    public DateTime? CreatedAt { get; set; }

    public virtual Category? Category { get; set; }

    public virtual User? Creator { get; set; }

    public virtual ICollection<Item> Items { get; set; } = new List<Item>();
}
