package keqing.gtqtcore.client.objmodels;

import keqing.gtqtcore.api.obj.AdvancedModelLoader;
import keqing.gtqtcore.api.obj.IModelCustom;
import net.minecraft.util.ResourceLocation;

public class ObjModels {
    //Dr新增带MTL文件的加载方式，需要传递模型的地址，mtl文件要与模型同名  这种方式可以在渲染中额外绑定图片 效果我测试会叠加一起
    public static final IModelCustom Dna_Model = AdvancedModelLoader.loadModelWithMtl(new ResourceLocation("gtqtcore", "models/obj/mdna.obj"));
    //常规不带mtl文件的加载方式，这种方式也可以在渲染中绑定图片，如果图片在编辑器中能和模型对上，mc自己的绑定方法也会完美绑定
    public static final IModelCustom Dna_Model1 = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtcore", "models/obj/mdna.obj"));
    public static final ResourceLocation test_pic = new ResourceLocation("gtqtcore", "models/obj/test.png");


    //Dr新增带MTL文件的加载方式，需要传递模型的地址，mtl文件要与模型同名  这种方式可以在渲染中额外绑定图片 效果我测试会叠加一起
    public static final IModelCustom Tree_Model = AdvancedModelLoader.loadModelWithMtl(new ResourceLocation("gtqtcore", "models/obj/tree.obj"));
    //常规不带mtl文件的加载方式，这种方式也可以在渲染中绑定图片，如果图片在编辑器中能和模型对上，mc自己的绑定方法也会完美绑定
    public static final IModelCustom Tree_Model1 = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtcore", "models/obj/tree.obj"));
    public static final ResourceLocation Tree_pic = new ResourceLocation("gtqtcore", "models/obj/tree.png");

    public static final IModelCustom SpaceLJ = AdvancedModelLoader.loadModel(new ResourceLocation("gtqtcore", "models/obj/spacelj.obj"));
    public static final ResourceLocation SpaceLJ_pic = new ResourceLocation("gtqtcore", "models/obj/spacelj.png");



}
