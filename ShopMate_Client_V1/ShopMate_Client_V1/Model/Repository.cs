using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
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

        public HttpResponseMessage PostBuffer(Uri uri, HttpContent content)
        {
            try
            {
                // Crear cliente HttpClient
                using (var client = new HttpClient())
                {
                    // Establecer encabezados y otros parámetros necesarios
                    client.DefaultRequestHeaders.Add("x-api-key", apiKey);

                    // Realizar la solicitud HTTP POST y devolver la respuesta
                    var response = client.PostAsync(uri, content).Result;
                    return response;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error en la solicitud HTTP POST: " + e.Message);
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
                // string jsonUser = JsonConvert.SerializeObject(user1);

                // Mostrar el formato JSON en un MessageBox
                // MessageBox.Show("Formato JSON del objeto User:\n" + jsonUser);
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
                string jsonItem = JsonConvert.SerializeObject(item);
                MessageBox.Show("Formato JSON del objeto Item:\n" + jsonItem);
                return (Item)MakeRequest(string.Concat(ws1 + "Item"), item, "POST", "application/json", typeof(Item));
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
                string requestUrl = $"http://172.16.24.21/api/user/images/{imageUrl}";
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
                // Usar WebClient para descargar la imagen desde la URL
                using (WebClient webClient = new WebClient())
                {
                    byte[] imageData = webClient.DownloadData(u.ProfileImage);

                    // Convertir los datos de la imagen descargada a un objeto Image
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


        private void WriteMultipartFormData(Stream stream, string boundary, string name, string value)
        {
            string formData = string.Format("--{0}\r\nContent-Disposition: form-data; name=\"{1}\"\r\n\r\n{2}\r\n", boundary, name, value);
            byte[] formDataBytes = Encoding.UTF8.GetBytes(formData);
            stream.Write(formDataBytes, 0, formDataBytes.Length);
        }

        private void WriteMultipartFile(Stream stream, string boundary, string name, IFormFile file)
        {
            if (file != null)
            {
                string fileName = file.FileName ?? "unknown";
                ;
                string contentType = file.ContentType.ToString() ?? "application/octet-stream";

                string header = string.Format("--{0}\r\nContent-Disposition: form-data; name=\"{1}\"; filename=\"{2}\"\r\nContent-Type: {3}\r\n\r\n", boundary, name, fileName, contentType);
                byte[] headerBytes = Encoding.UTF8.GetBytes(header);
                stream.Write(headerBytes, 0, headerBytes.Length);

                using (Stream fileStream = file.OpenReadStream())
                {
                    fileStream.CopyTo(stream);
                }

                byte[] lineBreakBytes = Encoding.UTF8.GetBytes("\r\n");
                stream.Write(lineBreakBytes, 0, lineBreakBytes.Length);
            }
            else
            {
                Console.WriteLine("El archivo es nulo. No se puede adjuntar.");
            }
        }

        public async Task<User> PostUserWithImage(User user, IFormFile image)
        {
            try
            {
                string requestUrl = $"{ws1}User/profileImage";
                

                HttpWebRequest request = WebRequest.Create(requestUrl) as HttpWebRequest;
                request.Headers["x-api-key"] = apiKey;
                request.Method = "POST";

                string boundary = "---------------------------" + DateTime.Now.Ticks.ToString("x");
                request.ContentType = "multipart/form-data; boundary=" + boundary;

                using (Stream requestStream = await request.GetRequestStreamAsync())
                {
                    WriteMultipartFormData(requestStream, boundary, "Username", user.Username);
                    WriteMultipartFormData(requestStream, boundary, "Name", user.Name);
                    WriteMultipartFormData(requestStream, boundary, "Password", user.Password);
                    WriteMultipartFormData(requestStream, boundary, "Email", user.Email);
                   // WriteMultipartFormData(requestStream, boundary, "Profile", user.Email);
                    WriteMultipartFormData(requestStream, boundary, "PhoneNumber", user.PhoneNumber);
                    WriteMultipartFormData(requestStream, boundary, "RegisterDate", DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ss"));
                    WriteMultipartFormData(requestStream, boundary, "LastConnection", DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ss"));
                    WriteMultipartFormData(requestStream, boundary, "SettingId", user.SettingId.ToString());
                    WriteMultipartFormData(requestStream, boundary, "StatId", user.StatId.ToString());
                    MessageBox.Show(image.FileName);
                    WriteMultipartFile(requestStream, boundary, "ProfileImage", image);

                    byte[] endBytes = Encoding.UTF8.GetBytes("--" + boundary + "--");
                    requestStream.Write(endBytes, 0, endBytes.Length);
                }

                using (HttpWebResponse response = await request.GetResponseAsync() as HttpWebResponse)
                {
                    if (response.StatusCode != HttpStatusCode.OK)
                        throw new Exception($"Server error (HTTP {response.StatusCode}: {response.StatusDescription}).");

                    Stream responseStream = response.GetResponseStream();
                    StreamReader sr = new StreamReader(responseStream);
                    string responseJson = sr.ReadToEnd();
                    User responseUser = JsonConvert.DeserializeObject<User>(responseJson);
                    return responseUser;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }

    }
}

    

