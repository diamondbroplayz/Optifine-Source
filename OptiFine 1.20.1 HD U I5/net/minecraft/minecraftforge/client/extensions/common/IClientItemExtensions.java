// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.client.extensions.common;

public interface IClientItemExtensions
{
    public static final IClientItemExtensions DUMMY = new IClientItemExtensions$1();
    
    default IClientItemExtensions of(final cfz itemStack) {
        return IClientItemExtensions.DUMMY;
    }
    
    default boolean applyForgeHandTransform(final eij poseStack, final fiy player, final bft arm, final cfz itemInHand, final float partialTick, final float equipProcess, final float swingProcess) {
        return false;
    }
    
    default eov getFont(final cfz stack, final FontContext context) {
        return null;
    }
    
    default fjj getCustomRenderer() {
        return enn.N().ap().getBlockEntityRenderer();
    }
    
    public enum FontContext
    {
        public static final FontContext ITEM_COUNT;
        public static final FontContext TOOLTIP;
        public static final FontContext SELECTED_ITEM_NAME;
        
        public static FontContext valueOf(final String name) {
            return Enum.valueOf(FontContext.class, name);
        }
        
        static {
            FontContext.ITEM_COUNT = new FontContext("ITEM_COUNT", 0);
            FontContext.TOOLTIP = new FontContext("TOOLTIP", 1);
            FontContext.SELECTED_ITEM_NAME = new FontContext("SELECTED_ITEM_NAME", 2);
            FontContext.$VALUES = $values();
        }
    }
}
