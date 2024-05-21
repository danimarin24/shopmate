using System;
using System.Collections.Generic;

namespace API.DTOs;

public partial class ColorDto
{
    public uint ColorId { get; set; }

    public string ColorHex { get; set; } = null!;

    public short ColorRed { get; set; }

    public short ColorGreen { get; set; }

    public short ColorBlue { get; set; }

    public string Name { get; set; } = null!;
}
