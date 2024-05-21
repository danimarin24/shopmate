using System.Collections.Generic;
using System.Linq;
using API.Context;
using API.Model;
using API.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace API.Services.Implementations
{
    public class UserService : IUserService
    {
        private readonly ShopMateContext _context;

        public UserService(ShopMateContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<User>> GetMembersByCardId(int cardId)
        {
            return await _context.Users.Where(u => u.MembersFromCards.Any(m => m.CardId == cardId)).ToListAsync();
        }
    }
}