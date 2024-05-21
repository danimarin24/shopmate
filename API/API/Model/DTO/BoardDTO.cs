using System;
using System.Collections.Generic;

namespace API.Model.DTO;

public partial class BoardDTO
{
    public uint BoardId { get; set; }

    public string? Title { get; set; }

    public uint OwnerId { get; set; }
    
    public ICollection<CardDTO> Cards { get; set; }

    public BoardDTO()
    {
    }

    public BoardDTO(uint boardId, string? title, uint ownerId)
    {
        BoardId = boardId;
        Title = title;
        OwnerId = ownerId;
    }

    public BoardDTO(uint boardId, string? title, uint ownerId, ICollection<CardDTO> cards)
    {
        BoardId = boardId;
        Title = title;
        OwnerId = ownerId;
        Cards = cards;
    }
}
