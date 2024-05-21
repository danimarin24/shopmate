using System.Collections.Generic;
using API.DTOs;
using API.Model;

namespace API.Services.Interfaces
{
    public interface IBoardService
    {
        Task<IEnumerable<BoardDto>> GetBoardsByUserId(uint userId);
        Task<BoardDto> AddBoard(Board board);
        Task<BoardDto> UpdateBoard(Board board);
        Task<bool>  DeleteBoard(uint boardId);
    }
}
