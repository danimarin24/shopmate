using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Color
{
    public uint ColorId { get; set; }

    public string? ColorHex { get; set; }

    public string? ColorRed { get; set; }

    public string? ColorGreen { get; set; }

    public string? ColorBlue { get; set; }

    public virtual ICollection<Card> Cards { get; set; } = new List<Card>();
}
