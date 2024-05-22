using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Role
{
    public uint RoleId { get; set; }

    public string RoleName { get; set; } = null!;

    public virtual ICollection<CardShareLink> CardShareLinks { get; set; } = new List<CardShareLink>();

    public virtual ICollection<MembersFromCard> MembersFromCards { get; set; } = new List<MembersFromCard>();
}
