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
                CardName = c.CardName,
                IsPublic = c.IsPublic,
                IsTemplate = c.IsTemplate,
                IsArchived = c.IsArchived,
                EstimatedPrice = c.EstimatedPrice,
                ColorId = c.ColorId
            })
            .ToListAsync();
    }

    public async Task<IEnumerable<BoardDto>> GetBoardsByUserId(uint userId)
    {
        return await _context.Boards
            .Where(b => b.OwnerId == userId)
            .Select(b => new BoardDto
            {
                BoardId = b.BoardId,
                Title = b.Title,
                OwnerId = b.OwnerId
            }).ToListAsync();
    }

    public async Task<BoardDto> Add(Board board)
    {
        _context.Boards.Add(board);
        await _context.SaveChangesAsync(); 
        return new BoardDto
        {
            BoardId = board.BoardId,
            Title = board.Title,
            OwnerId = board.OwnerId
        };
    }

    public async Task<BoardDto> Update(Board board)
    {
        var existingBoard = await _context.Boards.FindAsync(board.BoardId);
        if (existingBoard == null)
        {
            return null;
        }
        
        existingBoard.Title = board.Title;
        existingBoard.OwnerId = board.OwnerId;

        await UpdateAsync(existingBoard);
        return new BoardDto
        {
            BoardId = existingBoard.BoardId,
            Title = existingBoard.Title,
            OwnerId = existingBoard.OwnerId,
        };
    }

    public async Task<BoardDto> Modify(Board board)
    {
        throw new NotImplementedException();
    }

    public async Task<bool> Delete(uint boardId)
    {
        var board = await _context.Boards.FindAsync(boardId);
        if (board == null)
        {
            return false;
        }

        _context.Boards.Remove(board);
        await _context.SaveChangesAsync();
        return true;
    }

    public async Task UpdateAsync(Board board)
    {
        _context.Boards.Update(board);
        await _context.SaveChangesAsync();
    }
}
