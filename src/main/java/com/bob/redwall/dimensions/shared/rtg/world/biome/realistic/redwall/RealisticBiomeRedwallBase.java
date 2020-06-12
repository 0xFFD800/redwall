package com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.redwall;

import com.bob.redwall.dimensions.shared.rtg.world.biome.realistic.RealisticBiomeBase;

import net.minecraft.world.biome.Biome;

public abstract class RealisticBiomeRedwallBase extends RealisticBiomeBase {
    public static RealisticBiomeBase valourArctic;
    public static RealisticBiomeBase valourArcticHills;
    public static RealisticBiomeBase valourBareDesert;
    public static RealisticBiomeBase valourBareRedDesert;
    public static RealisticBiomeBase valourBeach;
    public static RealisticBiomeBase valourDeepOcean;
    public static RealisticBiomeBase valourDesert;
    public static RealisticBiomeBase valourFlowerForest;
    public static RealisticBiomeBase valourForest;
    public static RealisticBiomeBase valourForestAsh;
    public static RealisticBiomeBase valourForestBirch;
    public static RealisticBiomeBase valourForestConiferous;
    public static RealisticBiomeBase valourForestDeciduous;
    public static RealisticBiomeBase valourForestElm;
    public static RealisticBiomeBase valourForestFir;
    public static RealisticBiomeBase valourForestLarch;
    public static RealisticBiomeBase valourForestMaple;
    public static RealisticBiomeBase valourForestOak;
    public static RealisticBiomeBase valourForestPine;
    public static RealisticBiomeBase valourForestSpruce;
    public static RealisticBiomeBase valourGrassland;
    public static RealisticBiomeBase valourHills;
    public static RealisticBiomeBase valourMarsh;
    public static RealisticBiomeBase valourMesa;
    public static RealisticBiomeBase valourMountains;
    public static RealisticBiomeBase valourOcean;
    public static RealisticBiomeBase valourPlains;
    public static RealisticBiomeBase valourRedDesert;
    public static RealisticBiomeBase valourRiver;
    public static RealisticBiomeBase valourGravelBeach;
    public static RealisticBiomeBase valourRockyBeach;
    public static RealisticBiomeBase valourSunflowerPlains;
    public static RealisticBiomeBase valourScrubland;
    public static RealisticBiomeBase valourHeathland;
    public static RealisticBiomeBase valourScrublandPlateau;
    public static RealisticBiomeBase valourDesertPlateau;
    public static RealisticBiomeBase valourPineForestPlateau;
    public static RealisticBiomeBase valourRiverPlateau;
    public static RealisticBiomeBase valourAbyss;
    public static RealisticBiomeBase valourQuarry;
    public static RealisticBiomeBase valourSouthswardForest;
    public static RealisticBiomeBase valourSouthswardHills;
    public static RealisticBiomeBase valourDeeplough;
    public static RealisticBiomeBase valourNorthlands;
    public static RealisticBiomeBase valourNoonvale;
    public static RealisticBiomeBase valourNoonvaleHills;
    public static RealisticBiomeBase valourNoonvaleForest;

    public RealisticBiomeRedwallBase(Biome b, Biome riverbiome) {

        super(b, riverbiome);
    }

    @Override
    public Biome baseBiome() {
        return this.baseBiome;
    }

    @Override
    public Biome riverBiome() {
        return this.riverBiome;
    }

    @Override
    public String modSlug() {
        return "valour";
    }

    @Override
    public int lavaSurfaceLakeChance() {
        return 0;
    }

    public static void addBiomes() {
    	valourArctic = new RealisticBiomeRedwallArctic();
    	valourArcticHills = new RealisticBiomeRedwallArcticHills();
    	valourBareDesert = new RealisticBiomeValourBareDesert();
    	valourBareRedDesert = new RealisticBiomeValourBareRedDesert();
    	valourBeach = new RealisticBiomeValourBeach();
    	valourDeepOcean = new RealisticBiomeRedwallDeepOcean();
    	valourDesert = new RealisticBiomeValourDesert();
    	valourFlowerForest = new RealisticBiomeRedwallFlowerForest();
    	valourForest = new RealisticBiomeValourForest();
    	valourForestAsh = new RealisticBiomeValourForestAsh();
    	valourForestBirch = new RealisticBiomeValourForestBirch();
    	valourForestConiferous = new RealisticBiomeValourForestConiferous();
    	valourForestDeciduous = new RealisticBiomeValourForestDeciduous();
    	valourForestElm = new RealisticBiomeValourForestElm();
    	valourForestFir = new RealisticBiomeValourForestFir();
    	valourForestLarch = new RealisticBiomeValourForestLarch();
    	valourForestMaple = new RealisticBiomeValourForestMaple();
    	valourForestOak = new RealisticBiomeValourForestOak();
    	valourForestPine = new RealisticBiomeValourForestPine();
    	valourForestSpruce = new RealisticBiomeValourForestSpruce();
    	valourGrassland = new RealisticBiomeValourGrassland();
    	valourHills = new RealisticBiomeValourHills();
    	valourMarsh = new RealisticBiomeValourRedwall();
    	valourMesa = new RealisticBiomeValourMesa();
    	valourMountains = new RealisticBiomeValourMountains();
    	valourOcean = new RealisticBiomeRedwallOcean();
    	valourPlains = new RealisticBiomeValourPlains();
    	valourRedDesert = new RealisticBiomeValourRedDesert();
    	valourRiver = new RealisticBiomeRedwallRiver();
    	valourGravelBeach = new RealisticBiomeRedwallGravelBeach();
    	valourRockyBeach = new RealisticBiomeRedwallRockyBeach();
    	valourSunflowerPlains = new RealisticBiomeValourSunflowerPlains();
    	valourScrubland = new RealisticBiomeValourScrubland();
    	valourHeathland = new RealisticBiomeRedwallHeathland();
    	valourScrublandPlateau = new RealisticBiomeRedwallScrublandPlateau();
    	valourDesertPlateau = new RealisticBiomeRedwallDesertPlateau();
    	valourPineForestPlateau = new RealisticBiomeRedwallPineForestPlateau();
    	valourRiverPlateau = new RealisticBiomeRedwallRiverPlateau();
    	valourAbyss = new RealisticBiomeRedwallAbyss();
    	valourQuarry = new RealisticBiomeRedwallQuarry();
    	valourSouthswardForest = new RealisticBiomeRedwallSouthswardForest();
    	valourSouthswardHills = new RealisticBiomeRedwallSouthswardHills();
    	valourDeeplough = new RealisticBiomeValourDeeplough();
    	valourNorthlands = new RealisticBiomeRedwallNorthlands();
    	valourNoonvale = new RealisticBiomeRedwallNoonvale();
    	valourNoonvaleHills = new RealisticBiomeRedwallNoonvaleHills();
    	valourNoonvaleForest = new RealisticBiomeRedwallNoonvaleForest();
    }
}
