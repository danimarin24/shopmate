namespace API.Respositories.Interfaces;

public interface IRepository
{
    Task<T> GetByIdAsync<T>(uint id);
    Task<T> Add<T>(T card);
    Task<T> Update<T>(T card);
    Task<T> Modify<T>(T card);
    Task<bool> Delete(uint id);
    Task UpdateAsync<T>(T card);
}