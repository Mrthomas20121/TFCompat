OTHER_METAl_TYPE = {
    'chromium':True,
	'kanthal':False,
	'solder':False,
	'aluminium_steel':True,
	'weak_aluminium_steel':False,
    'bismuth_steel':True,
	'weak_bismuth_steel':False,
    'damascus_steel':True,
	'weak_damascus_steel':False,
    'stainless_steel':False,
	'weak_stainless_steel':False,
	'rose_alloy':False,
	'ferrochrome':False,
	'cadmium':False,
    'nichrome':True,
    'alnico':False,
    'vanadium':False,
    'rhodium':False,
    'palladium':False,
    'antimony': False,
    'constantan': False,
    'electrum': False,
    'red_alloy': False,
    'mithril': True,
    'nickel_silver': True,
    'invar': True,
    'aluminium': True,
    'aluminium_brass': False,
    'ardite': False,
    'cobalt': True,
    'manyullyn': True,
    'osmium': True,
    'titanium': True,
    'tungsten': True,
    'tungsten_steel': True,
    'boron': True,
    'thorium': False,
    'manganese': False,
    'magnesium': False,
    'lithium': False,
    'zirconium': False,
    'zircaloy': True,
    'beryllium': False,
    'beryllium_copper': True,
    'hsla_steel': False,
    'ferroboron': False,
    'tough': False,
    'magnesium_diboride': False,
    'uranium': False,
    'soulforged_steel':False,
	'signalum':True,
	'lumium':True,
	'enderium':True,
	'refined_obsidian':False,
	'refined_glowstone':False,
	'thaumium':True,
	'void_metal':False,
    'bismuth': False,
    'bismuth_bronze': True,
    'black_bronze': True,
    'brass': False,
    'bronze': True,
    'copper': True,
    'gold': False,
    'lead': False,
    'nickel': False,
    'rose_gold': False,
    'silver': False,
    'tin': False,
    'zinc': False,
    'sterling_silver': False,
    'wrought_iron': True,
    'pig_iron': False,
    'steel': True,
    'platinum': False,
    'black_steel': True,
    'blue_steel': True,
    'red_steel': True
}

def Upper(s) :
    splitString = s.replace('_', ' ').title()
    return splitString

for metal, tool_metal in OTHER_METAl_TYPE.items() :
    #print('item.firmalife.%s_mallet.name=%s Mallet' % (metal, Upper(metal)))
    #print('item.firmalife.%s_mallet_head.name=%s Mallet Head' % (metal, Upper(metal)))
    print("item.ironbackpacks.backpack.tfcompat.%s.name=%s Backpack" % (metal, Upper(metal)))