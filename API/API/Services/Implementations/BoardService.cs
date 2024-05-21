using System.Collections.Generic;
using System.Linq;
using API.Context;
using API.DTOs;
using API.Model;
using API.Respositories.Interfaces;
using API.Services.Interfaces;
using AutoMapper;

namespace API.Services.Implementations{
    public class BoardService : IBoardService
    {
        private readonly IBoardRepository _boardRepository;
        private readonly ICardRepository _cardRepository;
        //private readonly IUserRepository _userRepository;
        //private readonly INotificationService _notificationService;
        private readonly IMapper _mapper;

        public BoardService(
            IBoardRepository boardRepository,
            ICardRepository cardRepository,
            //IUserRepository userRepository,
            //INotificationService notificationService,
            IMapper mapper)
        {
            _boardRepository = boardRepository;
            _cardRepository = cardRepository;
            //_userRepository = userRepository;
            //_notificationService = notificationService;
            _mapper = mapper;
        }

         public async Task<IEnumerable<BoardDto>> GetBoardsByUserId(uint userId)
         {
             return await _boardRepository.GetBoardsByUserId(userId);
         }

        public async Task<BoardDto> AddBoard(Board board)
        {
            return await _boardRepository.Add(board);
        }

        public async Task<BoardDto> UpdateBoard(Board board)
        {
            return await _boardRepository.Modify(board);
        }

        public async Task<bool> DeleteBoard(uint boardId)
        {
            return await _boardRepository.Delete(boardId);
        }
    }
}