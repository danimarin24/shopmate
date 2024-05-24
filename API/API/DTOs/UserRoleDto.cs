using System;
using System.Collections.Generic;

namespace API.DTOs;

public partial class UserRoleDto
{
    public uint UserId { get; set; }

    public string Username { get; set; } = null!;

    public string Name { get; set; } = null!;
    
    public uint RoleId { get; set; }
}
