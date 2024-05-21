using System.Collections.Generic;
using API.Model;

namespace API.Services.Interfaces{
    public interface IUserService
    {
        Task<IEnumerable<User>> GetMembersByCardId(int cardId);
    }
}