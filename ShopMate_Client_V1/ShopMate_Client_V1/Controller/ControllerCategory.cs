using ShopMate_Client_V1.Model;
using ShopMate_Client_V1.View;
using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ShopMate_Client_V1.Controller
{
  
    public class ControllerCategory
    {
        Category cAux;
        Item iAux;
        FormCategory fCatForm;
        FormItem fItemForm;
        Repository r;
        DataGridView dtgCat;
        DataGridView dtgItem;
        List<Category> categoryList;
        List<Item> itemList;

        public ControllerCategory(Repository r, DataGridView dtg_cat, DataGridView dtg_item) {

             cAux = new Category();
            iAux = new Item();
             fCatForm = new FormCategory();
            fItemForm = new FormItem();
             dtgCat = dtg_cat;
             dtgItem = dtg_item;
             this.r = r;
             categoryList = r.GetCategories();
            itemList = r.GetItems();

            LoadData();
            InitListeners();

        }

        private void LoadData()
        {
            dtgCat.DataSource = categoryList;
            dtgItem.DataSource = itemList;            

        }

        internal void openFormCategory(object sender, EventArgs e)
        {
            
                Button btnAux = sender as Button;
            

                if (btnAux.Text.Equals("Add"))
                {

                fCatForm.btn_addCat.Text = "Add category";
                fCatForm.ShowDialog();
                   
                }

         }

        public void InitListeners()
        {
            // CATEGORY FORM-----------------------------
            fCatForm.btn_add_image_cat.Click += addImage;            
            dtgCat.CellDoubleClick += modifyCategory;
            fCatForm.btn_addCat.Click += postCategory;
            fCatForm.btn_back_fCat.Click += goBack;
            // ITEM FORM-----------------------------
            fItemForm.btn_add.Click += postItem;

        }

        private void postItem(object sender, EventArgs e)
        {
            String selectedCategoryName = fItemForm.combo_category.Text.ToString();
            Category selectedCategory = categoryList.FirstOrDefault(c => c.Name.Equals(selectedCategoryName));

            iAux.Name = fItemForm.txt_name.Text.ToString();           
            iAux.Category = selectedCategory;
            iAux.CategoryId = selectedCategory.CategoryId;
            r.PostItem(iAux);
           


        }

        private void modifyCategory(object sender, DataGridViewCellEventArgs e)
        {
            fCatForm.btn_addCat.Text = "Modify category";

            if (e.RowIndex >= 0)
            {
                DataGridViewRow selectedRow = dtgCat.Rows[e.RowIndex];
                Category categorySelected = selectedRow.DataBoundItem as Category; 

                if (categorySelected != null)
                {
                    fCatForm.txt_name.Text = categorySelected.Name.ToString();
                    // fCatForm.pictureBox_cat =

                }         
               
            }
            fCatForm.ShowDialog();

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
                Bitmap resizedImage = new Bitmap(originalImage, new Size(fCatForm.pictureBox_cat.Width, fCatForm.pictureBox_cat.Height));
                fCatForm.pictureBox_cat.Image = resizedImage;               
            }
        }

        private void postCategory(object sender, EventArgs e)
        {
           cAux.Name = fCatForm.Text.ToString();            
           // cAux.Icon = fCatForm.pictureBox_cat.ToString();
           cAux.UpdatedAt = DateTime.Now; 
           cAux.CreatedAt = DateTime.Now;

            r.PostCategory(cAux);
            LoadData();
            goBack(sender, e);
            
        }

        private void goBack(object sender, EventArgs e)
        {
            fCatForm.Close();
        }

      

        internal void Filter(string v)
        {
            if (string.IsNullOrWhiteSpace(v))
            {
                LoadData();
                return;
            }
            categoryList = categoryList.Where(c => c.Name.ToLower().Contains(v.ToLower())).ToList();
            dtgCat.DataSource = categoryList;
        }

        internal void orderByComboCategory(string selectedOrder)
        {
            if(!String.IsNullOrEmpty(selectedOrder))
            {
                try
                {

                    switch (selectedOrder)
                    {
                        case "Name":

                            categoryList = categoryList.OrderBy(c => c.Name).ToList();
                            break;

                        case "Last Update":
                            categoryList = categoryList.OrderBy(c => c.UpdatedAt).ToList();
                            break;

                        case "Register Date":

                            categoryList = categoryList.OrderBy(c => c.CreatedAt).ToList();
                            break;
                           
                    }
                    dtgCat.DataSource = categoryList;
                }
             
                 catch
                        {
                    MessageBox.Show("CAN'T CONNECT TO THE API", "Empty fields", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                        }
             
                                
              
            }
            
        }

        internal void openFormItem(object sender, EventArgs e)
        {
           fItemForm.combo_category.DataSource = categoryList.Select(c => c.Name).ToList();
           fItemForm.ShowDialog(); 
        }

        internal void orderByComboItem(string selectedOrder)
        {
            
        }

        //internal void showJustCheckedItems(bool isChecked)
        //{
        //    ulong booleanValue = isChecked ? 1UL : 0UL;
        //    itemList = itemList.Where(i => i.IsDefault == booleanValue).ToList();
        //    dtgItem.DataSource = itemList;
        //}
    }
}

