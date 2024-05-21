using System;
using System.Collections.Generic;

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
}
