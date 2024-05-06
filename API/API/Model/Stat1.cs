using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Stat
{
    public Stat(ulong nfollowers, ulong nfollows, ulong nsaves, ulong nyourSaves)
    {
        Nfollowers = nfollowers;
        Nfollows = nfollows;
        Nsaves = nsaves;
        NyourSaves = nyourSaves;
    }
}
