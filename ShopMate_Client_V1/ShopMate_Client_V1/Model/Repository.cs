using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace ShopMate_Client_V1.Model
{  

    public class Repository : HttpResponseMessage
    {
               
        private static string apiKey = "c11cddd5b1554b78b6532b41287bd243";
        string ws1 = $"http://172.16.24.21:6385/api/";
        static List<User> usersList;
        static List<Category> categoryList;
        static List<Item> itemList;


        public static object MakeRequest(string requestUrl, object JSONRequest, string JSONmethod, string JSONContentType, Type JSONResponseType)
        {
            try
            {
                HttpWebRequest request = WebRequest.Create(requestUrl) as HttpWebRequest;
                request.Headers["x-api-key"] = apiKey;
                string sb = JsonConvert.SerializeObject(JSONRequest);
                request.Method = JSONmethod;

                if (JSONmethod != "GET")
                {
                    request.ContentType = JSONContentType;
                    Byte[] bt = Encoding.UTF8.GetBytes(sb);
                    Stream st = request.GetRequestStream();
                    st.Write(bt, 0, bt.Length);
                    st.Close();
                }

                using (HttpWebResponse response = request.GetResponse() as HttpWebResponse)
                {
                    if (response.StatusCode != HttpStatusCode.OK)
                        throw new Exception(String.Format("Server error (HTTP {0}: {1}).", response.StatusCode, response.StatusDescription));

                    Stream stream1 = response.GetResponseStream();
                    StreamReader sr = new StreamReader(stream1);
                    string strsb = sr.ReadToEnd();
                    object objResponse = JsonConvert.DeserializeObject(strsb, JSONResponseType);
                    return objResponse;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }
        
        // USER
        public List<User> GetUsers()
        {
            usersList = (List<User>)MakeRequest(ws1 + "User", null, "GET", "application/json", typeof(List<User>));
            return usersList;
        }
        public User GetUserbyUserName(String username)
        {

            return (User)MakeRequest(ws1 + "User/" + username, null, "GET", "application/json", typeof(List<User>));
        }      
        public User PostUser(User user)
        {

            User user1 = new User();
            user1 = user;
            try
            {
                // Serializar el objeto User a JSON
                string jsonUser = JsonConvert.SerializeObject(user1);

                // Mostrar el formato JSON en un MessageBox
                MessageBox.Show("Formato JSON del objeto User:\n" + jsonUser);
                return (User)MakeRequest(string.Concat(ws1 + "User"), user1, "POST", "application/json", typeof(User));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

        public void DeleteUserById(int userId)
        {
           
            MakeRequest(ws1 + "User/" + userId, null, "DELETE", "application/json", typeof(void));
        }
        public User PutUser(User user, uint userId, string newName, string newUsername, string newPhone, string newEmail)
        {
            try
            {
                user.Name = newName;
                user.Username = newUsername;
                user.PhoneNumber = newPhone;
                user.Email = newEmail;

                string requestUrl = $"{ws1}User/{userId}";

                return (User)MakeRequest(requestUrl, user, "PUT", "application/json", typeof(User));


            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

        // CATEGORY
        public List<Category> GetCategories()
        {
            categoryList = (List<Category>)MakeRequest(ws1 + "Category", null, "GET", "application/json", typeof(List<Category>));
            return categoryList;
        }

        public Category PostCategory(Category category)
        {
            try
            {
                // string jsonUser = JsonConvert.SerializeObject(category);
                // MessageBox.Show("Formato JSON del objeto User:\n" + jsonUser);
                return (Category)MakeRequest(string.Concat(ws1 + "Category"), category, "POST", "application/json", typeof(Category));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }
        public void DeleteCategoriesById(int categoryId)
        {

            // MessageBox.Show(userId + "h");
            MakeRequest(ws1 + "Category/" + categoryId, null, "DELETE", "application/json", typeof(void));
        }

        // ITEMS
        public List<Item> GetItems()
        {
            itemList = (List<Item>)MakeRequest(ws1 + "Item", null, "GET", "application/json", typeof(List<Item>));
            return itemList;
        }

        public  Item PostItem(Item item)
        {
            try
            {
                object objResponse = MakeRequest(ws1 + "Item", item, "POST", "application/json", typeof(Item));

                return (Item)objResponse;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

       
    }
}

    

