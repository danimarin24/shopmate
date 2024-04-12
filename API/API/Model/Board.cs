using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Board
{
    public uint BoardId { get; set; }

    public string? Title { get; set; }

    public uint? OwnerId { get; set; }

    public virtual User? Owner { get; set; }

    public virtual ICollection<Card> Cards { get; set; } = new List<Card>();
}
