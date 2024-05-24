using API.DTOs;
using API.Model;

namespace API.Respositories.Interfaces;

public interface IBoardRepository 
{
    Task<Board> GetByIdAsync(uint boardId);
    Task<IEnumerable<CardDto>> GetCardsByBoardId(uint boardId);
    Task<IEnumerable<BoardDto>> GetBoardsByUserId(uint boardId);
    Task<BoardDto> Add(Board board);
    Task<BoardDto> Update(Board board);
    Task<BoardDto> Modify(Board board);
    Task<bool> Delete(uint cardId);
    Task UpdateAsync(Board board);
}