using System.Collections.Generic;
using System.Linq;
using API.Context;
using Microsoft.EntityFrameworkCore;
using API.DTOs;
using API.Model;
using API.Model.Extra;
using API.Respositories.Interfaces;
using API.Services.Interfaces;
using AutoMapper;

namespace API.Services.Implementations
{
    public class CardService : ICardService
    {
        private readonly IBoardRepository _boardRepository;
        private readonly ICardRepository _cardRepository;
        //private readonly IUserRepository _userRepository;
        //private readonly INotificationService _notificationService;
        private readonly IMapper _mapper;

        public CardService(
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

        public async Task<IEnumerable<CardDto>> GetCardsByBoardId(uint boardId)
        {
            return await _boardRepository.GetCardsByBoardId(boardId);
        }
        
        public async Task<IEnumerable<CardDto>> GetCardsByUserId(uint userId)
        {
            return await _cardRepository.GetCardsByUserId(userId);
        }

        public async Task<IEnumerable<CardDto>> GetCardsContainsName(string name)
        {
            return await _cardRepository.GetCardsContainsName(name);
        }


        public async Task<GenerateShareCardLinkResponse> GenerateShareCardLink(GenerateShareCardLinkRequest cardLinkRequest)
        {
            return await _cardRepository.GenerateShareCardLink(cardLinkRequest);
        }

        public async Task<ValidateShareCardLinkResponse> ValidateShareCardLink(ValidateShareCardLinkRequest validateCardLinkRequest)
        {
            return await _cardRepository.ValidateShareCardLink(validateCardLinkRequest);
        }
        
        public async Task<IEnumerable<string>> GetCategoryIconsByCardId(uint cardId)
        {
            return await _cardRepository.GetCategoryIcons(cardId);
        }

        public async Task<CardDto> GetCardById(uint cardId)
        {
            return await _cardRepository.GetByIdAsync(cardId);
        }

        public async Task<CardDto> AddCardToBoard(uint boardId, Card card)
        {
            
            return await _boardRepository.AddCardToABoard(boardId, card);
        }

        public async Task<CardDto> UpdateCard(Card card)
        {
            return await _cardRepository.Modify(card);
        }

        public async Task<bool> DeleteCard(uint cardId)
        {
            return await _cardRepository.Delete(cardId);
        }
    }
}
