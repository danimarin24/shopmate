using API.DTOs;
using API.Model;

namespace API.Respositories.Interfaces;

public interface IBoardRepository 
{
    Task<Board> GetByIdAsync(uint boardId);
    Task<IEnumerable<CardDto>> GetCardsByBoardId(uint boardId);
    Task UpdateAsync(Board board);
}