using ShopMate_Client_V1.Model;
using ShopMate_Client_V1.View;
using System.Windows.Forms;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Security.Cryptography;

namespace ShopMate_Client_V1.Controller
{
    public class ControllerLogin
    {
        Login l;
        Repository r;

        public ControllerLogin()
        {
            l = new Login();
            r = new Repository();
            InitListeners();
            LoadData();
            Application.Run(l);
        }
        private void LoadData()
        {
            
        }
        private void InitListeners()
        {
            l.btn_iniciar.Click += login;
                
        }

        private void login(object sender, EventArgs e)
        {
            var username = l.txt_username.Text;
            var password = sha256_hash(l.txt_pass.Text);
            bool checkUser = false;
            bool checkPass = false;

            
            l.lbl_adv_pass.Text = "";
            l.lbl_adv_user.Text = "";

            User u = r.GetUserbyUserName(username);

          
            if (u != null && u.Username.Equals(username))
            {
                checkUser = true;
            }
            else
            {
                l.lbl_adv_user.Text = "Username no encontrado";
            }

            if (u != null && u.Password.Equals(password))
            {
                checkPass = true;
            }
            else if (u != null) 
            {
                l.lbl_adv_pass.Text = "El password no coincide";
            }

            
            if (checkUser && checkPass)
            {             
                
                new Controller1();
                l.Visible = false;
                
            }
        }

        public static String sha256_hash(String value)
        {
            StringBuilder Sb = new StringBuilder();

            using (SHA256 hash = SHA256Managed.Create())
            {
                Encoding enc = Encoding.UTF8;
                Byte[] result = hash.ComputeHash(enc.GetBytes(value));

                foreach (Byte b in result)
                    Sb.Append(b.ToString("x2"));
            }

            return Sb.ToString();
        }

    }
}
