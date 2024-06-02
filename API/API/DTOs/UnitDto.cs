using API.Model;

namespace API.DTOs;

public class UnitDto
{
    
    public uint UnitId { get; set; }

    public string Name { get; set; } = null!;

    public string Prefix { get; set; } = null!;

    public UnitDto()
    {
    }

    public UnitDto(Unit u)
    {
        UnitId = u.UnitId;
        Name = u.Name;
        Prefix = u.Prefix;
    }

    public UnitDto(uint unitId, string name, string prefix)
    {
        UnitId = unitId;
        Name = name;
        Prefix = prefix;
    }
}