using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Rol
{
    public uint RolId { get; set; }

    public ulong CanRead { get; set; }

    public ulong CanEdit { get; set; }

    public ulong IsAdmin { get; set; }

    public virtual ICollection<MembersFromCard> MembersFromCards { get; set; } = new List<MembersFromCard>();
}
