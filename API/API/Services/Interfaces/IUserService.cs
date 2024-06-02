using System.Collections.Generic;
using API.DTOs;
using API.Model;

namespace API.Services.Interfaces{
    public interface IUserService
    {
        Task<IEnumerable<UserRoleDto>> GetMembersByCardId(int cardId);
        Task<IEnumerable<ItemCardLineDto>> GetItemsByCard(int cardId);

    }
}