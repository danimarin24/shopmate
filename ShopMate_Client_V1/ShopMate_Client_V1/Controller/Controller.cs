﻿using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.Internal;
using ShopMate_Client_V1.Model;
using ShopMate_Client_V1.View;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;


namespace ShopMate_Client_V1.Controller
{
    public class Controller1
    {
        ControllerCategory categoryController;
        Form1 f;
        FormUser fUser;
        Login l;
        List<User> originalUserList;
        List<UserDTO> userDTOList;
        List<Item> itemList;

        Repository r;
        User uAux = new User();        
        UserImageAux imageAux = new UserImageAux(); 

        public Controller1()
        {       
            
            f = new Form1();
            f.Show();
            fUser = new FormUser();
            r = new Repository();                    
            categoryController = new ControllerCategory(r, f.dtg_cat, f.dtg_item);           
            InitListeners();
            LoadData();
            
        }
        // INITIAL CHARGE
        public void LoadData()
        {
            originalUserList = r.GetUsers();
            userDTOList = originalUserList.Select(u => new UserDTO(u)).ToList();
            f.dtg_client.DataSource = userDTOList;
            itemList = r.GetItems(); 



            // f.dtg_client.DataSource = originalUserList;
            f.combo_cat.DataSource = new string[] { " ", "Name" };
            f.combo_user.DataSource = new string[] { " ", "Name", "Last Connection", "Register Date" };
            f.combo_item.DataSource = new string[] { " ", "Name", "Category", "Last Update", "Register Date" };
            



        }       

        // GLOBAL LISTENER
        public void InitListeners()
        {
            // USER -----------------------------
            f.btn_add_user.Click += openFormUser;
            f.btn_modify_user.Click += openFormUser;
            f.dtg_client.CellDoubleClick += modifyUserByDoubleClick;
            f.btn_delete_user.Click += deleteSelectedUsers;
            f.txt_search_user.TextChanged += searchUser;
            f.combo_user.SelectedIndexChanged += orderUser;
            f.dtg_client.CellFormatting += new DataGridViewCellFormattingEventHandler(dtg_client_CellFormatting);
            fUser.btn_add.Click += postUser;
            fUser.btn_add_image.Click += addImage;
            fUser.btn_modify.Click += putUser;
            fUser.btn_back.Click += backUserForm;

            // CATEGORY ----------------------------
            f.btn_add_cat.Click += categoryController.openFormCategory;
            f.txt_search_cat.TextChanged += searchCategory;
            f.btn_delete_cat.Click += deleteSelectedCategories;
            f.combo_cat.SelectedIndexChanged += orderCategory;

            // ITEM --------------------------------
            f.btn_add_item.Click += categoryController.openFormItem;
            f.combo_item.SelectedIndexChanged += orderItem;
            f.btn_showAll_it.Click += resetItemDGV;
            f.btn_delete_item.Click += deleteSelectedItems;
            f.txt_search_item.TextChanged += searchItem;

        }

        // USER
        private void searchUser(object sender, EventArgs e)
        {
            String actualText = f.txt_search_user.Text.ToString();

            if (String.IsNullOrEmpty(actualText))
            {
                
                f.dtg_client.DataSource = userDTOList;
            }
            else
            {
                List<UserDTO> filteredUserDTOList = userDTOList.Where(u => u.Name.ToLower().Contains(actualText.ToLower())).ToList();

                f.dtg_client.DataSource = filteredUserDTOList;
            }
        }
        private void orderUser(object sender, EventArgs e)
        {
            String selectedOrder = f.combo_user.Text.ToString();
            if (!String.IsNullOrEmpty(selectedOrder))
            {
                switch (selectedOrder)
                {
                    case "Name":
                        f.dtg_client.DataSource = userDTOList.OrderBy(u => u.Name).ToList();
                        break;
                    //case "Last Connection":
                    //    f.dtg_client.DataSource = userDTOList.OrderBy(u => u.LastConnection).ToList();
                    //    break;
                    //case "Register Date":
                    //    f.dtg_client.DataSource = userDTOList.OrderBy(u => u.RegisterDate).ToList();
                    //    break;
                    case " ":
                        f.dtg_client.DataSource = userDTOList;
                        break;
                }
            }
        }
        private async void postUser(object sender, EventArgs e)
        {
            String name = fUser.txt_name.Text;
            String userName = fUser.txt_username.Text;
            String pass = sha256_hash(fUser.txt_pass.Text);
            String phone = fUser.txt_phone.Text;
            String email = fUser.txt_email.Text;
            Image profileImage = fUser.pictureBox1.Image;

            if (String.IsNullOrEmpty(name) || String.IsNullOrEmpty(userName) || String.IsNullOrEmpty(pass))
            {
                MessageBox.Show("There are some empty fields", "Empty fields", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            User u = new User
            {
                Name = name,
                Username = userName,
                Password = pass,
                PhoneNumber = phone,
                Email = email,
                RegisterDate = DateTime.Now,
                FacebookToken = null,
                GoogleToken = null,
                LastConnection = DateTime.Now,
                SettingId = 0,
                StatId = 0
            };

            if (profileImage != null)
            {
                try
                {
                    string imageUrl = await r.PostImageAsync("user", profileImage);
                    u.ProfileImage = imageUrl;
                }
                catch (Exception ex)
                {
                    MessageBox.Show($"Error uploading image: {ex.Message}", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }
            }
            else
            {
                u.ProfileImage = "";
            }

            r.PostUser(u);
            MessageBox.Show("User posted successfully", "Success", MessageBoxButtons.OK, MessageBoxIcon.Information);
            fUser.Close();
            LoadData();
        }
        private void putUser(object sender, EventArgs e)
        {

            User selectedUser = r.GetUserById((int)selectedDGV_User().UserId);


            if (selectedUser != null)
            {

                string newName = fUser.txt_name.Text;
                string newUsername = fUser.txt_username.Text;
                string newPhone = fUser.txt_phone.Text;
                string newEmail = fUser.txt_email.Text;

                try
                {

                    User updatedUser = r.PutUser(selectedUser, selectedUser.UserId, newName, newUsername, newPhone, newEmail);

                    
                    if (updatedUser == null)
                    {
                        MessageBox.Show("Error updating user", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                    else
                    {
                        MessageBox.Show("User updated successfully", "Success", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error updating user: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                finally
                {
                   
                    fUser.Close();

                   
                    LoadData();
                }
            }
            else
            {
                MessageBox.Show("No user selected to modify", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }
        private void backUserForm(object sender, EventArgs e)
        {
            // fUser.Dispose();
            fUser.pictureBox1.Image = null;
            fUser.Close();
        }
        private void openFormUser(object sender, EventArgs e)
        {
            Button btnAux = sender as Button;

            uAux.RegisterDate = DateTime.Now;



            if (btnAux.Text.Equals("Add"))
            {
                //   MessageBox.Show("Hola mundo", "HGDASJKLDSL", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                openAddForm();
                

            }

            if (btnAux.Text.Equals("Modify"))
            {
                //  MessageBox.Show("Hola modify", "HGDASJKLDSL", MessageBoxButtons.OK, MessageBoxIcon.Warning);
               //  refreshUserImage();
                // fUser.txt_registerDate.Text = uAux.RegisterDate.ToString();
                User selectedUser = r.GetUserById((int)selectedDGV_User().UserId);
                openModifyForm(selectedUser);

            }

        }
        private void addImage(object sender, EventArgs e)
        {
           
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "Archivos de imagen (*.jpg, *.png)|*.jpg;*.png";
            openFileDialog.Multiselect = false;

            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                string fileName = openFileDialog.FileName;
                Bitmap originalImage = (Bitmap)Image.FromFile(fileName);                
                Bitmap resizedImage = new Bitmap(originalImage, new Size(fUser.pictureBox1.Width, fUser.pictureBox1.Height));
                fUser.pictureBox1.Image = resizedImage;            

                using (var stream = System.IO.File.OpenRead(fileName))
                {

                    imageAux.ProfileImage = new FormFile(stream, 0, stream.Length, null, Path.GetFileName(stream.Name));

                }
            }
        }
        private void deleteSelectedUsers(object sender, EventArgs e)
        {
            DataGridViewSelectedRowCollection selectedRows = f.dtg_client.SelectedRows;

            if (selectedRows.Count > 0)
            {
                DialogResult result = MessageBox.Show("Are you sure you want to delete the selected users?", "Confirm Deletion", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

                if (result == DialogResult.Yes)
                {
                    List<int> selectedUserIds = new List<int>();

                    foreach (DataGridViewRow row in selectedRows)
                    {
                        int userId = Convert.ToInt32(row.Cells["UserID"].Value);
                        selectedUserIds.Add(userId);
                    }

                    int deletedCount = 0; 
                    try
                    {
                        foreach (int userId in selectedUserIds)
                        {
                            r.DeleteUserById(userId);
                            deletedCount++;
                        }

                        LoadData();

                        MessageBox.Show($"{deletedCount} user(s) deleted successfully", "Success", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Error deleting selected users: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            else
            {
                
                MessageBox.Show("No users selected for deletion", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }      
        private void openModifyForm(User selectedUser)
        {
            clearForm();
            fUser.btn_add.Visible = false;
            fUser.btn_modify.Visible = true;
            fUser.txt_name.Text = r.GetUserById((int)selectedUser.UserId).Name;
            fUser.txt_username.Text = r.GetUserById((int)selectedUser.UserId).Username;
            fUser.txt_pass.Text = r.GetUserById((int)selectedUser.UserId).Password;
            fUser.txt_pass.Enabled = false;
           
            
            fUser.txt_email.Text = selectedUser.Email;
            fUser.txt_phone.Text = selectedUser.PhoneNumber;
            fUser.txt_registerDate.Text = selectedUser.RegisterDate.ToString();
            fUser.txt_lastConexion.Text = selectedUser.LastConnection.ToString();
            fUser.txt_registerDate.Enabled = false;
            fUser.txt_lastConexion.Enabled = false;

            if (!String.IsNullOrEmpty(selectedUser.GoogleToken)) {
                fUser.btn_add_image.Enabled = false;
                fUser.pictureBox1.Image = r.GetGoogleImage(selectedUser);

            } else
            {
                Image userImage = r.GetImageLocal(selectedUser.ProfileImage.ToString());
               // fUser.pictureBox1.Image = r.GetImageLocal(selectedUser.ProfileImage.ToString());
                fUser.pictureBox1.SizeMode = PictureBoxSizeMode.Zoom;
                fUser.pictureBox1.Image = userImage;
                fUser.btn_add_image.Enabled = true;
            }
            fUser.ShowDialog();
        }
        private void modifyUserByDoubleClick(object sender, DataGridViewCellEventArgs e)
        {

            User selectedUser = r.GetUserById((int)selectedDGV_User().UserId);
            openModifyForm(selectedUser);
        }
        private UserDTO selectedDGV_User()
        {
            int rowIndex = f.dtg_client.CurrentCell.RowIndex;

            if (rowIndex >= 0 && rowIndex < f.dtg_client.Rows.Count)
            {
                DataGridViewRow selectedRow = f.dtg_client.Rows[rowIndex];
                return selectedRow.DataBoundItem as UserDTO;
            }

            return null;
        }
        private void openAddForm()
        {
            clearForm();
            fUser.btn_add.Visible = true;
            fUser.btn_modify.Visible = false;
            fUser.txt_pass.Enabled = true;
            fUser.txt_registerDate.Enabled = true;
            fUser.txt_lastConexion.Enabled = true;
            fUser.btn_add_image.Enabled = true;
            fUser.ShowDialog();
        }
        private void dtg_client_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            var column = f.dtg_client.Columns[e.ColumnIndex].Name;
            if (column == "GoogleToken" || column == "FacebookToken" || column == "ProfileImage")
            {
                if (e.Value != null && e.Value.ToString() == "1")
                {
                    e.CellStyle.BackColor = Color.LightBlue;
                    e.CellStyle.ForeColor = Color.LightBlue;
                }
                else
                {
                    e.CellStyle.BackColor = Color.LightGray;
                    e.CellStyle.ForeColor = Color.LightGray;
                }
            }
        }
        private void clearForm()
        {

            fUser.txt_name.Text = string.Empty;
            fUser.txt_username.Text = string.Empty;
            fUser.txt_pass.Text = string.Empty;
            fUser.txt_email.Text = string.Empty;
            fUser.txt_phone.Text = string.Empty;
            fUser.txt_registerDate.Text = string.Empty;
            fUser.txt_lastConexion.Text = string.Empty;

            fUser.pictureBox1.Image = null;
        }

        // ITEM
        private void orderItem(object sender, EventArgs e)
        {
            String selectedOrder = f.combo_item.Text.ToString();

            categoryController.orderByComboItem(selectedOrder);
        }
        private void deleteSelectedItems(object sender, EventArgs e)
        {
            DataGridViewSelectedRowCollection selectedRows = f.dtg_item.SelectedRows;

            if (selectedRows.Count > 0)
            {
                DialogResult result = MessageBox.Show("Are you sure you want to delete the selected items?", "Confirm Deletion", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

                if (result == DialogResult.Yes)
                {
                    List<int> selectedItemsIds = new List<int>();

                    foreach (DataGridViewRow row in selectedRows)
                    {
                        int itemId = Convert.ToInt32(row.Cells["ItemId"].Value);
                        selectedItemsIds.Add(itemId);
                    }

                    int deletedCount = 0;

                    try
                    {
                        foreach (int itemId in selectedItemsIds)
                        {
                            r.DeleteItemById(itemId);
                            deletedCount++;
                        }

                        LoadData();

                        MessageBox.Show($"{deletedCount} item(s) deleted successfully", "Success", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Error deleting selected items: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            else
            {
                MessageBox.Show("No items selected for deletion", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }
        private void searchItem(object sender, EventArgs e)
        {
            string actualText = f.txt_search_item.Text.ToString();

            if (string.IsNullOrEmpty(actualText))
            {
                categoryController.defaultDGV_items();
            }
            else
            {
                List<Item> filteredItemList = itemList.Where(i => i.Name.ToLower().Contains(actualText.ToLower())).ToList();
                f.dtg_item.DataSource = filteredItemList;
            }
        }


        // CATEGORY
        private void orderCategory(object sender, EventArgs e)
        {
            String selectedOrder = f.combo_cat.Text.ToString();
            categoryController.orderByComboCategory(selectedOrder);
        }
        private void searchCategory(object sender, EventArgs e)
        {
            categoryController.categoryFilter(f.txt_search_cat.Text.ToString());
        }
        private void deleteSelectedCategories(object sender, EventArgs e)
        {
            DataGridViewSelectedRowCollection selectedRows = f.dtg_cat.SelectedRows;

            if (selectedRows.Count > 0)
            {
                DialogResult result = MessageBox.Show("Are you sure you want to delete the selected categories?", "Confirm Deletion", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

                if (result == DialogResult.Yes)
                {
                    List<int> selectedCategoriesIds = new List<int>();

                    foreach (DataGridViewRow row in selectedRows)
                    {
                        int categoryId = Convert.ToInt32(row.Cells["CategoryId"].Value); 
                        selectedCategoriesIds.Add(categoryId);
                    }

                    int deletedCount = 0; 

                    try
                    {
                        foreach (int categoryId in selectedCategoriesIds)
                        {
                            if (r.CategoryHasItem(categoryId))
                            {
                                MessageBox.Show($"Category ID: {categoryId} has associated items. Impossible to delete.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);

                            }
                            else
                            {
                                r.DeleteCategoriesById(categoryId);
                                deletedCount++;
                            }
                           
                        }

                        LoadData();

                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Error deleting selected users: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            else
            {
               
                MessageBox.Show("No users selected for deletion", "Information", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }
        private void resetItemDGV(object sender, EventArgs e)
        {
            categoryController.defaultDGV_items();

        }


        // Other methods

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
