using System;

namespace ShopMate_Client_V1.Model
{
    internal class DataDto
    {
        
        public string Username { get; set; }
        public string Name { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        public string ProfileImage { get; set; }
        public string GoogleToken { get; set; }
        public string FacebookToken { get; set; }
        public DateTime RegisterDate { get; set; }
        public DateTime LastConnection { get; set; }
        public uint SettingId { get; set; }
    }
}