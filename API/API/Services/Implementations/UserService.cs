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
        
        public async Task<IEnumerable<ItemCardDto>> GetItemsByCard(int cardId)
        {
           return await _context.ItemCardLines
               .Where(i => i.CardId == cardId)
                .Select(ic => new ItemCardDto()
                {
                    ItemCardLineId = ic.ItemCardLineId,
                    CardId = ic.CardId,
                    Amount = ic.Amount,
                    AssignedTo = ic.AssignedTo,
                    CreatedBy = ic.CreatedBy,
                    Item = new ItemDto() {
                        ItemId = ic.Item.ItemId,
                        Name = ic.Item.Name,
                        Category = new CategoryDto(
                            ic.Item.Category.CategoryId,
                            ic.Item.Category.Name,
                            ic.Item.Category.Icon,
                            ic.Item.Category.UpdatedAt,
                            ic.Item.Category.CreatedAt
                            ),
                        UpdatedAt = ic.Item.UpdatedAt,
                        CreatedAt = ic.Item.CreatedAt
                    },
                    Unit = new UnitDto()
                    {
                        UnitId = ic.UnitId,
                        Name = ic.Unit.Name,
                        Prefix = ic.Unit.Prefix
                    }
                })
                .ToListAsync();
        }
    }
}