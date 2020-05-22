package com.antrobot.livingTools.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class VectorConvert {
    public static Vec3i convertI(Vec3d v){
        return new Vec3i(v.x,v.y,v.z);
    }
    public static BlockPos convertPosD(Vec3d v){
        return new BlockPos(v.x,v.y,v.z);
    }
}
