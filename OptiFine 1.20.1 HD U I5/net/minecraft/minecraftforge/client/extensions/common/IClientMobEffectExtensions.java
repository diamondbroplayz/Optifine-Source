// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.client.extensions.common;

public interface IClientMobEffectExtensions
{
    public static final IClientMobEffectExtensions DUMMY = new IClientMobEffectExtensions$1();
    
    default IClientMobEffectExtensions of(final bfa mobeffectinstance) {
        return IClientMobEffectExtensions.DUMMY;
    }
    
    default IClientMobEffectExtensions of(final bey effect) {
        return IClientMobEffectExtensions.DUMMY;
    }
    
    default boolean isVisibleInInventory(final bfa instance) {
        return true;
    }
    
    default boolean isVisibleInGui(final bfa instance) {
        return true;
    }
    
    default boolean renderInventoryIcon(final bfa instance, final ewg<?> screen, final eox guiGraphics, final int x, final int y, final int blitOffset) {
        return false;
    }
    
    default boolean renderInventoryText(final bfa instance, final ewg<?> screen, final eox guiGraphics, final int x, final int y, final int blitOffset) {
        return false;
    }
    
    default boolean renderGuiIcon(final bfa instance, final eow gui, final eox guiGraphics, final int x, final int y, final float z, final float alpha) {
        return false;
    }
}
