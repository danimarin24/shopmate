using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Color
{
    public uint ColorId { get; set; }

    public string ColorHex { get; set; } = null!;

    public short ColorRed { get; set; }

    public short ColorGreen { get; set; }

    public short ColorBlue { get; set; }

    public string Name { get; set; } = null!;

    public virtual ICollection<Card> Cards { get; set; } = new List<Card>();
}
