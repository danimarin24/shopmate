using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Stat
{
    public uint StatId { get; set; }

    public int? Nfollowers { get; set; }

    public int? Nfollows { get; set; }

    public int? Nsaves { get; set; }

    public int? NyourSaves { get; set; }

    public virtual ICollection<User> Users { get; set; } = new List<User>();
}
