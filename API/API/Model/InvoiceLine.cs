using System;
using System.Collections.Generic;

namespace API.Model;

public partial class InvoiceLine
{
    public uint InvoiceId { get; set; }

    public uint? ItemId { get; set; }

    public string? Amount { get; set; }

    public string? Price { get; set; }

    public string? CostXunit { get; set; }

    public virtual Invoice Invoice { get; set; } = null!;

    public virtual ItemCardLine? Item { get; set; }
}
