using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ShopMate_Client_V1.Model
{
    internal class UserDTO
    {
        public uint UserId { get; set; }

        public string Username { get; set; }

        public string Name { get; set; }

        public string Password { get; set; }

        public string Email { get; set; }

        public string PhoneNumber { get; set; }

        public string ProfileImage { get; set; }

        public string GoogleToken { get; set; }

        public string FacebookToken { get; set; }

        public DateTime RegisterDate { get; set; }

        public DateTime? LastConnection { get; set; }



        public UserDTO(User u)
        {

            UserId = u.UserId;
            Username = u.Username;
            Name = u.Name;
            Password = u.Password;
            Email = u.Email;
            PhoneNumber = u.PhoneNumber;
            ProfileImage = checkProfileImage(u.ProfileImage);
            GoogleToken = checkGoogleToken(u.GoogleToken);
            FacebookToken = checkFacebookToken(u.FacebookToken);
            RegisterDate = u.RegisterDate;
            LastConnection = u.LastConnection;
        }
        private string checkGoogleToken(string token) => !string.IsNullOrEmpty(token) ? "1" : "0";
        private string checkFacebookToken(string token) => !string.IsNullOrEmpty(token) ? "1" : "0";
        private string checkProfileImage(string image) => !string.IsNullOrEmpty(image) ? "1" : "0";

    }
}
