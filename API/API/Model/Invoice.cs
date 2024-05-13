using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Invoice
{
    public uint InvoiceId { get; set; }

    public uint CardId { get; set; }

    public uint PaidBy { get; set; }

    public double TotalPrice { get; set; }

    public DateTime PaidDate { get; set; }

    public string TicketImage { get; set; } = null!;

    public virtual Card Card { get; set; } = null!;

    public virtual InvoiceLine? InvoiceLine { get; set; }

    public virtual User PaidByNavigation { get; set; } = null!;
}
