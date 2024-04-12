using System;
using System.Collections.Generic;

namespace API.Model;

public partial class Invoice
{
    public uint InvoiceId { get; set; }

    public uint? CardId { get; set; }

    public string? PaidBy { get; set; }

    public string? TotalPrice { get; set; }

    public string? PaidDate { get; set; }

    public ulong? TicketImage { get; set; }

    public virtual Card? Card { get; set; }

    public virtual InvoiceLine? InvoiceLine { get; set; }
}
