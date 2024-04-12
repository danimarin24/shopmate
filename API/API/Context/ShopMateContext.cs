using System;
using System.Collections.Generic;
using API.Model;
using Microsoft.EntityFrameworkCore;
using Pomelo.EntityFrameworkCore.MySql.Scaffolding.Internal;

namespace API.Context;

public partial class ShopMateContext : DbContext
{
    public ShopMateContext()
    {
    }

    public ShopMateContext(DbContextOptions<ShopMateContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Board> Boards { get; set; }

    public virtual DbSet<Card> Cards { get; set; }

    public virtual DbSet<Category> Categories { get; set; }

    public virtual DbSet<Color> Colors { get; set; }

    public virtual DbSet<Invoice> Invoices { get; set; }

    public virtual DbSet<InvoiceLine> InvoiceLines { get; set; }

    public virtual DbSet<Item> Items { get; set; }

    public virtual DbSet<ItemCardLine> ItemCardLines { get; set; }

    public virtual DbSet<MembersFromCard> MembersFromCards { get; set; }

    public virtual DbSet<Rol> Rols { get; set; }

    public virtual DbSet<Setting> Settings { get; set; }

    public virtual DbSet<Stat> Stats { get; set; }

    public virtual DbSet<Unit> Units { get; set; }

    public virtual DbSet<User> Users { get; set; }

    public virtual DbSet<UserItem> UserItems { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see https://go.microsoft.com/fwlink/?LinkId=723263.
        => optionsBuilder.UseMySql("server=localhost;port=3306;database=ShopMate;uid=root;pwd=123456", Microsoft.EntityFrameworkCore.ServerVersion.Parse("8.0.36-mysql"));

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder
            .UseCollation("utf8mb4_0900_ai_ci")
            .HasCharSet("utf8mb4");

        modelBuilder.Entity<Board>(entity =>
        {
            entity.HasKey(e => e.BoardId).HasName("PRIMARY");

            entity.ToTable("Board");

            entity.HasIndex(e => e.OwnerId, "IX_Board_OwnerId");

            entity.Property(e => e.Title)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");

            entity.HasOne(d => d.Owner).WithMany(p => p.Boards)
                .HasForeignKey(d => d.OwnerId)
                .HasConstraintName("Board_ibfk_1");

            entity.HasMany(d => d.Cards).WithMany(p => p.Boards)
                .UsingEntity<Dictionary<string, object>>(
                    "BoardCard",
                    r => r.HasOne<Card>().WithMany()
                        .HasForeignKey("CardId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("BoardCard_ibfk_2"),
                    l => l.HasOne<Board>().WithMany()
                        .HasForeignKey("BoardId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("BoardCard_ibfk_1"),
                    j =>
                    {
                        j.HasKey("BoardId", "CardId")
                            .HasName("PRIMARY")
                            .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0 });
                        j.ToTable("BoardCard");
                        j.HasIndex(new[] { "CardId" }, "IX_BoardCard_CardId");
                    });
        });

        modelBuilder.Entity<Card>(entity =>
        {
            entity.HasKey(e => e.CardId).HasName("PRIMARY");

            entity.ToTable("Card");

            entity.HasIndex(e => e.ColorId, "IX_Card_ColorId");

            entity.HasIndex(e => e.OwnerId, "IX_Card_OwnerId");

            entity.Property(e => e.EstimatedPrice)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.IsArchived)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.IsPublic)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.IsTemplate)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");

            entity.HasOne(d => d.Color).WithMany(p => p.Cards)
                .HasForeignKey(d => d.ColorId)
                .HasConstraintName("Card_ibfk_2");

            entity.HasOne(d => d.Owner).WithMany(p => p.Cards)
                .HasForeignKey(d => d.OwnerId)
                .HasConstraintName("Card_ibfk_1");
        });

        modelBuilder.Entity<Category>(entity =>
        {
            entity.HasKey(e => e.CategoryId).HasName("PRIMARY");

            entity.ToTable("Category");

            entity.HasIndex(e => e.Name, "IX_Category_Name");

            entity.Property(e => e.CreatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnType("datetime");
            entity.Property(e => e.Icon)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.Name)
                .HasMaxLength(20)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.UpdatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnType("datetime");
        });

        modelBuilder.Entity<Color>(entity =>
        {
            entity.HasKey(e => e.ColorId).HasName("PRIMARY");

            entity.ToTable("Color");

            entity.Property(e => e.ColorBlue)
                .HasMaxLength(3)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.ColorGreen)
                .HasMaxLength(3)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.ColorHex)
                .HasMaxLength(6)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.ColorRed)
                .HasMaxLength(3)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
        });

        modelBuilder.Entity<Invoice>(entity =>
        {
            entity.HasKey(e => e.InvoiceId).HasName("PRIMARY");

            entity.ToTable("Invoice");

            entity.HasIndex(e => e.CardId, "IX_Invoice_CardId");

            entity.Property(e => e.PaidBy)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.PaidDate)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.TicketImage).HasColumnType("bit(1)");
            entity.Property(e => e.TotalPrice)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");

            entity.HasOne(d => d.Card).WithMany(p => p.Invoices)
                .HasForeignKey(d => d.CardId)
                .HasConstraintName("Invoice_ibfk_1");
        });

        modelBuilder.Entity<InvoiceLine>(entity =>
        {
            entity.HasKey(e => e.InvoiceId).HasName("PRIMARY");

            entity.ToTable("InvoiceLine");

            entity.HasIndex(e => e.ItemId, "IX_InvoiceLine_ItemId");

            entity.Property(e => e.InvoiceId).ValueGeneratedOnAdd();
            entity.Property(e => e.Amount)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.CostXunit)
                .HasMaxLength(50)
                .HasColumnName("CostXUnit")
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.Price)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");

            entity.HasOne(d => d.Invoice).WithOne(p => p.InvoiceLine)
                .HasForeignKey<InvoiceLine>(d => d.InvoiceId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("InvoiceLine_ibfk_1");

            entity.HasOne(d => d.Item).WithMany(p => p.InvoiceLines)
                .HasForeignKey(d => d.ItemId)
                .HasConstraintName("InvoiceLine_ItemCardLine_FK");
        });

        modelBuilder.Entity<Item>(entity =>
        {
            entity.HasKey(e => e.ItemId).HasName("PRIMARY");

            entity.ToTable("Item");

            entity.HasIndex(e => e.CategoryId, "IX_Item_CategoryId");

            entity.HasIndex(e => e.ReferenceItemId, "Item_UserItem_FK");

            entity.Property(e => e.CreatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnType("datetime");
            entity.Property(e => e.IsDefault).HasColumnType("bit(1)");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.UpdatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnType("datetime");

            entity.HasOne(d => d.Category).WithMany(p => p.Items)
                .HasForeignKey(d => d.CategoryId)
                .HasConstraintName("Item_ibfk_1");

            entity.HasOne(d => d.ReferenceItem).WithMany(p => p.Items)
                .HasForeignKey(d => d.ReferenceItemId)
                .HasConstraintName("Item_UserItem_FK");
        });

        modelBuilder.Entity<ItemCardLine>(entity =>
        {
            entity.HasKey(e => e.ItemCardLineId).HasName("PRIMARY");

            entity.ToTable("ItemCardLine");

            entity.HasIndex(e => e.AssignedTo, "AssignedTo");

            entity.HasIndex(e => e.CardId, "CardId");

            entity.HasIndex(e => e.CreatedBy, "CreatedBy");

            entity.HasIndex(e => e.ItemId, "ItemCardLine_Item_FK");

            entity.HasIndex(e => e.UnitId, "ItemCardLine_Unit_FK");

            entity.Property(e => e.Amount)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");

            entity.HasOne(d => d.AssignedToNavigation).WithMany(p => p.ItemCardLineAssignedToNavigations)
                .HasForeignKey(d => d.AssignedTo)
                .HasConstraintName("ItemCardLine_ibfk_3");

            entity.HasOne(d => d.Card).WithMany(p => p.ItemCardLines)
                .HasForeignKey(d => d.CardId)
                .HasConstraintName("ItemCardLine_ibfk_1");

            entity.HasOne(d => d.CreatedByNavigation).WithMany(p => p.ItemCardLineCreatedByNavigations)
                .HasForeignKey(d => d.CreatedBy)
                .HasConstraintName("ItemCardLine_ibfk_2");

            entity.HasOne(d => d.Item).WithMany(p => p.ItemCardLines)
                .HasForeignKey(d => d.ItemId)
                .HasConstraintName("ItemCardLine_Item_FK");

            entity.HasOne(d => d.Unit).WithMany(p => p.ItemCardLines)
                .HasForeignKey(d => d.UnitId)
                .HasConstraintName("ItemCardLine_Unit_FK");
        });

        modelBuilder.Entity<MembersFromCard>(entity =>
        {
            entity.HasKey(e => new { e.CardId, e.UserId, e.RolId })
                .HasName("PRIMARY")
                .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0, 0 });

            entity.ToTable("MembersFromCard");

            entity.HasIndex(e => e.RolId, "IX_MembersFromCard_RolId");

            entity.HasIndex(e => e.UserId, "IX_MembersFromCard_UserId");

            entity.Property(e => e.CardId).ValueGeneratedOnAdd();

            entity.HasOne(d => d.Card).WithMany(p => p.MembersFromCards)
                .HasForeignKey(d => d.CardId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("MembersFromCard_ibfk_1");

            entity.HasOne(d => d.Rol).WithMany(p => p.MembersFromCards)
                .HasForeignKey(d => d.RolId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("MembersFromCard_ibfk_3");

            entity.HasOne(d => d.User).WithMany(p => p.MembersFromCards)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("MembersFromCard_ibfk_2");
        });

        modelBuilder.Entity<Rol>(entity =>
        {
            entity.HasKey(e => e.RolId).HasName("PRIMARY");

            entity.ToTable("Rol");

            entity.Property(e => e.CanEdit).HasColumnType("bit(1)");
            entity.Property(e => e.CanRead).HasColumnType("bit(1)");
            entity.Property(e => e.IsAdmin).HasColumnType("bit(1)");
        });

        modelBuilder.Entity<Setting>(entity =>
        {
            entity.HasKey(e => e.SettingId).HasName("PRIMARY");

            entity.ToTable("Setting");

            entity.Property(e => e.IsAdmin).HasColumnType("bit(1)");
            entity.Property(e => e.IsInfluencer).HasColumnType("bit(1)");
            entity.Property(e => e.IsOnline).HasColumnType("bit(1)");
            entity.Property(e => e.IsPrivate).HasColumnType("bit(1)");
            entity.Property(e => e.LastConnection).HasColumnType("datetime");
        });

        modelBuilder.Entity<Stat>(entity =>
        {
            entity.HasKey(e => e.StatId).HasName("PRIMARY");

            entity.ToTable("Stat");

            entity.Property(e => e.Nfollowers).HasColumnName("NFollowers");
            entity.Property(e => e.Nfollows).HasColumnName("NFollows");
            entity.Property(e => e.Nsaves).HasColumnName("NSaves");
            entity.Property(e => e.NyourSaves).HasColumnName("NYourSaves");
        });

        modelBuilder.Entity<Unit>(entity =>
        {
            entity.HasKey(e => e.UnitId).HasName("PRIMARY");

            entity.ToTable("Unit");

            entity.Property(e => e.Name)
                .HasMaxLength(20)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.Prefix)
                .HasMaxLength(4)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.UserId).HasName("PRIMARY");

            entity.ToTable("User");

            entity.HasIndex(e => e.Email, "IX_User_Email");

            entity.HasIndex(e => e.LastConnection, "IX_User_LastConnection");

            entity.HasIndex(e => e.Username, "IX_User_Username");

            entity.HasIndex(e => e.SettingId, "SettingId");

            entity.HasIndex(e => e.StatId, "StatId");

            entity.Property(e => e.Email)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.FacebookToken).HasColumnType("text");
            entity.Property(e => e.GoogleToken).HasColumnType("text");
            entity.Property(e => e.LastConnection)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.Password).HasColumnType("text");
            entity.Property(e => e.PhoneNumber)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.ProfileImage)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.RegisterDate)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.Username)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");

            entity.HasOne(d => d.Setting).WithMany(p => p.Users)
                .HasForeignKey(d => d.SettingId)
                .HasConstraintName("User_ibfk_1");

            entity.HasOne(d => d.Stat).WithMany(p => p.Users)
                .HasForeignKey(d => d.StatId)
                .HasConstraintName("User_ibfk_2");

            entity.HasMany(d => d.CardsNavigation).WithMany(p => p.Users)
                .UsingEntity<Dictionary<string, object>>(
                    "SavedCard",
                    r => r.HasOne<Card>().WithMany()
                        .HasForeignKey("CardId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("SavedCard_ibfk_2"),
                    l => l.HasOne<User>().WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("SavedCard_ibfk_1"),
                    j =>
                    {
                        j.HasKey("UserId", "CardId")
                            .HasName("PRIMARY")
                            .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0 });
                        j.ToTable("SavedCard");
                        j.HasIndex(new[] { "CardId" }, "IX_SavedCard_CardId");
                    });

            entity.HasMany(d => d.UserFolloweds).WithMany(p => p.Users)
                .UsingEntity<Dictionary<string, object>>(
                    "UserFollower",
                    r => r.HasOne<User>().WithMany()
                        .HasForeignKey("UserFollowed")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("UserFollower_ibfk_2"),
                    l => l.HasOne<User>().WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("UserFollower_ibfk_1"),
                    j =>
                    {
                        j.HasKey("UserId", "UserFollowed")
                            .HasName("PRIMARY")
                            .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0 });
                        j.ToTable("UserFollower");
                        j.HasIndex(new[] { "UserFollowed" }, "IX_UserFollower_UserFollowed");
                    });

            entity.HasMany(d => d.Users).WithMany(p => p.UserFolloweds)
                .UsingEntity<Dictionary<string, object>>(
                    "UserFollower",
                    r => r.HasOne<User>().WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("UserFollower_ibfk_1"),
                    l => l.HasOne<User>().WithMany()
                        .HasForeignKey("UserFollowed")
                        .OnDelete(DeleteBehavior.ClientSetNull)
                        .HasConstraintName("UserFollower_ibfk_2"),
                    j =>
                    {
                        j.HasKey("UserId", "UserFollowed")
                            .HasName("PRIMARY")
                            .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0 });
                        j.ToTable("UserFollower");
                        j.HasIndex(new[] { "UserFollowed" }, "IX_UserFollower_UserFollowed");
                    });
        });

        modelBuilder.Entity<UserItem>(entity =>
        {
            entity.HasKey(e => e.UserItemId).HasName("PRIMARY");

            entity.ToTable("UserItem");

            entity.HasIndex(e => e.CreatorId, "CreatorId");

            entity.HasIndex(e => e.CategoryId, "IX_UserItem_CategoryId");

            entity.Property(e => e.CreatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnType("datetime");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .UseCollation("utf8mb3_general_ci")
                .HasCharSet("utf8mb3");
            entity.Property(e => e.UpdatedAt)
                .HasDefaultValueSql("CURRENT_TIMESTAMP")
                .HasColumnType("datetime");

            entity.HasOne(d => d.Category).WithMany(p => p.UserItems)
                .HasForeignKey(d => d.CategoryId)
                .HasConstraintName("UserItem_ibfk_1");

            entity.HasOne(d => d.Creator).WithMany(p => p.UserItems)
                .HasForeignKey(d => d.CreatorId)
                .HasConstraintName("UserItem_ibfk_2");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
