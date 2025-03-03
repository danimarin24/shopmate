using System.Collections.Generic;
using System.Linq;
using API.Context;
using API.DTOs;
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

        public async Task<IEnumerable<UserRoleDto>> GetMembersByCardId(int cardId)
        {
            return await _context.Users
                .Where(u => u.MembersFromCards.Any(m => m.CardId == cardId))
                .Select(u => new UserRoleDto()
                {
                    UserId = u.UserId,
                    Username = u.Username,
                    Name = u.Name,
                    RoleId = u.MembersFromCards.Where(m => m.UserId == u.UserId).Select(m => m.RoleId).FirstOrDefault()
                })
                .ToListAsync();
        }
        
        public async Task<IEnumerable<ItemCardLineDto>> GetItemsByCard(int cardId)
        {
           return await _context.ItemCardLines
               .Include(i => i.Item)
               .Include(i => i.Item.Category)
               .Include(i => i.Unit)
               .Include(i => i.AssignedToNavigation)
               .Where(i => i.CardId == cardId)
                .Select(ic => new ItemCardLineDto(ic))
                .ToListAsync();
        }
    }
}