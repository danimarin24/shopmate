namespace API.Model.Extra
{
    public class ValidateShareCardLinkResponse
    {
        public bool IsValid { get; set; }
        public string Message { get; set; }
        public uint? CardId { get; set; }
        public uint? UserId { get; set; }
    }
}
