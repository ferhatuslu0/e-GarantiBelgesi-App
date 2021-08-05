using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Microsoft.Reporting.WinForms;
using System.IO;

namespace eGarantiBelgesiSunucu
{
    public partial class frmPrint : Form
    {
        public frmPrint()
        {
            InitializeComponent();
        }

        private void frmPrint_Load(object sender, EventArgs e)
        {
            
            ReportParameterCollection reportParameters = new ReportParameterCollection();
            
            reportParameters.Add(new ReportParameter("ReportParameter1", Form1.tc));
            reportParameters.Add(new ReportParameter("ReportParameter2", Form1.ad));
            reportParameters.Add(new ReportParameter("ReportParameter3", Form1.soyad));
            reportParameters.Add(new ReportParameter("ReportParameter4", Form1.tel));
            reportParameters.Add(new ReportParameter("ReportParameter5", Form1.email));
            reportParameters.Add(new ReportParameter("ReportParameter6", Form1.adres));
            reportParameters.Add(new ReportParameter("ReportParameter7", Form1.magaza));
            reportParameters.Add(new ReportParameter("ReportParameter8", Form1.urunKodu));
            reportParameters.Add(new ReportParameter("ReportParameter9", Form1.urunAdi));
            reportParameters.Add(new ReportParameter("ReportParameter10", Form1.fiyat));
            reportParameters.Add(new ReportParameter("ReportParameter11", Form1.tarih));
            reportParameters.Add(new ReportParameter("ReportParameter12", Form1.garantiSuresi)); 
            this.reportViewer1.LocalReport.SetParameters(reportParameters);
            this.reportViewer1.RefreshReport(); 

          
        }

        private void reportViewer1_Load(object sender, EventArgs e)
        {
            
        }
    }
}
