using System.Collections.Generic;
using API.DTOs;
using API.Model;

namespace API.Services.Interfaces
{
    public interface IBoardService
    {
        IEnumerable<BoardDto> GetBoardsByUserId(int userId);
        BoardDto AddBoard(Board board);
        BoardDto UpdateBoard(Board board);
        bool DeleteBoard(int boardId);
    }
}
