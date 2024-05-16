namespace API.Model.DTO;

public class UserStatisticDTO
{
    public uint? UserId { get; set; }

    public long? Nfollows { get; set; }

    public long? Nfollowers { get; set; }

    public long? NyourSaves { get; set; }

    public long? Nsaves { get; set; }

    public UserStatisticDTO()
    {
    }

    public UserStatisticDTO(uint? userId, long? nfollows, long? nfollowers, long? nyourSaves, long? nsaves)
    {
        UserId = userId;
        Nfollows = nfollows;
        Nfollowers = nfollowers;
        NyourSaves = nyourSaves;
        Nsaves = nsaves;
    }
}