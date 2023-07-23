// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.event.entity.living;

public class LivingChangeTargetEvent extends LivingEvent
{
    public bfz getNewTarget() {
        return null;
    }
    
    public enum LivingTargetType implements ILivingTargetType
    {
        public static final LivingTargetType MOB_TARGET;
        public static final LivingTargetType BEHAVIOR_TARGET;
        
        public static LivingTargetType valueOf(final String name) {
            return Enum.valueOf(LivingTargetType.class, name);
        }
        
        static {
            LivingTargetType.MOB_TARGET = new LivingTargetType("MOB_TARGET", 0);
            LivingTargetType.BEHAVIOR_TARGET = new LivingTargetType("BEHAVIOR_TARGET", 1);
            LivingTargetType.$VALUES = $values();
        }
    }
}
