namespace API.DTOs;

public class UserActionResponseDto
{
    public bool isActionPerformed { get; set; }
    public string Message { get; set; }
    public uint? UserId { get; set; }
    public uint? UserAction { get; set; }

    public UserActionResponseDto()
    {
    }

    public UserActionResponseDto(uint? userId, uint? userAction)
    {
        UserId = userId;
        UserAction = userAction;
    }
}