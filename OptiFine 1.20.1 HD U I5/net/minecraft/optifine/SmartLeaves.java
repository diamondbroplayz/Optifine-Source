// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine;

import net.optifine.util.RandomUtils;
import net.optifine.model.ModelUtils;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class SmartLeaves
{
    private static fwr modelLeavesCullAcacia;
    private static fwr modelLeavesCullBirch;
    private static fwr modelLeavesCullDarkOak;
    private static fwr modelLeavesCullJungle;
    private static fwr modelLeavesCullOak;
    private static fwr modelLeavesCullSpruce;
    private static List generalQuadsCullAcacia;
    private static List generalQuadsCullBirch;
    private static List generalQuadsCullDarkOak;
    private static List generalQuadsCullJungle;
    private static List generalQuadsCullOak;
    private static List generalQuadsCullSpruce;
    private static fwr modelLeavesDoubleAcacia;
    private static fwr modelLeavesDoubleBirch;
    private static fwr modelLeavesDoubleDarkOak;
    private static fwr modelLeavesDoubleJungle;
    private static fwr modelLeavesDoubleOak;
    private static fwr modelLeavesDoubleSpruce;
    private static final apf RANDOM;
    
    public static fwr getLeavesModel(final fwr model, final dcb stateIn) {
        if (!Config.isTreesSmart()) {
            return model;
        }
        final List generalQuads = model.a(stateIn, (ha)null, SmartLeaves.RANDOM);
        if (generalQuads == SmartLeaves.generalQuadsCullAcacia) {
            return SmartLeaves.modelLeavesDoubleAcacia;
        }
        if (generalQuads == SmartLeaves.generalQuadsCullBirch) {
            return SmartLeaves.modelLeavesDoubleBirch;
        }
        if (generalQuads == SmartLeaves.generalQuadsCullDarkOak) {
            return SmartLeaves.modelLeavesDoubleDarkOak;
        }
        if (generalQuads == SmartLeaves.generalQuadsCullJungle) {
            return SmartLeaves.modelLeavesDoubleJungle;
        }
        if (generalQuads == SmartLeaves.generalQuadsCullOak) {
            return SmartLeaves.modelLeavesDoubleOak;
        }
        if (generalQuads == SmartLeaves.generalQuadsCullSpruce) {
            return SmartLeaves.modelLeavesDoubleSpruce;
        }
        return model;
    }
    
    public static boolean isSameLeaves(final dcb state1, final dcb state2) {
        if (state1 == state2) {
            return true;
        }
        final cpn block1 = state1.b();
        final cpn block2 = state2.b();
        return block1 == block2;
    }
    
    public static void updateLeavesModels() {
        final List updatedTypes = new ArrayList();
        SmartLeaves.modelLeavesCullAcacia = getModelCull("acacia", updatedTypes);
        SmartLeaves.modelLeavesCullBirch = getModelCull("birch", updatedTypes);
        SmartLeaves.modelLeavesCullDarkOak = getModelCull("dark_oak", updatedTypes);
        SmartLeaves.modelLeavesCullJungle = getModelCull("jungle", updatedTypes);
        SmartLeaves.modelLeavesCullOak = getModelCull("oak", updatedTypes);
        SmartLeaves.modelLeavesCullSpruce = getModelCull("spruce", updatedTypes);
        SmartLeaves.generalQuadsCullAcacia = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullAcacia);
        SmartLeaves.generalQuadsCullBirch = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullBirch);
        SmartLeaves.generalQuadsCullDarkOak = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullDarkOak);
        SmartLeaves.generalQuadsCullJungle = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullJungle);
        SmartLeaves.generalQuadsCullOak = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullOak);
        SmartLeaves.generalQuadsCullSpruce = getGeneralQuadsSafe(SmartLeaves.modelLeavesCullSpruce);
        SmartLeaves.modelLeavesDoubleAcacia = getModelDoubleFace(SmartLeaves.modelLeavesCullAcacia);
        SmartLeaves.modelLeavesDoubleBirch = getModelDoubleFace(SmartLeaves.modelLeavesCullBirch);
        SmartLeaves.modelLeavesDoubleDarkOak = getModelDoubleFace(SmartLeaves.modelLeavesCullDarkOak);
        SmartLeaves.modelLeavesDoubleJungle = getModelDoubleFace(SmartLeaves.modelLeavesCullJungle);
        SmartLeaves.modelLeavesDoubleOak = getModelDoubleFace(SmartLeaves.modelLeavesCullOak);
        SmartLeaves.modelLeavesDoubleSpruce = getModelDoubleFace(SmartLeaves.modelLeavesCullSpruce);
        if (updatedTypes.size() > 0) {
            Config.dbg(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, Config.arrayToString(updatedTypes.toArray())));
        }
    }
    
    private static List getGeneralQuadsSafe(final fwr model) {
        if (model == null) {
            return null;
        }
        return model.a((dcb)null, (ha)null, SmartLeaves.RANDOM);
    }
    
    static fwr getModelCull(final String type, final List updatedTypes) {
        final fwx modelManager = Config.getModelManager();
        if (modelManager == null) {
            return null;
        }
        final acq locState = new acq(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, type));
        if (!Config.isFromDefaultResourcePack(locState)) {
            return null;
        }
        final acq locModel = new acq(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, type));
        if (!Config.isFromDefaultResourcePack(locModel)) {
            return null;
        }
        final fwy mrl = fwy.c(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, type), "normal");
        final fwr model = modelManager.a(mrl);
        if (model == null || model == modelManager.a()) {
            return null;
        }
        final List listGeneral = model.a((dcb)null, (ha)null, SmartLeaves.RANDOM);
        if (listGeneral.size() == 0) {
            return model;
        }
        if (listGeneral.size() != 6) {
            return null;
        }
        for (final fkr quad : listGeneral) {
            final List listFace = model.a((dcb)null, quad.e(), SmartLeaves.RANDOM);
            if (listFace.size() > 0) {
                return null;
            }
            listFace.add(quad);
        }
        listGeneral.clear();
        updatedTypes.add(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, type));
        return model;
    }
    
    private static fwr getModelDoubleFace(final fwr model) {
        if (model == null) {
            return null;
        }
        if (model.a((dcb)null, (ha)null, SmartLeaves.RANDOM).size() > 0) {
            Config.warn(invokedynamic(makeConcatWithConstants:(ILfwr;)Ljava/lang/String;, model.a((dcb)null, (ha)null, SmartLeaves.RANDOM).size(), model));
            return model;
        }
        final ha[] faces = ha.p;
        for (int i = 0; i < faces.length; ++i) {
            final ha face = faces[i];
            final List<fkr> quads = (List<fkr>)model.a((dcb)null, face, SmartLeaves.RANDOM);
            if (quads.size() != 1) {
                Config.warn(invokedynamic(makeConcatWithConstants:(Lha;ILfwr;)Ljava/lang/String;, face, quads.size(), model));
                return model;
            }
        }
        final fwr model2 = ModelUtils.duplicateModel(model);
        final List[] faceQuads = new List[faces.length];
        for (int j = 0; j < faces.length; ++j) {
            final ha face2 = faces[j];
            final List<fkr> quads2 = (List<fkr>)model2.a((dcb)null, face2, SmartLeaves.RANDOM);
            final fkr quad = quads2.get(0);
            final fkr quad2 = new fkr((int[])quad.b().clone(), quad.d(), quad.e(), quad.a(), quad.f());
            final int[] vd = quad2.b();
            final int[] vd2 = vd.clone();
            final int step = vd.length / 4;
            System.arraycopy(vd, 0 * step, vd2, 3 * step, step);
            System.arraycopy(vd, 1 * step, vd2, 2 * step, step);
            System.arraycopy(vd, 2 * step, vd2, 1 * step, step);
            System.arraycopy(vd, 3 * step, vd2, 0 * step, step);
            System.arraycopy(vd2, 0, vd, 0, vd2.length);
            quads2.add(quad2);
        }
        return model2;
    }
    
    static {
        SmartLeaves.modelLeavesCullAcacia = null;
        SmartLeaves.modelLeavesCullBirch = null;
        SmartLeaves.modelLeavesCullDarkOak = null;
        SmartLeaves.modelLeavesCullJungle = null;
        SmartLeaves.modelLeavesCullOak = null;
        SmartLeaves.modelLeavesCullSpruce = null;
        SmartLeaves.generalQuadsCullAcacia = null;
        SmartLeaves.generalQuadsCullBirch = null;
        SmartLeaves.generalQuadsCullDarkOak = null;
        SmartLeaves.generalQuadsCullJungle = null;
        SmartLeaves.generalQuadsCullOak = null;
        SmartLeaves.generalQuadsCullSpruce = null;
        SmartLeaves.modelLeavesDoubleAcacia = null;
        SmartLeaves.modelLeavesDoubleBirch = null;
        SmartLeaves.modelLeavesDoubleDarkOak = null;
        SmartLeaves.modelLeavesDoubleJungle = null;
        SmartLeaves.modelLeavesDoubleOak = null;
        SmartLeaves.modelLeavesDoubleSpruce = null;
        RANDOM = RandomUtils.makeThreadSafeRandomSource(0);
    }
}
