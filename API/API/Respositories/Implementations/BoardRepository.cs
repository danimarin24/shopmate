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

    public async Task<CardDto> AddCardToABoard(uint boardId, Card card)
    {

        var newCard = new Card()
        {
            CardId = card.CardId,
            OwnerId = card.OwnerId,
            CardName = card.CardName,
            ColorId = card.Color.ColorId,
            EstimatedPrice = card.EstimatedPrice,
            IsArchived = card.IsArchived,
            IsPublic = card.IsPublic,
            IsTemplate = card.IsTemplate,
        };

        _context.Cards.Add(newCard);
        await _context.SaveChangesAsync();
        
        var board = await _context.Boards
            .Include(b => b.Cards)
            .FirstOrDefaultAsync(b => b.BoardId == boardId);

        if (board == null)
        {
            throw new Exception("Board not found");
        }
        
        board.Cards.Add(newCard); 
        await _context.SaveChangesAsync();
        
        return new CardDto()
        {
            CardId = card.CardId,
            CardName = card.CardName,
            Color = new ColorDto()
            {
                ColorId = card.Color.ColorId,
                ColorRed = card.Color.ColorRed,
                ColorBlue = card.Color.ColorBlue,
                ColorGreen = card.Color.ColorGreen,
                ColorHex = card.Color.ColorHex
            },
            EstimatedPrice = card.EstimatedPrice,
            IsArchived = card.IsArchived,
            IsPublic = card.IsPublic,
            IsTemplate = card.IsTemplate,
        };
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
                Color = new ColorDto()
                {
                    ColorId = c.Color.ColorId,
                    ColorBlue = c.Color.ColorBlue,
                    ColorRed = c.Color.ColorRed,
                    ColorGreen = c.Color.ColorGreen,
                    ColorHex = c.Color.ColorHex
                }
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
