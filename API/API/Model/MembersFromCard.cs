using System;
using System.Collections.Generic;

namespace API.Model;

public partial class MembersFromCard
{
    public uint CardId { get; set; }

    public uint UserId { get; set; }

    public uint RoleId { get; set; }

    public virtual Card Card { get; set; } = null!;

    public virtual Role Role { get; set; } = null!;

    public virtual User User { get; set; } = null!;
}
