using System;
using System.Collections.Generic;

namespace API.Model;

public partial class User
{
    public uint UserId { get; set; }

    public string? Username { get; set; }

    public string? Name { get; set; }

    public string? Password { get; set; }

    public string? Email { get; set; }

    public string? PhoneNumber { get; set; }

    public string? ProfileImage { get; set; }

    public string? GoogleToken { get; set; }

    public string? FacebookToken { get; set; }

    public string? RegisterDate { get; set; }

    public string? LastConnection { get; set; }

    public uint? SettingId { get; set; }

    public uint? StatId { get; set; }

    public virtual ICollection<Board> Boards { get; set; } = new List<Board>();

    public virtual ICollection<Card> Cards { get; set; } = new List<Card>();

    public virtual ICollection<ItemCardLine> ItemCardLineAssignedToNavigations { get; set; } = new List<ItemCardLine>();

    public virtual ICollection<ItemCardLine> ItemCardLineCreatedByNavigations { get; set; } = new List<ItemCardLine>();

    public virtual ICollection<MembersFromCard> MembersFromCards { get; set; } = new List<MembersFromCard>();

    public virtual Setting? Setting { get; set; }

    public virtual Stat? Stat { get; set; }

    public virtual ICollection<UserItem> UserItems { get; set; } = new List<UserItem>();

    public virtual ICollection<Card> CardsNavigation { get; set; } = new List<Card>();

    public virtual ICollection<User> UserFolloweds { get; set; } = new List<User>();

    public virtual ICollection<User> Users { get; set; } = new List<User>();
}
