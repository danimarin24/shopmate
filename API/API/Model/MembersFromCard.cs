using System;
using System.Collections.Generic;

namespace API.Model;

public partial class MembersFromCard
{
    public uint CardId { get; set; }

    public uint UserId { get; set; }

    public uint RolId { get; set; }

    public virtual Card Card { get; set; } = null!;

    public virtual Rol Rol { get; set; } = null!;

    public virtual User User { get; set; } = null!;
}
