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
using System.Windows.Navigation;
using System.Windows.Shapes;
using JDClientRicheCsharp.Models;
using Microsoft.Samples.CustomControls;

namespace JDClientRicheCsharp
{
    /// <summary>
    /// Logique d'interaction pour MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void New_GemID_Click(object sender, RoutedEventArgs e)
        {
            Window fenetre = new NewGemID();
            fenetre.Show();
            
        }

        private void Synchronize_Click(object sender, RoutedEventArgs e)
        {

        }

        private void ColorFilter_Click(object sender, RoutedEventArgs e)
        {
            ColorPickerDialog cpicker = new ColorPickerDialog();
            cpicker.ShowDialog();
            sample_color_filter.Fill = new SolidColorBrush(cpicker.SelectedColor);
        }

    }
}
