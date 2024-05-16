using System;
using System.Collections.Generic;

namespace API.Model.DTO;

public partial class ColorDTO
{
    public uint ColorId { get; set; }

    public string ColorHex { get; set; } = null!;

    public short ColorRed { get; set; }

    public short ColorGreen { get; set; }

    public short ColorBlue { get; set; }

    public string Name { get; set; } = null!;

    public ColorDTO()
    {
    }

    public ColorDTO(uint colorId, string colorHex, short colorRed, short colorGreen, short colorBlue, string name)
    {
        ColorId = colorId;
        ColorHex = colorHex;
        ColorRed = colorRed;
        ColorGreen = colorGreen;
        ColorBlue = colorBlue;
        Name = name;
    }
}
