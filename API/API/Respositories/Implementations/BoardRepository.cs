using API.Respositories.Interfaces;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;
using API.Context;
using API.DTOs;
using API.Model;
using API.Services.Implementations;

namespace API.Respositories.Implementations;

public class BoardRepository : IBoardRepository
{
    private readonly ShopMateContext _context;
    private readonly ILogger<BoardService> _logger;


    public BoardRepository(ShopMateContext context, ILogger<BoardService> logger)
    {
        _context = context;
        _logger = logger;

    }

    public async Task<Board> GetByIdAsync(uint boardId)
    {
        return await _context.Boards
            .Include(b => b.Cards)
            .FirstOrDefaultAsync(b => b.BoardId == boardId);
    }

    public async Task<CardDto> AddCardToABoard(uint boardId, Card card)
    {
        _logger.LogInformation($"Creating new card with ColorId: {card.Color.ColorId}");
        
        // Check if the color already exists
        var existingColor = await _context.Colors
            .FirstOrDefaultAsync(c => c.ColorId == card.Color.ColorId);

        if (existingColor == null)
        {
            existingColor = new Color()
            {
                ColorId = card.Color.ColorId,
                ColorHex = card.Color.ColorHex,
                ColorRed = card.Color.ColorRed,
                ColorGreen = card.Color.ColorGreen,
                ColorBlue = card.Color.ColorBlue,
                Name = card.Color.Name
            };
            _context.Colors.Add(existingColor);
            await _context.SaveChangesAsync();
        }

        var newCard = new Card()
        {
            CardId = card.CardId,
            OwnerId = card.OwnerId,
            CardName = card.CardName,
            ColorId = existingColor.ColorId,
            Color = existingColor, // Reuse the existing color
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

        return new CardDto(newCard);
    }

    public async Task<IEnumerable<CardDto>> GetCardsByBoardId(uint boardId)
    {
        return await _context.Cards
            .Include(c => c.Color)
            .Where(c => c.Boards.Any(b => b.BoardId == boardId))
            .Select(c => new CardDto(c))
            .ToListAsync();
    }

    public async Task<IEnumerable<BoardDto>> GetBoardsByUserId(uint userId)
    {
        return await _context.Boards
            .Where(b => b.OwnerId == userId)
            .Select(b => new BoardDto(b)).ToListAsync();
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
        return new BoardDto(existingBoard);
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
