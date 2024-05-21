using API.Respositories.Interfaces;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;
using API.Context;
using API.DTOs;
using API.Model;

namespace API.Respositories.Implementations;

public class BoardRepository : IBoardRepository
{
    private readonly ShopMateContext _context;

    public BoardRepository(ShopMateContext context)
    {
        _context = context;
    }

    public async Task<Board> GetByIdAsync(uint boardId)
    {
        return await _context.Boards
            .Include(b => b.Cards)
            .FirstOrDefaultAsync(b => b.BoardId == boardId);
    }
    
    public async Task<IEnumerable<CardDto>> GetCardsByBoardId(uint boardId)
    {
        return await _context.Cards
            .Where(c => c.Boards.Any(b => b.BoardId == boardId))
            .Select(c => new CardDto
            {
                CardId = c.CardId,
                //Name = c.Name,
                IsPublic = c.IsPublic,
                IsTemplate = c.IsTemplate,
                IsArchived = c.IsArchived,
                EstimatedPrice = c.EstimatedPrice,
                ColorId = c.ColorId
            })
            .ToListAsync();
    }

    public async Task UpdateAsync(Board board)
    {
        _context.Boards.Update(board);
        await _context.SaveChangesAsync();
    }
}
