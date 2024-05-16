using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Board
{
    public Board()
    {
    }

    public Board(string? title, uint ownerId)
    {
        Title = title;
        OwnerId = ownerId;
    }
    
    public Board(uint boardId, string? title, uint ownerId)
    {
        BoardId = boardId;
        Title = title;
        OwnerId = ownerId;
    }
}
