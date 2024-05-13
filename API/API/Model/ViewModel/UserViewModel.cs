namespace API.Model.ViewModel;

public class UserViewModel
{
    public string? Username { get; set; }

    public string? Name { get; set; }

    public string? Password { get; set; }

    public string? Email { get; set; }

    public string? PhoneNumber { get; set; } 

    public IFormFile? ProfileImage { get; set; }

    public DateTime RegisterDate { get; set; }

    public DateTime? LastConnection { get; set; }

    public uint SettingId { get; set; }
    
}