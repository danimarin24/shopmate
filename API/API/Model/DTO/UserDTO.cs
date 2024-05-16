using System;
using System.Collections.Generic;

namespace API.Model.DTO;

public partial class UserDTO
{
    public uint UserId { get; set; }

    public string Username { get; set; } = null!;

    public string Name { get; set; } = null!;

    public string? Password { get; set; }

    public string Email { get; set; } = null!;

    public string? PhoneNumber { get; set; }

    public string ProfileImage { get; set; } = null!;

    public string? GoogleToken { get; set; }

    public string? FacebookToken { get; set; }

    public DateTime RegisterDate { get; set; }

    public DateTime? LastConnection { get; set; }

    public uint SettingId { get; set; }

    public UserDTO()
    {
    }

    public UserDTO(uint userId, string username, string name, string? password, string email, string? phoneNumber, string profileImage, string? googleToken, string? facebookToken, DateTime registerDate, DateTime? lastConnection, uint settingId)
    {
        UserId = userId;
        Username = username;
        Name = name;
        Password = password;
        Email = email;
        PhoneNumber = phoneNumber;
        ProfileImage = profileImage;
        GoogleToken = googleToken;
        FacebookToken = facebookToken;
        RegisterDate = registerDate;
        LastConnection = lastConnection;
        SettingId = settingId;
    }
}
