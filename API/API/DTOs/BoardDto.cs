using System;
using System.Collections.Generic;

namespace API.DTOs;

public partial class BoardDto
{
    public uint BoardId { get; set; }

    public string? Title { get; set; }

    public uint OwnerId { get; set; }
}
