using System;
using System.Collections.Generic;

namespace API.Model;

public partial class CardShareLink
{
    public string Token { get; set; } = null!;

    public uint CardId { get; set; }

    public uint RoleId { get; set; }

    public DateTime? Expiration { get; set; }

    public virtual Card Card { get; set; } = null!;

    public virtual Role Role { get; set; } = null!;
}
