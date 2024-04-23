using System;
using System.Collections.Generic;

namespace API.Model;

public partial class ItemCardLine
{
    public uint ItemCardLineId { get; set; }

    public uint CardId { get; set; }

    public uint CreatedBy { get; set; }

    public uint AssignedTo { get; set; }

    public int Amount { get; set; }

    public uint UnitId { get; set; }

    public double Price { get; set; }

    public uint ItemId { get; set; }

    public virtual User AssignedToNavigation { get; set; } = null!;

    public virtual Card Card { get; set; } = null!;

    public virtual User CreatedByNavigation { get; set; } = null!;

    public virtual ICollection<InvoiceLine> InvoiceLines { get; set; } = new List<InvoiceLine>();

    public virtual Item Item { get; set; } = null!;

    public virtual Unit Unit { get; set; } = null!;
}
