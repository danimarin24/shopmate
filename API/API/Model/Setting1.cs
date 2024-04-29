namespace API.Model;

public partial class Setting
{
    public Setting() {}
    
    public Setting(DateTime lastConnection, ulong isOnline, ulong isAdmin, ulong isPrivate, ulong isInfluencer, DateTime lastPasswordChanged)
    {
        LastConnection = lastConnection;
        IsOnline = isOnline;
        IsAdmin = isAdmin;
        IsPrivate = isPrivate;
        IsInfluencer = isInfluencer;
        LastPasswordChanged = lastPasswordChanged;
    }
}
