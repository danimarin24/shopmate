using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Unit
{
    public uint UnitId { get; set; }

    public string Name { get; set; } = null!;

    public string Prefix { get; set; } = null!;

    public virtual ICollection<ItemCardLine> ItemCardLines { get; set; } = new List<ItemCardLine>();
}
