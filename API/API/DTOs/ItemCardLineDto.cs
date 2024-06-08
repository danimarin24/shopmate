using System;
using System.Collections.Generic;
using API.DTOs;
using API.Model;

namespace API.DTOs;

public class ItemCardLineDto
{
    public uint ItemCardLineId { get; set; }

    public uint CardId { get; set; }

    public uint CreatedBy { get; set; }

    public uint AssignedTo { get; set; }

    public int Amount { get; set; }

    public uint UnitId { get; set; }

    public double Price { get; set; }

    public uint ItemId { get; set; }

    public UserDto AssignedToUser { get; set; } = null!;

    public ItemDto Item { get; set; } = null!;

    public UnitDto Unit { get; set; } = null!;

    public ItemCardLineDto()
    {
    }

    public ItemCardLineDto(ItemCardLine i)
    {
        ItemCardLineId = i.ItemCardLineId;
        CardId = i.CardId;
        CreatedBy = i.CreatedBy;
        AssignedTo = i.AssignedTo;
        Amount = i.Amount;
        UnitId = i.UnitId;
        Price = i.Price;
        ItemId = i.ItemId;
        AssignedToUser = new UserDto(i.AssignedToNavigation);
        Item = new ItemDto(i.Item, true);
        Unit = new UnitDto(i.Unit);
    }

    public ItemCardLineDto(uint itemCardLineId, uint cardId, uint createdBy, uint assignedToId, int amount, uint unitId, double price, uint itemId, UserDto assignedTo, ItemDto item, UnitDto unit)
    {
        ItemCardLineId = itemCardLineId;
        CardId = cardId;
        CreatedBy = createdBy;
        AssignedTo = assignedToId;
        Amount = amount;
        UnitId = unitId;
        Price = price;
        ItemId = itemId;
        AssignedToUser = assignedTo;
        Item = item;
        Unit = unit;
    }
}
