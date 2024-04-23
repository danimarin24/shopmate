using System;
using System.Collections.Generic;

namespace API.Model;

public partial class InvoiceLine
{
    public uint InvoiceId { get; set; }

    public uint ItemId { get; set; }

    public int Amount { get; set; }

    public double Price { get; set; }

    public double CostXunit { get; set; }

    public virtual Invoice Invoice { get; set; } = null!;

    public virtual ItemCardLine Item { get; set; } = null!;
}
