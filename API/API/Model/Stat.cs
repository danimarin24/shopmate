using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Stat
{
    public uint StatId { get; set; }

    public ulong Nfollowers { get; set; }

    public ulong Nfollows { get; set; }

    public ulong Nsaves { get; set; }

    public ulong NyourSaves { get; set; }

    public virtual ICollection<User> Users { get; set; } = new List<User>();
}
