using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace eGarantiBelgesiSunucu
{
    public partial class FrmMesajlar : Form
    {
        public FrmMesajlar()
        {
            InitializeComponent();
        }
        MySqlConnection baglanti = new MySqlConnection("SERVER= localhost;DATABASE=sunucuveritabani;SslMode=none;UID=root;PASSWORD=;Convert Zero Datetime=True");
        MySqlCommand komut = new MySqlCommand();

        private void FrmMesajlar_Load(object sender, EventArgs e)
        {
            doldur();
            dataGridView1.Columns[0].Visible = false;


            for (int i = 0; i < dataGridView1.Rows.Count - 1; i++)
            {
                Application.DoEvents();
                DataGridViewCellStyle renk = new DataGridViewCellStyle();      
                if (dataGridView1.Rows[i].Cells["durum"].Value.ToString() == "okunmadi")
                {
                    renk.BackColor = Color.Red;
                    renk.ForeColor = Color.White;
                }
                else 
                {
                    renk.BackColor = Color.Green;
                }
               
                dataGridView1.Rows[i].DefaultCellStyle = renk;
            }
        }

        void doldur()
        {

            string sorgu = "select DISTINCT  iletiID,tc,ad,soyad,iletiEmail,tel,iletiUrunKod,iletiUrunAd,iletiMesaj,durum from iletiler inner join satistablosu on iletiler.iletiEmail = satistablosu.eMail order by urunTarih desc";
            MySqlDataAdapter da = new MySqlDataAdapter(sorgu, baglanti);
            DataTable ds = new DataTable();
            da.Fill(ds);
            dataGridView1.DataSource = ds;
        }

        private void dataGridView1_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {


            label1.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[1].Value.ToString();
            label2.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[2].Value.ToString();       
            label3.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[3].Value.ToString();
            label4.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[4].Value.ToString();
            label5.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[5].Value.ToString();
            label6.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[6].Value.ToString();
            label15.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[7].Value.ToString();
            textBox1.Text = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[8].Value.ToString();



            string okuMesaj = dataGridView1.Rows[dataGridView1.CurrentRow.Index].Cells[9].Value.ToString();

           if(okuMesaj == "okunmadi")
            {
                try
                {
                    baglanti.Open();
                    string satirGuncelle = dataGridView1.CurrentRow.Cells[0].Value.ToString();

                    string sorgu = "Update iletiler set durum=@1 where iletiID= " + satirGuncelle + " ";
                    komut = new MySqlCommand(sorgu, baglanti);
                    komut.Parameters.AddWithValue("@1", "okundu");
                    komut.ExecuteNonQuery();
                    doldur();

                    baglanti.Close();

                }
                catch (Exception ex)
                {

                    MessageBox.Show("Güncelleme Hatası" + ex.Message);
                }

                for (int i = 0; i < dataGridView1.Rows.Count - 1; i++)
                {
                    Application.DoEvents();
                    DataGridViewCellStyle renk = new DataGridViewCellStyle();
                    if (dataGridView1.Rows[i].Cells["durum"].Value.ToString() == "okunmadi")
                    {
                        renk.BackColor = Color.Red;
                    }
                    else
                    {
                        renk.BackColor = Color.Green;
                    }

                    dataGridView1.Rows[i].DefaultCellStyle = renk;
                }
            }
           
            

        }
    }
}
