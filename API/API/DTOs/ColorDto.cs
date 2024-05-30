using System;
using System.Collections.Generic;
using API.Model;

namespace API.DTOs;

public partial class ColorDto
{
    public uint ColorId { get; set; }

    public string ColorHex { get; set; } = null!;

    public short ColorRed { get; set; }

    public short ColorGreen { get; set; }

    public short ColorBlue { get; set; }

    public string Name { get; set; } = null!;

    public ColorDto()
    {
    }

    public ColorDto(Color c)
    {
        ColorId = c.ColorId;
        ColorHex = c.ColorHex;
        ColorRed = c.ColorRed;
        ColorGreen = c.ColorGreen;
        ColorBlue = c.ColorBlue;
        Name = c.Name;
    }

    public ColorDto(uint colorId, string colorHex, short colorRed, short colorGreen, short colorBlue, string name)
    {
        ColorId = colorId;
        ColorHex = colorHex;
        ColorRed = colorRed;
        ColorGreen = colorGreen;
        ColorBlue = colorBlue;
        Name = name;
    }
}
