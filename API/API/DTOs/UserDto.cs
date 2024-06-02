using System;
using System.Collections.Generic;
using API.Model;

namespace API.DTOs;

public partial class UserDto
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

    public UserDto()
    {
    }

    public UserDto(User u)
    {
        UserId = u.UserId;
        Username = u.Username;
        Name = u.Name;
        Password = u.Password;
        Email = u.Email;
        PhoneNumber = u.PhoneNumber;
        ProfileImage = u.ProfileImage;
        GoogleToken = u.GoogleToken;
        FacebookToken = u.FacebookToken;
        RegisterDate = u.RegisterDate;
        LastConnection = u.LastConnection;
        SettingId = u.SettingId;
    }

    public UserDto(uint userId, string username, string name, string? password, string email, string? phoneNumber, string profileImage, string? googleToken, string? facebookToken, DateTime registerDate, DateTime? lastConnection, uint settingId)
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
