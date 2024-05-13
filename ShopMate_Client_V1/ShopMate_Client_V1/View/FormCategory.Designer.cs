namespace ShopMate_Client_V1.View
{
    partial class FormCategory
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
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.label1 = new System.Windows.Forms.Label();
            this.txt_name = new System.Windows.Forms.TextBox();
            this.btn_add_image_cat = new System.Windows.Forms.Button();
            this.pictureBox_cat = new System.Windows.Forms.PictureBox();
            this.btn_addCat = new System.Windows.Forms.Button();
            this.btn_back_fCat = new System.Windows.Forms.Button();
            this.flowLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox_cat)).BeginInit();
            this.SuspendLayout();
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
            | System.Windows.Forms.AnchorStyles.Left)
            | System.Windows.Forms.AnchorStyles.Right)));
            this.flowLayoutPanel1.Controls.Add(this.label1);
            this.flowLayoutPanel1.Controls.Add(this.txt_name);
            this.flowLayoutPanel1.Location = new System.Drawing.Point(12, 245);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Padding = new System.Windows.Forms.Padding(20);
            this.flowLayoutPanel1.Size = new System.Drawing.Size(341, 83);
            this.flowLayoutPanel1.TabIndex = 9;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.flowLayoutPanel1.SetFlowBreak(this.label1, true);
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(23, 20);
            this.label1.MinimumSize = new System.Drawing.Size(140, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(140, 20);
            this.label1.TabIndex = 0;
            this.label1.Text = "Category name:";
            // 
            // txt_name
            // 
            this.flowLayoutPanel1.SetFlowBreak(this.txt_name, true);
            this.txt_name.Location = new System.Drawing.Point(23, 43);
            this.txt_name.Name = "txt_name";
            this.txt_name.Size = new System.Drawing.Size(269, 20);
            this.txt_name.TabIndex = 6;
            // 
            // btn_add_image_cat
            // 
            this.btn_add_image_cat.BackColor = System.Drawing.Color.DarkSeaGreen;
            this.btn_add_image_cat.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_add_image_cat.Location = new System.Drawing.Point(138, 168);
            this.btn_add_image_cat.Name = "btn_add_image_cat";
            this.btn_add_image_cat.Size = new System.Drawing.Size(95, 29);
            this.btn_add_image_cat.TabIndex = 19;
            this.btn_add_image_cat.Text = "Add icon";
            this.btn_add_image_cat.UseVisualStyleBackColor = false;
            // 
            // pictureBox_cat
            // 
            this.pictureBox_cat.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.pictureBox_cat.Location = new System.Drawing.Point(123, 42);
            this.pictureBox_cat.MinimumSize = new System.Drawing.Size(120, 120);
            this.pictureBox_cat.Name = "pictureBox_cat";
            this.pictureBox_cat.Size = new System.Drawing.Size(120, 120);
            this.pictureBox_cat.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
            this.pictureBox_cat.TabIndex = 18;
            this.pictureBox_cat.TabStop = false;
            // 
            // btn_addCat
            // 
            this.btn_addCat.BackColor = System.Drawing.Color.DarkSeaGreen;
            this.btn_addCat.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_addCat.Location = new System.Drawing.Point(105, 362);
            this.btn_addCat.Name = "btn_addCat";
            this.btn_addCat.Size = new System.Drawing.Size(149, 38);
            this.btn_addCat.TabIndex = 20;
            this.btn_addCat.Text = "Add category";
            this.btn_addCat.UseVisualStyleBackColor = false;
            // 
            // btn_back_fCat
            // 
            this.btn_back_fCat.BackColor = System.Drawing.Color.Salmon;
            this.btn_back_fCat.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_back_fCat.Location = new System.Drawing.Point(123, 406);
            this.btn_back_fCat.Name = "btn_back_fCat";
            this.btn_back_fCat.Size = new System.Drawing.Size(102, 28);
            this.btn_back_fCat.TabIndex = 21;
            this.btn_back_fCat.Text = "Back";
            this.btn_back_fCat.UseVisualStyleBackColor = false;
            // 
            // FormCategory
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Honeydew;
            this.ClientSize = new System.Drawing.Size(371, 499);
            this.Controls.Add(this.btn_back_fCat);
            this.Controls.Add(this.btn_addCat);
            this.Controls.Add(this.btn_add_image_cat);
            this.Controls.Add(this.pictureBox_cat);
            this.Controls.Add(this.flowLayoutPanel1);
            this.Name = "FormCategory";
            this.Text = "FormCategory";
            this.flowLayoutPanel1.ResumeLayout(false);
            this.flowLayoutPanel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox_cat)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        public System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        public System.Windows.Forms.Label label1;
        public System.Windows.Forms.TextBox txt_name;
        public System.Windows.Forms.Button btn_add_image_cat;
        public System.Windows.Forms.PictureBox pictureBox_cat;
        public System.Windows.Forms.Button btn_addCat;
        public System.Windows.Forms.Button btn_back_fCat;
    }
}