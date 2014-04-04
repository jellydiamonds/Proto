using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Windows;

namespace JellyDiamondsCRicheC.Models
{
    public class GemID : ObservableCollection<string>
    {


        public List<string> Shapes = new List<string>()
        {
            "Camer","Cushion","Fancy","Heart","Marquise","Octagon",
           "Oval","Pear","Rectangle","Round","Ruff","Square","Trillion","Other"
        };
        public List<string> Listshapes { get { return Shapes; } }

        List<string> Cutting = new List<string>()
        {
         "Asscher","Baguette","Briolette","Cabashian"," Checker-board","Concave","Diamond",
        "Fancy","Millenium","Mixte","Portugueese","Princess","Radiant","Ruff","Step-cut","Other"
        };
        public List<string> ListCutting { get { return Cutting; } }

        List<string> Certificate = new List<string>() 
     { 
      "AIGS", //(Asian Institute Of Gemological Sciences)
      "GIA", //Gemmology institute
       "Güblin", //(Swiss)
      "HRD", //(Anverse)
      "Other"
     };
        public List<string> ListCertificate { get { return Certificate; } }

        List<string> Light = new List<string>()
       {
           "Daylight",
            "Fluorescent",
           "Incandescent"
       };
        public List<string> ListLight { get { return Light;} }
    }
}
