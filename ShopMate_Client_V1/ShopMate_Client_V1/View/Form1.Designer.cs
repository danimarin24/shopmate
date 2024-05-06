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
            this.check_isDefault = new System.Windows.Forms.CheckBox();
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
            this.button1 = new System.Windows.Forms.Button();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_client)).BeginInit();
            this.tabPage2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_item)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dtg_cat)).BeginInit();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Location = new System.Drawing.Point(14, 12);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(1270, 538);
            this.tabControl1.TabIndex = 0;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.label7);
            this.tabPage1.Controls.Add(this.lbl_order_it);
            this.tabPage1.Controls.Add(this.combo_user);
            this.tabPage1.Controls.Add(this.lbl_search_it);
            this.tabPage1.Controls.Add(this.txt_search_user);
            this.tabPage1.Controls.Add(this.btn_delete_user);
            this.tabPage1.Controls.Add(this.btn_modify_user);
            this.tabPage1.Controls.Add(this.btn_add_user);
            this.tabPage1.Controls.Add(this.dtg_client);
            this.tabPage1.Location = new System.Drawing.Point(4, 22);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(1262, 512);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "Users";
            this.tabPage1.UseVisualStyleBackColor = true;
            this.tabPage1.Click += new System.EventHandler(this.tabPage1_Click);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(14, 3);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(78, 24);
            this.label7.TabIndex = 17;
            this.label7.Text = "USERS";
            this.label7.Click += new System.EventHandler(this.label7_Click);
            // 
            // lbl_order_it
            // 
            this.lbl_order_it.AutoSize = true;
            this.lbl_order_it.Location = new System.Drawing.Point(395, 53);
            this.lbl_order_it.Name = "lbl_order_it";
            this.lbl_order_it.Size = new System.Drawing.Size(50, 13);
            this.lbl_order_it.TabIndex = 7;
            this.lbl_order_it.Text = "Order by:";
            // 
            // combo_user
            // 
            this.combo_user.FormattingEnabled = true;
            this.combo_user.Location = new System.Drawing.Point(451, 50);
            this.combo_user.Name = "combo_user";
            this.combo_user.Size = new System.Drawing.Size(147, 21);
            this.combo_user.TabIndex = 6;
            // 
            // lbl_search_it
            // 
            this.lbl_search_it.AutoSize = true;
            this.lbl_search_it.Location = new System.Drawing.Point(15, 55);
            this.lbl_search_it.Name = "lbl_search_it";
            this.lbl_search_it.Size = new System.Drawing.Size(47, 13);
            this.lbl_search_it.TabIndex = 5;
            this.lbl_search_it.Text = "Search :";
            // 
            // txt_search_user
            // 
            this.txt_search_user.Location = new System.Drawing.Point(68, 50);
            this.txt_search_user.Name = "txt_search_user";
            this.txt_search_user.Size = new System.Drawing.Size(166, 20);
            this.txt_search_user.TabIndex = 4;
            // 
            // btn_delete_user
            // 
            this.btn_delete_user.Location = new System.Drawing.Point(829, 48);
            this.btn_delete_user.Name = "btn_delete_user";
            this.btn_delete_user.Size = new System.Drawing.Size(75, 23);
            this.btn_delete_user.TabIndex = 3;
            this.btn_delete_user.Text = "Delete";
            this.btn_delete_user.UseVisualStyleBackColor = true;
            // 
            // btn_modify_user
            // 
            this.btn_modify_user.Location = new System.Drawing.Point(748, 48);
            this.btn_modify_user.Name = "btn_modify_user";
            this.btn_modify_user.Size = new System.Drawing.Size(75, 23);
            this.btn_modify_user.TabIndex = 2;
            this.btn_modify_user.Text = "Modify";
            this.btn_modify_user.UseVisualStyleBackColor = true;
            // 
            // btn_add_user
            // 
            this.btn_add_user.Location = new System.Drawing.Point(667, 48);
            this.btn_add_user.Name = "btn_add_user";
            this.btn_add_user.Size = new System.Drawing.Size(75, 23);
            this.btn_add_user.TabIndex = 1;
            this.btn_add_user.Text = "Add";
            this.btn_add_user.UseVisualStyleBackColor = true;
            // 
            // dtg_client
            // 
            this.dtg_client.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dtg_client.Location = new System.Drawing.Point(18, 96);
            this.dtg_client.Name = "dtg_client";
            this.dtg_client.Size = new System.Drawing.Size(1238, 410);
            this.dtg_client.TabIndex = 0;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.button1);
            this.tabPage2.Controls.Add(this.check_isDefault);
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
            this.tabPage2.Location = new System.Drawing.Point(4, 22);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(1262, 512);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Categories";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // check_isDefault
            // 
            this.check_isDefault.AutoSize = true;
            this.check_isDefault.Location = new System.Drawing.Point(672, 20);
            this.check_isDefault.Name = "check_isDefault";
            this.check_isDefault.Size = new System.Drawing.Size(88, 17);
            this.check_isDefault.TabIndex = 24;
            this.check_isDefault.Text = "Default Items";
            this.check_isDefault.UseVisualStyleBackColor = true;
            // 
            // btn_delete_item
            // 
            this.btn_delete_item.Location = new System.Drawing.Point(1181, 66);
            this.btn_delete_item.Name = "btn_delete_item";
            this.btn_delete_item.Size = new System.Drawing.Size(75, 23);
            this.btn_delete_item.TabIndex = 23;
            this.btn_delete_item.Text = "Delete";
            this.btn_delete_item.UseVisualStyleBackColor = true;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(884, 23);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(47, 13);
            this.label6.TabIndex = 22;
            this.label6.Text = "Search :";
            // 
            // txt_search_item
            // 
            this.txt_search_item.Location = new System.Drawing.Point(947, 20);
            this.txt_search_item.Name = "txt_search_item";
            this.txt_search_item.Size = new System.Drawing.Size(147, 20);
            this.txt_search_item.TabIndex = 21;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(893, 71);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(50, 13);
            this.label5.TabIndex = 20;
            this.label5.Text = "Order by:";
            // 
            // combo_item
            // 
            this.combo_item.FormattingEnabled = true;
            this.combo_item.Location = new System.Drawing.Point(947, 69);
            this.combo_item.Name = "combo_item";
            this.combo_item.Size = new System.Drawing.Size(147, 21);
            this.combo_item.TabIndex = 19;
            // 
            // btn_add_item
            // 
            this.btn_add_item.Location = new System.Drawing.Point(1100, 67);
            this.btn_add_item.Name = "btn_add_item";
            this.btn_add_item.Size = new System.Drawing.Size(75, 23);
            this.btn_add_item.TabIndex = 18;
            this.btn_add_item.Text = "Add";
            this.btn_add_item.UseVisualStyleBackColor = true;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(651, 69);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(59, 24);
            this.label4.TabIndex = 17;
            this.label4.Text = "ITEM";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(19, 69);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(123, 24);
            this.label3.TabIndex = 16;
            this.label3.Text = "CATEGORY";
            // 
            // dtg_item
            // 
            this.dtg_item.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dtg_item.Location = new System.Drawing.Point(649, 96);
            this.dtg_item.Name = "dtg_item";
            this.dtg_item.Size = new System.Drawing.Size(607, 410);
            this.dtg_item.TabIndex = 15;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(262, 69);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(50, 13);
            this.label1.TabIndex = 14;
            this.label1.Text = "Order by:";
            // 
            // combo_cat
            // 
            this.combo_cat.FormattingEnabled = true;
            this.combo_cat.Location = new System.Drawing.Point(316, 67);
            this.combo_cat.Name = "combo_cat";
            this.combo_cat.Size = new System.Drawing.Size(147, 21);
            this.combo_cat.TabIndex = 13;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(253, 20);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(47, 13);
            this.label2.TabIndex = 12;
            this.label2.Text = "Search :";
            // 
            // txt_search_cat
            // 
            this.txt_search_cat.Location = new System.Drawing.Point(316, 17);
            this.txt_search_cat.Name = "txt_search_cat";
            this.txt_search_cat.Size = new System.Drawing.Size(147, 20);
            this.txt_search_cat.TabIndex = 11;
            // 
            // btn_delete_cat
            // 
            this.btn_delete_cat.Location = new System.Drawing.Point(550, 67);
            this.btn_delete_cat.Name = "btn_delete_cat";
            this.btn_delete_cat.Size = new System.Drawing.Size(75, 23);
            this.btn_delete_cat.TabIndex = 10;
            this.btn_delete_cat.Text = "Delete";
            this.btn_delete_cat.UseVisualStyleBackColor = true;
            // 
            // btn_add_cat
            // 
            this.btn_add_cat.Location = new System.Drawing.Point(469, 67);
            this.btn_add_cat.Name = "btn_add_cat";
            this.btn_add_cat.Size = new System.Drawing.Size(75, 23);
            this.btn_add_cat.TabIndex = 8;
            this.btn_add_cat.Text = "Add";
            this.btn_add_cat.UseVisualStyleBackColor = true;
            // 
            // dtg_cat
            // 
            this.dtg_cat.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dtg_cat.Location = new System.Drawing.Point(18, 96);
            this.dtg_cat.Name = "dtg_cat";
            this.dtg_cat.Size = new System.Drawing.Size(607, 410);
            this.dtg_cat.TabIndex = 1;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(787, 69);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 25;
            this.button1.Text = "button1";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1296, 555);
            this.Controls.Add(this.tabControl1);
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
        public System.Windows.Forms.CheckBox check_isDefault;
        private System.Windows.Forms.Button button1;
    }
}

