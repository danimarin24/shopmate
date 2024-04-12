using System.Security.Cryptography;
using System.Text;

namespace API.Model;

public static class Repository
{
    public static string HashPassword(string password)
    {
        byte[] passwordBytes = Encoding.UTF8.GetBytes(password);
        
        using (SHA512 sha512 = SHA512.Create())
        {
            byte[] hashedBytes = sha512.ComputeHash(passwordBytes);
            return Convert.ToBase64String(hashedBytes);
        }
    }
}