using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows;
using System.Reflection;
using System.Linq;

namespace JDClientRicheCsharp.Models
{
    public class GemID : ObservableCollection<Dictionary<int, string>>
    {
        public enum GemShape {
        [Description("Camer")]
        Camer=1,
        [Description("Cushion")]
        Cushion=2,
        [Description("Fancy")]
        Fancy=3,
        [Description("Heart")]
        Heart=4,
        [Description("Marquise")]
        Marquise=5,
        [Description("Octagon")]
        Octagon=6,
        [Description("Oval")]
        Oval=7,
        [Description("Pear")]
        Pear=8,
        [Description("Rectangle")]
        Rectangle=9,
        [Description("Round")]
        Round=10,
        [Description("Ruff")]
        Ruff=11,
        [Description("Square")]
        Square=12,
        [Description("Trillion")]
        Trillion=13
        }
        public Dictionary<int, string> DictGemShape = Enum.GetValues(typeof(GemShape))
        .Cast<GemShape>()
        .ToDictionary(t => (int)t, t => t.GetDescription());

        public Dictionary<int, string> propDictGemShape
        {
            get
            {
                return DictGemShape;
            }
        }
//-----------------------------------------------------------------------------------------
    
        public enum GemOrigin
        {
            [Description("Unknown")]
            Unknown = 0,
            [Description("Afghanistan")]
            Afghanistan = 1,
            [Description("Australia")]
            Australia = 2,
            [Description("Birmania")]
            Birmania = 3,
            [Description("Brazil")]
            Brazil = 4,
            [Description("Colombia")]
            Colombia = 5,
            [Description("India")]
            India = 6,
            [Description("Kenya")]
            Kenya = 7,
            [Description("Madagascar")]
            Madagascar = 8,
            [Description("Mozambique")]
            Mozambique = 9,
            [Description("Namibia")]
            Namibia = 10,
            [Description("Nigeria")]
            Nigeria = 11,
            [Description("Pakistan")]
            Pakistan = 12,
            [Description("Russia")]
            Russia = 13,
            [Description("Sri Lanka")]
            Sri_lanka = 14,
            [Description("Tanzania")]
            Tanzania = 15,
            [Description("Thailand")]
            Thailand = 16,
            [Description("USA")]
            USA = 17,
            [Description("Zambia")]
            Zambia = 18
        }
        public Dictionary<int, string> DictGemOrigin = Enum.GetValues(typeof(GemOrigin))
        .Cast<GemOrigin>()
        .ToDictionary(t => (int)t, t => t.GetDescription());

        public Dictionary<int, string> propDictGemOrigin
        {
            get
            {
                return DictGemOrigin;
            }
        }
//-----------------------------------------------------------------------------
    public enum GemLight {
    [Description("Daylight")]
	Daylight=1,
    [Description("Fluorescent")]
	Fluorescent_light=2,
    [Description("Incandescent")]
	Incandescent_light = 3
    }
    
    public Dictionary<int, string> DictGemLight = Enum.GetValues(typeof(GemLight))
    .Cast<GemLight>()
    .ToDictionary(t => (int)t, t => t.GetDescription());

    public Dictionary<int, string> propDictGemLight
        {
            get
            {
                return DictGemLight;
            }
        }
//-----------------------------------------------------------------------------------------------

    public enum GemSpecies {
        [Description("Agate")]
        AGATE = 1,
        [Description("Alexandrite")]
        ALEXANDRITE = 2,
        [Description("Almandine")]
        ALMANDINE = 3,
        [Description("Amazonite")]
        AMAZONITE = 4,
        [Description("Amber")]
        AMBER = 5,
        [Description("Amethyste")]
	    AMETHYST=6, 
        [Description("Ametrine")]
	    AMETRINE=7,  
        [Description("Andalusite")]
	    ANDALUSITE=8, 
        [Description("Apatite")]
	    APATITE=9,  
        [Description("Aquamarine")]
	    AQUAMARINE=10, 
        [Description("Beryl")]
	    BERYL=11, 
        [Description("Bixbite")]
	    BIXBITE=12, 
        [Description("Chalcedony")]
	    CHALCEDONY=13, 
        [Description("Chrome tourmaline")]
	    CHROME_TOURMALINE=14, 
        [Description("Chrysoberyl")]
	    CHRYSOBERYL=15, 
        [Description("Citrine")]
	    CITRINE=16,  
        [Description("Color change garnet")]
	    COLOR_CHANGE_GARNET=17, 
        [Description("Color change sapphire")]
	    COLOR_CHANGE_SAPPHIRE=18, 
        [Description("Cubic zirconia")]
	    CUBIC_ZIRCONIA=19, 
        [Description("Demantoid")]
	    DEMANTOID=20, 
        [Description("Diamond")]
	    DIAMOND=21, 
        [Description("Diopside")]
	    DIOPSIDE=22, 
        [Description("Emerald")]
	    EMERALD=23, 
        [Description("Fluorite")]
	    FLUORITE=24, 
        [Description("Goshenite")]
	    GOSHENITE=25, 
        [Description("Hessonite")]
	    HESSONITE=26,  
        [Description("Iolite")]
	    IOLITE=27, 
        [Description("Kunzite")]
	    KUNZITE=28, 
        [Description("Kyanite")]
	    KYANITE=29, 
        [Description("Lapis lazuli")]
	    LAPIS_LAZULI=30, 
        [Description("Malaisa garnet")]
	    MALAISA_GARNET=31, 
        [Description("Moonstone")]
	    MOONSTONE=32, 
        [Description("Morganite")]
	    MORGANITE=33, 
        [Description("Opal")]
	    OPAL=34, 
        [Description("Peridot")]
	    PERIDOT=35, 
        [Description("Pezzottatite")]
	    PEZZOTTAITE=36,  
        [Description("Prhenite")]
	    PREHNITE=37, 
        [Description("Pyrope")]
	    PYROPE=38, 
        [Description("Rhodochrosite")]
	    RHODOCHROSITE=39, 
        [Description("Rhodolite")]
	    RHODOLITE=40, 
        [Description("Rhodonite")]
	    RHODONITE=41, 
        [Description("Quartz Rose")]
	    ROSE_QUARTZ=42, 
        [Description("Rubelite")]
	    RUBELITE=43, 
        [Description("Ruby")]
	    RUBY=44, 
        [Description("Sapphire")]
	    SAPPHIRE=45, 
        [Description("Scapolite")]
	    SCAPOLITE=46, 
        [Description("Smoky Quartz")]
	    SMOKY_QUARTZ=47, 
        [Description("Spessartite")]
	    SPESSARTITE=48, 
        [Description("Sphene")]
	    SPHENE=49, 
        [Description("Spinel")]
	    SPINEL=50,
        [Description("Star Ruby")]
	    STAR_RUBY=51,
        [Description("Star Sapphire")]
	    STAR_SAPPHIRE=52, 
        [Description("Tanzanite")]
	    TANZANITE=53,  
        [Description("Topaz")]
	    TOPAZ=54,  
        [Description("Tourmaline")]
	    TOURMALINE=55,  
        [Description("Tsavorite")]
	    TSAVORITE=56, 
        [Description("Turquoise")]
	    TURQUOISE=57, 
        [Description("Zircon")]
	    ZIRCON=58
        }
    public Dictionary<int, string> DictGemSpecies = Enum.GetValues(typeof(GemSpecies))
        .Cast<GemSpecies>()
        .ToDictionary(t => (int)t, t => t.GetDescription());
    public Dictionary<int, string> propDictGemSpecies
        {
            get
            {
                return DictGemSpecies;
            }
        }
//-------------------------------------------------------------------------------------------------
        public enum GemCertificate {
        [Description("GIA")]
	    GIA =1,
        [Description("Gublin")]
	    Gublin =2,
        [Description("AIGS")]
	    AIGS =3,
        [Description("HRD")]
	    HRD = 4
        }
        public Dictionary<int, string> DictGemCertificate = Enum.GetValues(typeof(GemCertificate))
        .Cast<GemCertificate>()
        .ToDictionary(t => (int)t, t => t.GetDescription());
        public Dictionary<int, string> propDictGemCertificate
        {
            get
            {
                return DictGemCertificate;
            }
        }
//------------------------------------------------------------------------------------------------
        public enum GemClarity {
        [Description("Loupe clean")]
	    Loupe_Clean = 1,
        [Description("Eye clean")]
	    Eye_Clean =2,
        [Description("Eye Clean to Slightly included")]
	    Eyes_Clean_to_Slightly_Included =3,
        [Description("Slightly included")]
	    Slightly_Included =4,
        [Description("Moderately included")]
	    Moderately_Included = 5,
        [Description("Heavily included")]
	    Heavily_Included =6,
        [Description("Translucent")]
	    Translucent =7,
        [Description("Opaque")]
	    Opaque =8
        }
        public Dictionary<int, string> DictGemClarity = Enum.GetValues(typeof(GemClarity))
        .Cast<GemClarity>()
        .ToDictionary(t => (int)t, t => t.GetDescription());
        public Dictionary<int, string> propDictGemClarity
        {
            get
            {
                return DictGemClarity;
            }
        }
//----------------------------------------------------------------------------------------
        public enum GemCut
        {
            [Description("Asscher")]
            Asscher = 1,
            [Description("Baguette")]
            Baguette = 2,
            [Description("Briolette")]
            Briolette = 3,
            [Description("Cabashian")]
            Cabashian = 4,
            [Description("Checker Board")]
            Checker_board = 5,
            [Description("Concave")]
            Concave = 6,
            [Description("Diamond")]
            Diamond = 7,
            [Description("Fancy")]
            Fancy = 8,
            [Description("Millenium")]
            Millenium = 9,
            [Description("Mixte")]
            Mixte = 10,
            [Description("Portugeese")]
            Portugueese = 11,
            [Description("Princess")]
            Princess = 12,
            [Description("Radiant")]
            Radiant = 13,
            [Description("Ruff")]
            Ruff = 14,
            [Description("Step cut")]
            Step_cut = 15
        }
        public Dictionary<int, string> DictGemCut = Enum.GetValues(typeof(GemCut))
        .Cast<GemCut>()
        .ToDictionary(t => (int)t, t => t.GetDescription());
        public Dictionary<int, string> propDictGemCut
        {
            get
            {
                return DictGemCut;
            }
        }
//-------------------------------------------------------------
        public enum GemEnhancements
        {

            [Description("High Pressure")]
            High_Pressure = 1,
            [Description("High Temperature")]
            High_Temperature = 2,
            [Description("Unkown")]
            Unknown = 3
        }
        public Dictionary<int, string> DictGemEnhancement = Enum.GetValues(typeof(GemEnhancements))
       .Cast<GemEnhancements>()
       .ToDictionary(t => (int)t, t => t.GetDescription());
        public Dictionary<int, string> propDictGemEnhancement
        {
            get
            {
                return DictGemEnhancement;
            }
        }
        //.ToDictionary(t => (int)t, t => t.ToString());
    }

        public static class EnumExtensions
	    {
        public static string GetDescription(this Enum value)
	        {
	            FieldInfo fieldInfo = value.GetType().GetField(value.ToString());
	            DescriptionAttribute attribute = Attribute.GetCustomAttribute(fieldInfo, typeof(DescriptionAttribute)) as DescriptionAttribute;
	            return attribute == null ? value.ToString() : attribute.Description;
	        }
	    }

}