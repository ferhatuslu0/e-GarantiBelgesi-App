using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using MySql.Data;
using MySql.Data.MySqlClient;
using System.IO;

namespace eGarantiBelgesiSunucu
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        MySqlConnection baglanti = new MySqlConnection("SERVER= localhost;DATABASE=sunucuveritabani;SslMode=none;UID=root;PASSWORD=;Convert Zero Datetime=True");
        MySqlCommand komut = new MySqlCommand();
        private void button1_Click(object sender, EventArgs e)
        {

            Font drawFont = new Font("Arial", 16);
            SolidBrush drawBrush = new SolidBrush(Color.Black);

            Graphics g = Graphics.FromImage(pictureBox2.Image);
            Point point = new Point(this.label1.Location.X - 55, this.label1.Location.Y + 248);
            g.DrawString(textBox1.Text, drawFont, drawBrush, point);

            Graphics g2 = Graphics.FromImage(pictureBox2.Image);
            Point point2 = new Point(this.label1.Location.X - 55, this.label1.Location.Y + 282);
            g.DrawString(textBox2.Text, drawFont, drawBrush, point2);

            Graphics g3 = Graphics.FromImage(pictureBox2.Image);
            Point point3 = new Point(this.label1.Location.X - 55, this.label1.Location.Y + 318);
            g.DrawString(textBox3.Text, drawFont, drawBrush, point3);

            Graphics g4 = Graphics.FromImage(pictureBox2.Image);
            Point point4 = new Point(this.label1.Location.X - 55, this.label1.Location.Y + 353);
            g.DrawString(textBox4.Text, drawFont, drawBrush, point4);

            Graphics g5 = Graphics.FromImage(pictureBox2.Image);
            Point point5 = new Point(this.label1.Location.X - 55, this.label1.Location.Y + 386);
            g.DrawString(textBox5.Text, drawFont, drawBrush, point5);

            Graphics g6 = Graphics.FromImage(pictureBox2.Image);
            Point point6 = new Point(this.label1.Location.X - 55, this.label1.Location.Y + 422);
            g.DrawString(textBox6.Text, drawFont, drawBrush, point6);

            Graphics g7 = Graphics.FromImage(pictureBox2.Image);
            Point point7 = new Point(this.label1.Location.X + 275, this.label1.Location.Y + 248);
            g.DrawString(textBox11.Text, drawFont, drawBrush, point7);

            Graphics g8 = Graphics.FromImage(pictureBox2.Image);
            Point point8 = new Point(this.label1.Location.X + 275, this.label1.Location.Y + 282);
            g.DrawString(textBox7.Text, drawFont, drawBrush, point8);

            Graphics g9 = Graphics.FromImage(pictureBox2.Image);
            Point point9 = new Point(this.label1.Location.X + 275, this.label1.Location.Y + 318);
            g.DrawString(textBox8.Text, drawFont, drawBrush, point9);

            Graphics g10 = Graphics.FromImage(pictureBox2.Image);
            Point point10 = new Point(this.label1.Location.X + 275, this.label1.Location.Y + 353);
            g.DrawString(textBox9.Text, drawFont, drawBrush, point10);

            Graphics g11 = Graphics.FromImage(pictureBox2.Image);
            Point point11 = new Point(this.label1.Location.X + 275, this.label1.Location.Y + 386);
            g.DrawString(dateTimePicker1.Text, drawFont, drawBrush, point11);

            Graphics g12 = Graphics.FromImage(pictureBox2.Image);
            Point point12 = new Point(this.label1.Location.X + 275, this.label1.Location.Y + 422);
            g.DrawString(textBox10.Text, drawFont, drawBrush, point12);

            pictureBox3.Image = pictureBox2.Image;

            Image img = pictureBox3.Image;
            string bArr = imgToByteArray(img);


            


            baglanti.Open();
            if(textBox1.Text != " " && textBox2.Text != " " && textBox3.Text != " " && textBox4.Text != " ")
            {
                if(textBox1.Text.Length == 11 )
                {                
                
                try
                {
                        string sorgu = "INSERT INTO satistablosu(tc,ad,soyad,tel,eMail,adres,magazaAdi,urunKodu,urunAdi,urunFiyat,urunTarih,garantiSure,nakitKart,urunFoto,garantiFoto) values (@1,@2,@3,@4,@5,@6,@7,@8,@9,@10,@11,@12,@13,@14,@15)";
                        komut = new MySqlCommand(sorgu, baglanti);

                        komut.Parameters.AddWithValue("@1", textBox1.Text);
                        komut.Parameters.AddWithValue("@2", textBox2.Text);
                        komut.Parameters.AddWithValue("@3", textBox3.Text);
                        komut.Parameters.AddWithValue("@4", textBox4.Text);
                        komut.Parameters.AddWithValue("@5", textBox5.Text);
                        komut.Parameters.AddWithValue("@6", textBox6.Text);
                        komut.Parameters.AddWithValue("@7", textBox11.Text);
                        komut.Parameters.AddWithValue("@8", textBox7.Text);
                        komut.Parameters.AddWithValue("@9", textBox8.Text);
                        komut.Parameters.AddWithValue("@10", textBox9.Text);
                        komut.Parameters.AddWithValue("@11", dateTimePicker1.Text);
                        komut.Parameters.AddWithValue("@12", textBox10.Text);
                        komut.Parameters.AddWithValue("@13", comboBox1.SelectedItem);
                        komut.Parameters.AddWithValue("@14", geciciUrunFoto);
                        komut.Parameters.AddWithValue("@15", bArr);

                        komut.ExecuteNonQuery();
                        doldur();
                        MessageBox.Show("Satış başarı ile gerçekleştirildi.");
                    }
                catch (Exception ex)
                {

                    MessageBox.Show(ex.Message);
                }


                }
            }

            baglanti.Close();
        }

        public string imgToByteArray(Image img)
        {
            using (MemoryStream mStream = new MemoryStream())
            {
                img.Save(mStream, img.RawFormat);

                byte[] imageBytes = mStream.ToArray();

                // Convert byte[] to Base64 String
                base64String = Convert.ToBase64String(imageBytes);
               

            }

            return base64String;
        }

        void temizle()
        {
            textBox1.Text = "";
            textBox2.Text = "";
            textBox3.Text = "";
            textBox4.Text = "";
            textBox5.Text = "";
            textBox6.Text = "";
            textBox11.Text = "";
            textBox7.Text = "";
            textBox8.Text = "";
            textBox9.Text = "";
            textBox10.Text = "";
            comboBox1.Text = "";
            pictureBox1.Image = null;
           // pictureBox3.Image = null;
        }


        void doldur()
        {
          
            string sorgu = "select * from satistablosu";
            MySqlDataAdapter da = new MySqlDataAdapter(sorgu, baglanti);
            DataTable ds = new DataTable();
            da.Fill(ds);
            dataGridView1.DataSource = ds;
        }
        
        private void Form1_Load(object sender, EventArgs e)
        {
            doldur();

            dataGridView1.Columns[0].Visible = false;
            dataGridView1.Columns[14].Visible = false;
            dataGridView1.Columns[15].Visible = false;

           
        }
        static public string tc = "";
        static public string ad = "";
        static public string soyad = "";
        static public string tel = "";
        static public string email = "";
        static public string adres = "";
        static public string magaza = "";
        static public string urunKodu = "";
        static public string urunAdi = "";
        static public string fiyat = "";
        static public string tarih = "";
        static public string garantiSuresi = "";
        private void dataGridView1_Click(object sender, EventArgs e)
        {
          

            textBox1.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[1].Value.ToString();
            textBox2.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[2].Value.ToString();
            textBox3.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[3].Value.ToString();
            textBox4.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[4].Value.ToString();
            textBox5.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[5].Value.ToString();
            textBox6.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[6].Value.ToString();
            textBox11.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[7].Value.ToString();
            textBox7.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[8].Value.ToString();
            textBox8.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[9].Value.ToString();
            textBox9.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[10].Value.ToString();
            dateTimePicker1.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[11].Value.ToString();
            textBox10.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[12].Value.ToString();
            comboBox1.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[13].Value.ToString();

           


            tc = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[1].Value.ToString();
            ad = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[2].Value.ToString();
            soyad = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[3].Value.ToString();
            tel = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[4].Value.ToString();
            email = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[5].Value.ToString();
            adres = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[6].Value.ToString();
            magaza = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[7].Value.ToString();
            urunKodu = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[8].Value.ToString();
            urunAdi = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[9].Value.ToString();
            fiyat = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[10].Value.ToString() + "  TL";
            tarih = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[11].Value.ToString();
            garantiSuresi = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[12].Value.ToString(); 
        }

        private void button3_Click(object sender, EventArgs e)
        {
           

            baglanti.Open();


            try
            {
                DialogResult guncelle;
                guncelle = MessageBox.Show("Satış Bilgilerini Güncellemek İstediğinizden Eminmisiniz", "Uyarı ?", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (guncelle == DialogResult.Yes && dataGridView1.CurrentRow.Cells[0].Value.ToString().Trim() != "")
                {
                    string satirGuncelle = dataGridView1.CurrentRow.Cells[0].Value.ToString();

                    string sorgu = "Update satistablosu set tc=@1,ad=@2,soyad=@3,tel=@4,eMail=@5,adres=@6,magazaAdi=@7,urunKodu=@8,urunAdi=@9,urunFiyat=@10,urunTarih=@11,garantiSure=@12,nakitKart=@13 where satisID= " + satirGuncelle + " ";

                    komut = new MySqlCommand(sorgu, baglanti);
                    komut.Parameters.AddWithValue("@1", textBox1.Text);
                    komut.Parameters.AddWithValue("@2", textBox2.Text);
                    komut.Parameters.AddWithValue("@3", textBox3.Text);
                    komut.Parameters.AddWithValue("@4", textBox4.Text);
                    komut.Parameters.AddWithValue("@5", textBox5.Text);
                    komut.Parameters.AddWithValue("@6", textBox6.Text);
                    komut.Parameters.AddWithValue("@7", textBox11.Text);
                    komut.Parameters.AddWithValue("@8", textBox7.Text);
                    komut.Parameters.AddWithValue("@9", textBox8.Text);
                    komut.Parameters.AddWithValue("@10", textBox9.Text);
                    komut.Parameters.AddWithValue("@11", dateTimePicker1.Text);
                    komut.Parameters.AddWithValue("@12", textBox10.Text);
                    komut.Parameters.AddWithValue("@13", comboBox1.Text);
                    komut.ExecuteNonQuery();
                    doldur();
                    MessageBox.Show(" Satış Bilgileri Başarı İle Güncellendi.");


                }
                else
                {
                    MessageBox.Show("Güncelleme İşlemi İptal Edildi ");
                }
            }
            catch (Exception ex)
            {

                MessageBox.Show("Güncelleme Hatası" + ex.Message);
            }

            baglanti.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            baglanti.Open();

            DialogResult sil;
            sil = MessageBox.Show("Satış bilgileri Silinecektir Silmek istediğinizden Eminmisiniz ?", "Uyarı", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

            if (sil == DialogResult.Yes && dataGridView1.CurrentRow.Cells[0].Value.ToString().Trim() != "")
            {
                string satirSil = dataGridView1.CurrentRow.Cells[0].Value.ToString();
                string sorgu = "delete from satistablosu where satisID=" + satirSil + " ";
                komut = new MySqlCommand(sorgu, baglanti);
                komut.ExecuteNonQuery();
                doldur();         
                MessageBox.Show("Satış bilgileri Başarı İle Silindi.");
                temizle();

            }
            else
            {
                MessageBox.Show("Silme İşlemi İptal edildi.");
            }
            baglanti.Close();
        }

       
        
        private void button4_Click(object sender, EventArgs e)
        {
            frmPrint frm = new frmPrint();
            frm.Show();
            
        }

        private void button5_Click(object sender, EventArgs e)
        {
             temizle();
          
        }
        string geciciUrunFoto = "";
        private String base64String;

        private void textBox7_TextChanged(object sender, EventArgs e)
        {
            if(textBox7.Text != "")
            {
                string uzunluk = Convert.ToString(textBox7.Text);

                string sorgu = "SELECT urunKod,urunAd,urunFiyat,urunGarSur,urunFoto from urunler where urunKod =" + textBox7.Text + "";
                komut = new MySqlCommand(sorgu, baglanti);


                baglanti.Open();

                try
                {
                    if (uzunluk.Length == 10)
                    {
                        MySqlDataReader dr = komut.ExecuteReader();
                        while (dr.Read())
                        {
                            textBox8.Text = dr["urunAd"].ToString();
                            textBox9.Text = dr["urunFiyat"].ToString();
                            textBox10.Text = dr["urunGarSur"].ToString();
                            // pictureBox1.Image =Image.FromFile(Application.StartupPath+ @dr["urunFoto"].ToString());
                            pictureBox1.Load(dr["urunFoto"].ToString());
                            geciciUrunFoto = dr["urunFoto"].ToString();
                        }


                    }
                    else
                    {
                        textBox8.Text = "";
                        textBox9.Text = "";
                        textBox10.Text = "";
                        pictureBox1.Image = null;

                    }

                }
                catch (Exception ex)
                {

                    MessageBox.Show(ex.Message);
                }




                baglanti.Close();
            }
           
        }
       
        private void button6_Click(object sender, EventArgs e)
        {
            FrmMesajlar frmmesaj = new FrmMesajlar();
            frmmesaj.Show();

        }
    }
}
