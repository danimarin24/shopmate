using ShopMate_Client_V1.Properties;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Windows.Forms.AxHost;

namespace ShopMate_Client_V1.Model
{
    public class User
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

        public uint SettingId { get; set; }

        public uint StatId { get; set; }

        // public int nFollowes = r.getNfollowersbyID();
        // public int nFolloweds = 
    }
}
