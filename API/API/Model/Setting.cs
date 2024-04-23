using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Setting
{
    public uint SettingId { get; set; }

    public DateTime LastConnection { get; set; }

    public ulong IsOnline { get; set; }

    public ulong IsAdmin { get; set; }

    public ulong IsPrivate { get; set; }

    public ulong IsInfluencer { get; set; }

    public DateTime LastPasswordChanged { get; set; }

    public string LastPasswordHash { get; set; } = null!;

    public virtual ICollection<User> Users { get; set; } = new List<User>();
}
