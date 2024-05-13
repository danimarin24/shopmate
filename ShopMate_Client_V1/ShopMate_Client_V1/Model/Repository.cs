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
        public async Task<User> PostUserWithImage(User user, Image image)
        {
            try
            {
                var uri = new Uri(ws1 + "User/profileImage");

                using (var client = new HttpClient())
                {
                    using (var content = new MultipartFormDataContent())
                    {
                        // Agregar los campos de usuario como form data
                        content.Add(new StringContent(user.Name), "Name");
                        content.Add(new StringContent(user.SettingId.ToString()), "SettingId");
                        content.Add(new StringContent(user.StatId.ToString()), "StatId");
                        content.Add(new StringContent(user.RegisterDate.ToString()), "RegisterDate");
                        content.Add(new StringContent(user.LastConnection.ToString()), "LastConnection");
                        content.Add(new StringContent(user.Username), "Username");
                        content.Add(new StringContent(user.PhoneNumber), "PhoneNumber");
                        content.Add(new StringContent(user.Password), "Password");
                        content.Add(new StringContent(user.Email), "Email");

                        // Convertir la imagen a un byte[]
                        byte[] imageBytes;
                        using (var memoryStream = new MemoryStream())
                        {
                            image.Save(memoryStream, image.RawFormat);
                            imageBytes = memoryStream.ToArray();
                        }

                        // Agregar el byte[] de la imagen como form data
                        ByteArrayContent imageContent = new ByteArrayContent(imageBytes);
                        imageContent.Headers.ContentType = new MediaTypeHeaderValue("image/jpeg"); // Ajustar el tipo de contenido según el formato de la imagen
                        content.Add(imageContent, "ProfileImage", "profileImage.jpg");

                        var response = await client.PostAsync(uri, content);

                        if (response.IsSuccessStatusCode)
                        {
                            string jsonResponse = await response.Content.ReadAsStringAsync();
                            User newUser = JsonConvert.DeserializeObject<User>(jsonResponse);
                            return newUser;
                        }
                        else
                        {
                            string errorContent = await response.Content.ReadAsStringAsync();
                            Console.WriteLine($"Error al enviar la solicitud. Código de estado: {response.StatusCode}, Contenido del error: {errorContent}");
                            return null;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error al publicar el usuario con imagen: " + e.Message);
                return null;
            }
        }




        public async Task<User> PostUserWithImage(User user, byte[] imageBytes)
        {
            try
            {
                var uri = new Uri(ws1 + "User/profileImage");

                using (var client = new HttpClient())
                {
                    using (var content = new MultipartFormDataContent())
                    {
                        content.Add(new StringContent(JsonConvert.SerializeObject(user), Encoding.UTF8, "application/json"), "user");

                        // Crear un StreamContent a partir de los bytes de la imagen
                        var fileContent = new ByteArrayContent(imageBytes);
                        fileContent.Headers.ContentType = new MediaTypeHeaderValue("image/jpg"); // Ajustar el tipo de contenido según el formato de la imagen

                        content.Add(fileContent, "profileImage", "profileImage.jpg"); // Puedes ajustar el nombre del archivo según sea necesario

                        var response = await client.PostAsync(uri, content);

                        if (response.IsSuccessStatusCode)
                        {
                            string jsonResponse = await response.Content.ReadAsStringAsync();
                            User newUser = JsonConvert.DeserializeObject<User>(jsonResponse);
                            return newUser;
                        }
                        else
                        {
                            string errorContent = await response.Content.ReadAsStringAsync();
                            Console.WriteLine($"Error al enviar la solicitud. Código de estado: {response.StatusCode}, Contenido del error: {errorContent}");
                            return null;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error al publicar el usuario con imagen: " + e.Message);
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

        public string PostImageAsync(Stream imageStream, string category)
        {
            try
            {
                using (var client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Add("x-api-key", apiKey);

                    using (var content = new MultipartFormDataContent())
                    {
                        content.Add(new StreamContent(imageStream), "file", "image.jpg");

                        var response = client.PostAsync(ws1 + $"SharedImage?categoryImage={category}", content);

                        // http://172.16.24.21:6385/api/SharedImage?categoryImage=user






                        return response.ToString();
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return null;
            }
        }
        public async Task<string> PostImage(IFormFile file, string categoryImage)
        {
            try
            {
                // Construir la URL del endpoint para publicar la imagen
                string url = $"{ws1}SharedImage";

                // Crear un MultipartFormDataContent para enviar la imagen y categoría
                using (var content = new MultipartFormDataContent())
                {
                    // Agregar el archivo de imagen al contenido
                    content.Add(new StreamContent(file.OpenReadStream()), "file", file.FileName);

                    // Agregar la categoría de imagen al contenido
                    content.Add(new StringContent(categoryImage), "categoryImage");

                    // Realizar la solicitud HTTP POST al servidor
                    using (var response = await new HttpClient().PostAsync(url, content))
                    {
                        // Verificar si la solicitud fue exitosa
                        response.EnsureSuccessStatusCode();

                        // Leer la respuesta del servidor
                        string responseContent = await response.Content.ReadAsStringAsync();

                        // Deserializar la respuesta JSON a un objeto anónimo
                        var result = JsonConvert.DeserializeObject<dynamic>(responseContent);

                        // Obtener la URL final de la imagen desde la respuesta
                        string finalUrl = result.finalUrl;

                        return finalUrl;
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error al publicar la imagen: {ex.Message}");
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

        public async Task<string> PostImage(string imageType, byte[] imageData)
        {
            try
            {
                var uri = new Uri(ws1 + "Image"); // URL del endpoint para subir imágenes
                using (var client = new HttpClient())
                {
                    using (var content = new MultipartFormDataContent())
                    {
                        // Agregar el tipo de imagen como form data
                        content.Add(new StringContent(imageType), "ImageType");

                        // Agregar los bytes de la imagen como form data
                        ByteArrayContent imageContent = new ByteArrayContent(imageData);
                        imageContent.Headers.ContentType = new MediaTypeHeaderValue("image/jpeg"); // Ajustar el tipo de contenido según el formato de la imagen
                        content.Add(imageContent, "Image", "image.jpg"); // Puedes ajustar el nombre del archivo según sea necesario

                        // Establecer el encabezado x-api-key
                        client.DefaultRequestHeaders.Add("x-api-key", apiKey);

                        // Realizar la solicitud HTTP POST y obtener la respuesta
                        var response = await client.PostAsync(uri, content);

                        if (response.IsSuccessStatusCode)
                        {
                            string jsonResponse = await response.Content.ReadAsStringAsync();
                            return jsonResponse; // Devolver la respuesta del servidor
                        }
                        else
                        {
                            string errorContent = await response.Content.ReadAsStringAsync();
                            Console.WriteLine($"Error al enviar la solicitud. Código de estado: {response.StatusCode}, Contenido del error: {errorContent}");
                            return null;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error al enviar la imagen: " + e.Message);
                return null;
            }
        }




    }
}

    

