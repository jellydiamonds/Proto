using Microsoft.Samples.CustomControls;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace JellyDiamondsCRicheCS
{
    /// <summary>
    /// Logique d'interaction pour NewGemID.xaml
    /// </summary>
    public partial class NewGemID : Window
    {
        public NewGemID()
        {
            InitializeComponent();
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            //System.Windows.Forms.ColorDialog clrdial = new System.Windows.Forms.ColorDialog();
            //WPFColorPickerLib.ColorDialog clrdial2 = new WPFColorPickerLib.ColorDialog();
            //clrdial.AnyColor = true;
            //clrdial.ShowDialog();
            //clrdial2.Show();
            // Doesn't work :(   ColorPicker clrpick = new ColorPicker();
            ColorPicker cpicker = new ColorPicker();
            Display_main_color.Fill =  new SolidColorBrush(cpicker.SelectedColor);

        }

        private void Button_Click_3(object sender, RoutedEventArgs e)
        {
            ColorPicker cpicker = new ColorPicker();
            Display_2nd_color_1.Fill = new SolidColorBrush(cpicker.SelectedColor);
        }

        private void Button_Click_4(object sender, RoutedEventArgs e)
        {
            ColorPicker cpicker = new ColorPicker();
            Display_2nd_color_2.Fill = new SolidColorBrush(cpicker.SelectedColor);
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            Microsoft.Win32.OpenFileDialog openFileDialog = new Microsoft.Win32.OpenFileDialog();
            System.IO.Stream myStream = null;
            openFileDialog.InitialDirectory = "c:\\";
            openFileDialog.Filter = "txt files (*.txt)|*.txt|All files (*.*)|*.*";
            openFileDialog.FilterIndex = 2;
            openFileDialog.RestoreDirectory = true;


            if (openFileDialog.ShowDialog() == true)
            {
                try
                {
                    //you can do something like this     openFileDialog1.FileName to get the file's fullpathname 

                    if ((myStream = openFileDialog.OpenFile()) != null)
                    {
                        String chemin = openFileDialog.FileName;
                        BitmapImage bm = new BitmapImage(new Uri(chemin, UriKind.RelativeOrAbsolute));
                        imagepierre.Source = bm;


                        using (myStream)
                        {


                            // Insert code to read the stream here.
                        }
                    }
                }

                catch (Exception ex)
                {
                    Xceed.Wpf.Toolkit.MessageBox.Show("Error: Could not read file from disk. Original error: " + ex.Message);
                }
            }
        }

        private void TextBox_TextChanged_1(object sender, TextChangedEventArgs e)
        {

        }
    }
}
