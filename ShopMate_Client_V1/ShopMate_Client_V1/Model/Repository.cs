using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.Remoting.Messaging;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace ShopMate_Client_V1.Model
{

    public class Repository : HttpResponseMessage
    {

        private static string apiKey = "c11cddd5b1554b78b6532b41287bd243";
        string ws1 = $"https://boa-possible-mudfish.ngrok-free.app/api/";
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

            return (User)MakeRequest(ws1 + "User/checkusername/" + username, null, "GET", "application/json", typeof(User));
        }

        public User GetUserById(int userId)
        {
            return (User)MakeRequest(ws1 + "User/" + userId, null, "GET", "application/json", typeof(User));
        }
        public User PostUser(User user)
        {

            User user1 = new User();
            user1 = user;
            try
            {          
                return (User)MakeRequest(string.Concat(ws1 + "User"), user1, "POST", "application/json", typeof(User));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }
        public async Task<string> PostImageAsync(string categoryImage, Image image)
        {
            string url = $"{ws1}SharedImage?categoryImage={categoryImage}";

            using (var client = new HttpClient())
            {
                client.DefaultRequestHeaders.Add("x-api-key", apiKey);

                using (var content = new MultipartFormDataContent())
                {
                    using (var ms = new MemoryStream())
                    {
                        image.Save(ms, ImageFormat.Jpeg);
                        ms.Position = 0;

                        var fileContent = new StreamContent(ms);
                        fileContent.Headers.ContentType = new MediaTypeHeaderValue("image/jpeg");
                        content.Add(fileContent, "file", "image.jpg");

                        HttpResponseMessage response = await client.PostAsync(url, content);

                        if (!response.IsSuccessStatusCode)
                        {
                            throw new Exception($"Server error (HTTP {response.StatusCode}: {response.ReasonPhrase}).");
                        }

                        string jsonResponse = await response.Content.ReadAsStringAsync();
                        var result = JsonConvert.DeserializeObject<UploadResponse>(jsonResponse);

                        return result.FinalUrl;
                    }
                }
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

            MakeRequest(ws1 + "Category/" + categoryId, null, "DELETE", "application/json", typeof(void));
        }
        public Category PutCategory(Category categorySelected, uint categoryId, string newName)
        {
            try
            {
                categorySelected.Name = newName;


                string requestUrl = $"{ws1}Category/{categoryId}";

                return (Category)MakeRequest(requestUrl, categorySelected, "PUT", "application/json", typeof(Category));


            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

        public Category GetCategoryById(int categoryId)
        {
           return (Category) MakeRequest(ws1 + "Category/" + categoryId, null, "GET", "application/json", typeof(void));
        }

        public bool CategoryHasItem(int categoryId)
        {
            
            int nItems = GetItems().Count(i => i.CategoryId == categoryId);
           
            return nItems > 0;
        }


        // ITEMS
        public List<Item> GetItems()
        {
            itemList = (List<Item>)MakeRequest(ws1 + "Item", null, "GET", "application/json", typeof(List<Item>));
            return itemList;
        }
        public Item PostItem(Item item)
        {
            try
            {
                //string jsonItem = JsonConvert.SerializeObject(item);
                //MessageBox.Show("Formato JSON del objeto Item:\n" + jsonItem);
                return (Item)MakeRequest(string.Concat(ws1 + "Item"), item, "POST", "application/json", typeof(Item));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }
        public Item DeleteItemById(int itemId)
        {
            try
            {
                return (Item)MakeRequest(ws1 + "Item/" + itemId, null, "DELETE", "application/json", typeof(void));

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }

        }


        // IMAGE
        public Image GetImageLocal(string imageUrl)
        {
            try
            {
                string requestUrl = $"http://172.16.24.21:6385{imageUrl}";
                string extension = Path.GetExtension(imageUrl).ToLower();


                string contentType = "";
                switch (extension)
                {
                    case ".jpeg":
                    case ".jpg":
                        contentType = "image/jpeg";
                        break;
                    case ".png":
                        contentType = "image/png";
                        break;
                    default:
                        throw new NotSupportedException($"Extension {extension} not supported.");
                }


                byte[] imageData;
                using (WebClient webClient = new WebClient())
                {
                    webClient.Headers.Add("Content-Type", contentType);
                    imageData = webClient.DownloadData(requestUrl);
                }


                using (MemoryStream ms = new MemoryStream(imageData))
                {
                    return Image.FromStream(ms);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

        public Image GetGoogleImage(User u)
        {
            try
            {
              
                using (WebClient webClient = new WebClient())
                {
                    byte[] imageData = webClient.DownloadData(u.ProfileImage);                    

                    using (var stream = new System.IO.MemoryStream(imageData))
                    {
                        Image image = Image.FromStream(stream);
                        return image;
                    }
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error to charge Google Profile Photo: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return null;


            }
        }

    }
}

    

