using System.Collections.Generic;
using System.Linq;
using API.Context;
using API.DTOs;
using API.Model;
using API.Services.Interfaces;

namespace API.Services.Implementations{
    public class BoardService : IBoardService
    {
        private readonly ShopMateContext _context;

        public BoardService(ShopMateContext context)
        {
            _context = context;
        }

         public IEnumerable<BoardDto> GetBoardsByUserId(int userId)
        {
            return _context.Boards
                .Where(b => b.OwnerId == userId)
                .Select(b => new BoardDto
                {
                    BoardId = b.BoardId,
                    Title = b.Title,
                    OwnerId = b.OwnerId
                }).ToList();
        }

        public BoardDto AddBoard(Board board)
        {
            _context.Boards.Add(board);
            _context.SaveChanges();
            return new BoardDto
            {
                BoardId = board.BoardId,
                Title = board.Title,
                OwnerId = board.OwnerId
            };
        }

        public BoardDto UpdateBoard(Board board)
        {
            var existingBoard = _context.Boards.Find(board.BoardId);
            if (existingBoard == null)
            {
                return null;
            }

            existingBoard.Title = board.Title;
            existingBoard.OwnerId = board.OwnerId;

            _context.Boards.Update(existingBoard);
            _context.SaveChanges();
            return new BoardDto
            {
                BoardId = existingBoard.BoardId,
                Title = existingBoard.Title,
                OwnerId = existingBoard.OwnerId
            };
        }

        public bool DeleteBoard(int boardId)
        {
            var board = _context.Boards.Find(boardId);
            if (board == null)
            {
                return false;
            }

            _context.Boards.Remove(board);
            _context.SaveChanges();
            return true;
        }
    }
}