namespace API.DTOs;
public class UserStatisticDto
{
    public uint? UserId { get; set; }

    public long? Nfollows { get; set; }

    public long? Nfollowers { get; set; }

    public long? NyourSaves { get; set; }

    public long? Nsaves { get; set; }

}