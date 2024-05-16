namespace ShopMate_Client_V1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.label7 = new System.Windows.Forms.Label();
            this.lbl_order_it = new System.Windows.Forms.Label();
            this.combo_user = new System.Windows.Forms.ComboBox();
            this.lbl_search_it = new System.Windows.Forms.Label();
            this.txt_search_user = new System.Windows.Forms.TextBox();
            this.btn_delete_user = new System.Windows.Forms.Button();
            this.btn_modify_user = new System.Windows.Forms.Button();
            this.btn_add_user = new System.Windows.Forms.Button();
            this.dtg_client = new System.Windows.Forms.DataGridView();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.lbl_categoryName = new System.Windows.Forms.Label();
            this.btn_showAll_it = new System.Windows.Forms.Button();
            this.btn_delete_item = new System.Windows.Forms.Button();
            this.label6 = new System.Windows.Forms.Label();
            this.txt_search_item = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.combo_item = new System.Windows.Forms.ComboBox();
            this.btn_add_item = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.dtg_item = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.combo_cat = new System.Windows.Forms.ComboBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txt_search_cat = new System.Windows.Forms.TextBox();
            this.btn_delete_cat = new System.Windows.Forms.Button();
            this.btn_add_cat = new System.Windows.Forms.Button();
            this.dtg_cat = new System.Windows.Forms.DataGridView();
            this.panel1 = new System.Windows.Forms.Panel();
            this.btn_close_form = new System.Windows.Forms.Button();
            this.label8 = new System.Windows.Forms.Label();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_client)).BeginInit();
            this.tabPage2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_item)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_cat)).BeginInit();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Location = new System.Drawing.Point(2, 47);
            this.tabControl1.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.tabControl1.Multiline = true;
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(1482, 621);
            this.tabControl1.TabIndex = 0;
            // 
            // tabPage1
            // 
            this.tabPage1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(44)))), ((int)(((byte)(94)))), ((int)(((byte)(136)))));
            this.tabPage1.Controls.Add(this.btn_delete_user);
            this.tabPage1.Controls.Add(this.btn_modify_user);
            this.tabPage1.Controls.Add(this.btn_add_user);
            this.tabPage1.Controls.Add(this.label7);
            this.tabPage1.Controls.Add(this.lbl_order_it);
            this.tabPage1.Controls.Add(this.combo_user);
            this.tabPage1.Controls.Add(this.lbl_search_it);
            this.tabPage1.Controls.Add(this.txt_search_user);
            this.tabPage1.Controls.Add(this.dtg_client);
            this.tabPage1.Location = new System.Drawing.Point(4, 24);
            this.tabPage1.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.tabPage1.Size = new System.Drawing.Size(1474, 593);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "Users";
            this.tabPage1.Click += new System.EventHandler(this.tabPage1_Click);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.label7.Location = new System.Drawing.Point(18, 18);
            this.label7.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(78, 24);
            this.label7.TabIndex = 17;
            this.label7.Text = "USERS";
            this.label7.Click += new System.EventHandler(this.label7_Click);
            // 
            // lbl_order_it
            // 
            this.lbl_order_it.AutoSize = true;
            this.lbl_order_it.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.lbl_order_it.Location = new System.Drawing.Point(758, 54);
            this.lbl_order_it.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbl_order_it.Name = "lbl_order_it";
            this.lbl_order_it.Size = new System.Drawing.Size(56, 15);
            this.lbl_order_it.TabIndex = 7;
            this.lbl_order_it.Text = "Order by:";
            // 
            // combo_user
            // 
            this.combo_user.FormattingEnabled = true;
            this.combo_user.Location = new System.Drawing.Point(823, 49);
            this.combo_user.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.combo_user.Name = "combo_user";
            this.combo_user.Size = new System.Drawing.Size(171, 23);
            this.combo_user.TabIndex = 6;
            // 
            // lbl_search_it
            // 
            this.lbl_search_it.AutoSize = true;
            this.lbl_search_it.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.lbl_search_it.Location = new System.Drawing.Point(249, 54);
            this.lbl_search_it.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbl_search_it.Name = "lbl_search_it";
            this.lbl_search_it.Size = new System.Drawing.Size(48, 15);
            this.lbl_search_it.TabIndex = 5;
            this.lbl_search_it.Text = "Search :";
            // 
            // txt_search_user
            // 
            this.txt_search_user.Location = new System.Drawing.Point(324, 49);
            this.txt_search_user.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txt_search_user.Name = "txt_search_user";
            this.txt_search_user.Size = new System.Drawing.Size(193, 23);
            this.txt_search_user.TabIndex = 4;
            // 
            // btn_delete_user
            // 
            this.btn_delete_user.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btn_delete_user.BackgroundImage")));
            this.btn_delete_user.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.btn_delete_user.FlatAppearance.BorderSize = 0;
            this.btn_delete_user.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btn_delete_user.Location = new System.Drawing.Point(936, 407);
            this.btn_delete_user.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_delete_user.Name = "btn_delete_user";
            this.btn_delete_user.Size = new System.Drawing.Size(58, 37);
            this.btn_delete_user.TabIndex = 3;
            this.btn_delete_user.UseVisualStyleBackColor = true;
            // 
            // btn_modify_user
            // 
            this.btn_modify_user.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btn_modify_user.BackgroundImage")));
            this.btn_modify_user.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.btn_modify_user.FlatAppearance.BorderSize = 0;
            this.btn_modify_user.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btn_modify_user.Font = new System.Drawing.Font("Segoe UI", 1E-05F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_modify_user.Location = new System.Drawing.Point(73, 409);
            this.btn_modify_user.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_modify_user.Name = "btn_modify_user";
            this.btn_modify_user.Size = new System.Drawing.Size(44, 35);
            this.btn_modify_user.TabIndex = 2;
            this.btn_modify_user.Text = "Modify";
            this.btn_modify_user.UseVisualStyleBackColor = true;
            // 
            // btn_add_user
            // 
            this.btn_add_user.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(44)))), ((int)(((byte)(94)))), ((int)(((byte)(136)))));
            this.btn_add_user.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btn_add_user.BackgroundImage")));
            this.btn_add_user.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.btn_add_user.FlatAppearance.BorderSize = 0;
            this.btn_add_user.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btn_add_user.Font = new System.Drawing.Font("Segoe UI", 1F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_add_user.ForeColor = System.Drawing.Color.Transparent;
            this.btn_add_user.Location = new System.Drawing.Point(22, 407);
            this.btn_add_user.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_add_user.Name = "btn_add_user";
            this.btn_add_user.Size = new System.Drawing.Size(43, 37);
            this.btn_add_user.TabIndex = 1;
            this.btn_add_user.Text = "Add";
            this.btn_add_user.UseVisualStyleBackColor = false;
            // 
            // dtg_client
            // 
            this.dtg_client.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(58)))), ((int)(((byte)(95)))));
            this.dtg_client.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dtg_client.CellBorderStyle = System.Windows.Forms.DataGridViewCellBorderStyle.SunkenVertical;
            this.dtg_client.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dtg_client.GridColor = System.Drawing.SystemColors.ActiveCaption;
            this.dtg_client.Location = new System.Drawing.Point(22, 78);
            this.dtg_client.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dtg_client.Name = "dtg_client";
            this.dtg_client.Size = new System.Drawing.Size(972, 322);
            this.dtg_client.TabIndex = 0;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.lbl_categoryName);
            this.tabPage2.Controls.Add(this.btn_showAll_it);
            this.tabPage2.Controls.Add(this.btn_delete_item);
            this.tabPage2.Controls.Add(this.label6);
            this.tabPage2.Controls.Add(this.txt_search_item);
            this.tabPage2.Controls.Add(this.label5);
            this.tabPage2.Controls.Add(this.combo_item);
            this.tabPage2.Controls.Add(this.btn_add_item);
            this.tabPage2.Controls.Add(this.label4);
            this.tabPage2.Controls.Add(this.label3);
            this.tabPage2.Controls.Add(this.dtg_item);
            this.tabPage2.Controls.Add(this.label1);
            this.tabPage2.Controls.Add(this.combo_cat);
            this.tabPage2.Controls.Add(this.label2);
            this.tabPage2.Controls.Add(this.txt_search_cat);
            this.tabPage2.Controls.Add(this.btn_delete_cat);
            this.tabPage2.Controls.Add(this.btn_add_cat);
            this.tabPage2.Controls.Add(this.dtg_cat);
            this.tabPage2.Location = new System.Drawing.Point(4, 24);
            this.tabPage2.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.tabPage2.Size = new System.Drawing.Size(1474, 593);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Categories";
            this.tabPage2.UseVisualStyleBackColor = true;
            this.tabPage2.Click += new System.EventHandler(this.tabPage2_Click);
            // 
            // lbl_categoryName
            // 
            this.lbl_categoryName.AutoSize = true;
            this.lbl_categoryName.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl_categoryName.Location = new System.Drawing.Point(1294, 42);
            this.lbl_categoryName.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lbl_categoryName.Name = "lbl_categoryName";
            this.lbl_categoryName.Size = new System.Drawing.Size(0, 17);
            this.lbl_categoryName.TabIndex = 26;
            // 
            // btn_showAll_it
            // 
            this.btn_showAll_it.Location = new System.Drawing.Point(918, 80);
            this.btn_showAll_it.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_showAll_it.Name = "btn_showAll_it";
            this.btn_showAll_it.Size = new System.Drawing.Size(88, 27);
            this.btn_showAll_it.TabIndex = 25;
            this.btn_showAll_it.Text = "Show All";
            this.btn_showAll_it.UseVisualStyleBackColor = true;
            // 
            // btn_delete_item
            // 
            this.btn_delete_item.Location = new System.Drawing.Point(1378, 76);
            this.btn_delete_item.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_delete_item.Name = "btn_delete_item";
            this.btn_delete_item.Size = new System.Drawing.Size(88, 27);
            this.btn_delete_item.TabIndex = 23;
            this.btn_delete_item.Text = "Delete";
            this.btn_delete_item.UseVisualStyleBackColor = true;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(1031, 27);
            this.label6.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(48, 15);
            this.label6.TabIndex = 22;
            this.label6.Text = "Search :";
            // 
            // txt_search_item
            // 
            this.txt_search_item.Location = new System.Drawing.Point(1105, 23);
            this.txt_search_item.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txt_search_item.Name = "txt_search_item";
            this.txt_search_item.Size = new System.Drawing.Size(171, 23);
            this.txt_search_item.TabIndex = 21;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(1042, 82);
            this.label5.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(56, 15);
            this.label5.TabIndex = 20;
            this.label5.Text = "Order by:";
            // 
            // combo_item
            // 
            this.combo_item.FormattingEnabled = true;
            this.combo_item.Location = new System.Drawing.Point(1105, 80);
            this.combo_item.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.combo_item.Name = "combo_item";
            this.combo_item.Size = new System.Drawing.Size(171, 23);
            this.combo_item.TabIndex = 19;
            // 
            // btn_add_item
            // 
            this.btn_add_item.Location = new System.Drawing.Point(1283, 77);
            this.btn_add_item.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_add_item.Name = "btn_add_item";
            this.btn_add_item.Size = new System.Drawing.Size(88, 27);
            this.btn_add_item.TabIndex = 18;
            this.btn_add_item.Text = "Add";
            this.btn_add_item.UseVisualStyleBackColor = true;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(760, 80);
            this.label4.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(59, 24);
            this.label4.TabIndex = 17;
            this.label4.Text = "ITEM";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(22, 80);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(123, 24);
            this.label3.TabIndex = 16;
            this.label3.Text = "CATEGORY";
            // 
            // dtg_item
            // 
            this.dtg_item.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dtg_item.Location = new System.Drawing.Point(757, 111);
            this.dtg_item.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dtg_item.Name = "dtg_item";
            this.dtg_item.Size = new System.Drawing.Size(708, 473);
            this.dtg_item.TabIndex = 15;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(306, 80);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(56, 15);
            this.label1.TabIndex = 14;
            this.label1.Text = "Order by:";
            // 
            // combo_cat
            // 
            this.combo_cat.FormattingEnabled = true;
            this.combo_cat.Location = new System.Drawing.Point(369, 77);
            this.combo_cat.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.combo_cat.Name = "combo_cat";
            this.combo_cat.Size = new System.Drawing.Size(171, 23);
            this.combo_cat.TabIndex = 13;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(295, 23);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(48, 15);
            this.label2.TabIndex = 12;
            this.label2.Text = "Search :";
            // 
            // txt_search_cat
            // 
            this.txt_search_cat.Location = new System.Drawing.Point(369, 20);
            this.txt_search_cat.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.txt_search_cat.Name = "txt_search_cat";
            this.txt_search_cat.Size = new System.Drawing.Size(171, 23);
            this.txt_search_cat.TabIndex = 11;
            // 
            // btn_delete_cat
            // 
            this.btn_delete_cat.Location = new System.Drawing.Point(642, 77);
            this.btn_delete_cat.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_delete_cat.Name = "btn_delete_cat";
            this.btn_delete_cat.Size = new System.Drawing.Size(88, 27);
            this.btn_delete_cat.TabIndex = 10;
            this.btn_delete_cat.Text = "Delete";
            this.btn_delete_cat.UseVisualStyleBackColor = true;
            // 
            // btn_add_cat
            // 
            this.btn_add_cat.Location = new System.Drawing.Point(547, 77);
            this.btn_add_cat.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.btn_add_cat.Name = "btn_add_cat";
            this.btn_add_cat.Size = new System.Drawing.Size(88, 27);
            this.btn_add_cat.TabIndex = 8;
            this.btn_add_cat.Text = "Add";
            this.btn_add_cat.UseVisualStyleBackColor = true;
            // 
            // dtg_cat
            // 
            this.dtg_cat.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dtg_cat.Location = new System.Drawing.Point(21, 111);
            this.dtg_cat.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.dtg_cat.Name = "dtg_cat";
            this.dtg_cat.Size = new System.Drawing.Size(708, 473);
            this.dtg_cat.TabIndex = 1;
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(0)))), ((int)(((byte)(58)))), ((int)(((byte)(95)))));
            this.panel1.Controls.Add(this.pictureBox1);
            this.panel1.Controls.Add(this.label8);
            this.panel1.Controls.Add(this.btn_close_form);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1512, 30);
            this.panel1.TabIndex = 1;
            this.panel1.Paint += new System.Windows.Forms.PaintEventHandler(this.panel1_Paint);
            // 
            // btn_close_form
            // 
            this.btn_close_form.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("btn_close_form.BackgroundImage")));
            this.btn_close_form.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.btn_close_form.Dock = System.Windows.Forms.DockStyle.Right;
            this.btn_close_form.FlatAppearance.BorderSize = 0;
            this.btn_close_form.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btn_close_form.Location = new System.Drawing.Point(1494, 0);
            this.btn_close_form.Name = "btn_close_form";
            this.btn_close_form.Size = new System.Drawing.Size(18, 30);
            this.btn_close_form.TabIndex = 0;
            this.btn_close_form.UseVisualStyleBackColor = true;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.ForeColor = System.Drawing.SystemColors.ButtonHighlight;
            this.label8.Location = new System.Drawing.Point(682, 9);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(96, 21);
            this.label8.TabIndex = 1;
            this.label8.Text = "SHOPMATE";
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pictureBox1.BackgroundImage")));
            this.pictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.pictureBox1.Location = new System.Drawing.Point(784, 3);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(36, 27);
            this.pictureBox1.TabIndex = 18;
            this.pictureBox1.TabStop = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.ClientSize = new System.Drawing.Size(1512, 640);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.tabControl1);
            this.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Margin = new System.Windows.Forms.Padding(4, 3, 4, 3);
            this.Name = "Form1";
            this.Text = "Form1";
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_client)).EndInit();
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_item)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_cat)).EndInit();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion
        public System.Windows.Forms.TabPage tabPage1;
        public System.Windows.Forms.Button btn_delete_user;
        public System.Windows.Forms.Button btn_modify_user;
        public System.Windows.Forms.Button btn_add_user;
        public System.Windows.Forms.DataGridView dtg_client;
        public System.Windows.Forms.Label lbl_order_it;
        public System.Windows.Forms.Label lbl_search_it;
        public System.Windows.Forms.TabControl tabControl1;
        public System.Windows.Forms.TabPage tabPage2;
        public System.Windows.Forms.ComboBox combo_user;
        public System.Windows.Forms.TextBox txt_search_user;
        public System.Windows.Forms.Label label1;
        public System.Windows.Forms.ComboBox combo_cat;
        public System.Windows.Forms.Label label2;
        public System.Windows.Forms.TextBox txt_search_cat;
        public System.Windows.Forms.Button btn_delete_cat;
        public System.Windows.Forms.Button btn_add_cat;
        public System.Windows.Forms.DataGridView dtg_cat;
        public System.Windows.Forms.Label label4;
        public System.Windows.Forms.Label label3;
        public System.Windows.Forms.DataGridView dtg_item;
        public System.Windows.Forms.Label label6;
        public System.Windows.Forms.TextBox txt_search_item;
        public System.Windows.Forms.Label label5;
        public System.Windows.Forms.ComboBox combo_item;
        public System.Windows.Forms.Button btn_add_item;
        public System.Windows.Forms.Button btn_delete_item;
        public System.Windows.Forms.Label label7;
        public System.Windows.Forms.Button btn_showAll_it;
        public System.Windows.Forms.Label lbl_categoryName;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button btn_close_form;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.PictureBox pictureBox1;
    }
}

