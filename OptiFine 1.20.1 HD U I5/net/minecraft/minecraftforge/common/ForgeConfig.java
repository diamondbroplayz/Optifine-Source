// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraftforge.common;

public class ForgeConfig
{
    public static Server SERVER;
    public static Common COMMON;
    public static Client CLIENT;
    
    static {
        ForgeConfig.SERVER = new Server();
        ForgeConfig.COMMON = new Common();
        ForgeConfig.CLIENT = new Client();
    }
    
    public static class Server
    {
    }
    
    public static class Common
    {
        public ForgeConfigSpec.BooleanValue indexVanillaPackCachesOnThread;
    }
    
    public static class Client
    {
    }
}
