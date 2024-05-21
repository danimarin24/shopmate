using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Card
{
    public uint CardId { get; set; }
    public uint OwnerId { get; set; }

    public ulong IsPublic { get; set; }

    public ulong IsTemplate { get; set; }

    public ulong IsArchived { get; set; }

    public double? EstimatedPrice { get; set; }

    public uint ColorId { get; set; }

    public virtual Color Color { get; set; } = null!;

    public virtual ICollection<Invoice> Invoices { get; set; } = new List<Invoice>();

    public virtual ICollection<ItemCardLine> ItemCardLines { get; set; } = new List<ItemCardLine>();

    public virtual ICollection<MembersFromCard> MembersFromCards { get; set; } = new List<MembersFromCard>();

    public virtual User Owner { get; set; } = null!;

    public virtual ICollection<Board> Boards { get; set; } = new List<Board>();

    public virtual ICollection<User> Users { get; set; } = new List<User>();
}
