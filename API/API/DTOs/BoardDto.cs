using System;
using System.Collections.Generic;
using API.Model;

namespace API.DTOs;

public partial class BoardDto
{
    public uint BoardId { get; set; }

    public string? Title { get; set; }

    public uint OwnerId { get; set; }

    public BoardDto()
    {
    }
    
    public BoardDto(Board b)
    {
        BoardId = b.BoardId;
        Title = b.Title;
        OwnerId = b.OwnerId;
    }

    public BoardDto(uint boardId, string? title, uint ownerId)
    {
        BoardId = boardId;
        Title = title;
        OwnerId = ownerId;
    }
}
