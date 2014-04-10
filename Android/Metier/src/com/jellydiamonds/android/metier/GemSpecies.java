package com.jellydiamonds.android.metier;

public enum GemSpecies {
	
	EMPTY(0),
	AGATE(1),
	ALEXANDRITE(2),
	ALMANDINE(3),
	AMAZONITE(4),
	AMBER(5),
	AMETHYST(6),
	AMETRINE(7),
	ANDALUSITE(8),
	APATITE(9),
	AQUAMARINE(10),
	BERYL(11),
	BIXBITE(12),
	CHALCEDONY(13),
	CHROME_TOURMALINE(14),
	CHRYSOBERYL(15),
	CITRINE(16),
	COLOR_CHANGE_GARNET(17),
	COLOR_CHANGE_SAPPHIRE(18),
	CUBIC_ZIRCONIA(19),
	DEMANTOID(20),
	DIAMOND(21),
	DIOPSIDE(22),
	EMERALD(23),
	FLUORITE(24),
	GOSHENITE(25),
	HESSONITE(26),
	IOLITE(27),
	KUNZITE(28),
	KYANITE(29),
	LAPIS_LAZULI(30),
	MALAISA_GARNET(31),
	MOONSTONE(32),
	MORGANITE(33),
	OPAL(34),
	PERIDOT(35),
	PEZZOTTAITE(36),
	PREHNITE(37),
	PYROPE(38),
	RHODOCHROSITE(39),
	RHODOLITE(40),
	RHODONITE(41),
	ROSE_QUARTZ(42),
	RUBELITE(43),
	RUBY(44),
	SAPPHIRE(45),
	SCAPOLITE(46),
	SMOKY_QUARTZ(47),
	SPESSARTITE(48),
	SPHENE(49),
	SPINEL(50),
	STAR_RUBY(51),
	STAR_SAPPHIRE(52),
	TANZANITE(53),
	TOPAZ(54),
	TOURMALINE(55),
	TSAVORITE(56),
	TURQUOISE(57),
	ZIRCON(58);

	
	protected int specie;
	
	GemSpecies( int specie )
	{
		this.specie = specie;
	}
	
	public Integer getValue()
	{
		return this.specie;
	}
	
	/*
	(1,'Agate'),
	(2,'Alexandrite'),
	(3,'Almandine'),
	(4,'Amazonite'),
	(5,'Amber'),
	(6,'Amethyst'),
	(7,'Ametrine'),
	(8,'Andalusite'),
	(9,'Apatite'),
	(10,'Aquamarine'),
	(11,'Beryl'),
	(12,'Bixbite'),
	(13,'Chalcedony'),
	(14,'Chrome tourmaline'),
	(15,'Chrysoberyl'),
	(16,'Citrine'),
	(17,'Color change garnet'),
	(18,'Color change sapphire'),
	(19,'Cubic zirconia'),
	(20,'Demantoid'),
	(21,'Diamond'),
	(22,'Diopside'),
	(23,'Emerald'),
	(24,'Fluorite'),
	(25,'Goshenite'),
	(26,'Hessonite'),
	(27,'Iolite'),
	(28,'Kunzite'),
	(29,'Kyanite'),
	(30,'Lapis lazuli'),
	(31,'Malaia garnet'),
	(32,'Moonstone'),
	(33,'Morganite'),
	(34,'Opal'),
	(35,'Peridot'),
	(36,'Pezzottaite'),
	(37,'Prehnite'),
	(38,'Pyrope'),
	(39,'Rhodochrosite'),
	(40,'Rhodolite'),
	(41,'Rhodonite'),
	(42,'Rose quartz'),
	(43,'Rubelite'),
	(44,'Ruby'),
	(45,'Sapphire'),
	(46,'Scapolite'),
	(47,'Smoky quartz'),
	(48,'Spessartite'),
	(49,'Sphene'), 
	(50,'Spinel'),
	(51,'Star ruby'),
	(52,'Star sapphire'),
	(53,'Tanzanite'),
	(54,'Topaz'),
	(55,'Tourmaline'),
	(56,'Tsavorite'),
	(57,'Turquoise'),
	(58,'Zircon');*/
}
