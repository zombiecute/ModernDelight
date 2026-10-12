package com.zombie_cute.mc.bakingdelight.util.enums;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum BacteriaType {
    LACTOBACILLUS("lactobacillus", 35, 15,5000,20000, 7,0), // 乳酸菌
    ACETOBACTER("acetobacter", 32, 10, 3000,100000,13, 1), // 醋酸菌
    YEAST("yeast", 28, 12, 2000,30000,7, 2), // 酵母菌
    MUCOR("mucor", 25, 5, 4000,10000,4, 3), // 毛霉
    ESCHERICHIA("escherichia", 37, 3, 8000,10000,4, 4); // 大肠杆菌
    final String name;
    final int optimalTemp;
    final int tolerance;
    final int maxLight;
    final int rawID;
    final int optimalMinCFU;
    final int optimalMaxCFU;
    BacteriaType(String name, int optimalTemp, int tolerance,int optimalMinCFU,int optimalMaxCFU, int maxLight, int rawID) {
        this.name = name;
        this.optimalTemp = optimalTemp;
        this.tolerance = tolerance;
        this.maxLight = maxLight;
        this.rawID = rawID;
        this.optimalMinCFU = optimalMinCFU;
        this.optimalMaxCFU = optimalMaxCFU;
    }
    public String getTranslationKey() {
        return ModernDelightMain.MOD_ID + ".bacteria." + this.name;
    }
    public static boolean checkLight(World world, BlockPos pos, BacteriaType type){
        int luminance = world.getLightLevel(pos);
        return luminance <= type.getMaxLight();
    }
    public static BacteriaType getBacteriaFromName(String name){
        for(BacteriaType b : BacteriaType.values()){
            if(b.name.equals(name)) return b;
        }
        return null;
    }
    public static BacteriaType getBacteriaFromRawID(int rawID){
        for(BacteriaType b : BacteriaType.values()){
            if(b.rawID == rawID) return b;
        }
        return null;
    }
    public boolean isInBestCFU(int cfu){
        return cfu >= optimalMinCFU && cfu <= optimalMaxCFU;
    }
    public String getName() {
        return name;
    }

    public int getOptimalTemp() {
        return optimalTemp;
    }

    public int getTolerance() {
        return tolerance;
    }

    public int getMaxLight() {
        return maxLight;
    }

    public int getRawID() {
        return rawID;
    }

}
