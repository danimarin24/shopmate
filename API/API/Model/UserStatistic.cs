using System;
using System.Collections.Generic;

namespace API.Model;

public partial class UserStatistic
{
    public uint? UserId { get; set; }

    public long? Nfollows { get; set; }

    public long? Nfollowers { get; set; }

    public long? NyourSaves { get; set; }

    public long? Nsaves { get; set; }
}
