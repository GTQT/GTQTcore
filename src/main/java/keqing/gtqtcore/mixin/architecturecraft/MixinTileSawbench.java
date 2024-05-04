package keqing.gtqtcore.mixin.architecturecraft;

/*
 * Copyright (c) 2023 Nomifactory-CEu
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 2.1 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

import com.elytradev.architecture.common.shape.EnumShape;
import com.elytradev.architecture.common.shape.ShapePage;
import com.elytradev.architecture.common.tile.TileSawbench;
import keqing.gtqtcore.integration.architecturecraft.GTQTShapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author IntegerLimit
 */
@Mixin(value = TileSawbench.class, remap = false)
public class MixinTileSawbench {

    /**
     * Adds the new Shapes to the pages.
     */
    @Inject(method = "<clinit>",
            at = @At("TAIL"))
    private static void changePages(CallbackInfo ci) {
        TileSawbench.pages = new ShapePage[] {
                new ShapePage("Roofing",
                        EnumShape.ROOF_TILE,
                        EnumShape.ROOF_OUTER_CORNER,
                        EnumShape.ROOF_INNER_CORNER,
                        EnumShape.ROOF_RIDGE,
                        EnumShape.ROOF_SMART_RIDGE,
                        EnumShape.ROOF_VALLEY,
                        EnumShape.ROOF_SMART_VALLEY,
                        EnumShape.ROOF_OVERHANG,
                        EnumShape.ROOF_OVERHANG_OUTER_CORNER,
                        EnumShape.ROOF_OVERHANG_INNER_CORNER,
                        EnumShape.ROOF_OVERHANG_GABLE_LH,
                        EnumShape.ROOF_OVERHANG_GABLE_RH,
                        EnumShape.ROOF_OVERHANG_GABLE_END_LH,
                        EnumShape.ROOF_OVERHANG_GABLE_END_RH,
                        EnumShape.ROOF_OVERHANG_RIDGE,
                        EnumShape.ROOF_OVERHANG_VALLEY,
                        EnumShape.BEVELLED_OUTER_CORNER,
                        EnumShape.BEVELLED_INNER_CORNER),
                new ShapePage("Rounded",
                        EnumShape.CYLINDER,
                        EnumShape.CYLINDER_HALF,
                        EnumShape.CYLINDER_QUARTER,
                        EnumShape.CYLINDER_LARGE_QUARTER,
                        EnumShape.ANTICYLINDER_LARGE_QUARTER,
                        EnumShape.PILLAR,
                        EnumShape.POST,
                        EnumShape.POLE,
                        EnumShape.SPHERE_FULL,
                        EnumShape.SPHERE_HALF,
                        EnumShape.SPHERE_QUARTER,
                        EnumShape.SPHERE_EIGHTH,
                        EnumShape.SPHERE_EIGHTH_LARGE,
                        EnumShape.SPHERE_EIGHTH_LARGE_REV),
                new ShapePage("Classical",
                        EnumShape.PILLAR_BASE,
                        EnumShape.PILLAR,
                        EnumShape.DORIC_CAPITAL,
                        EnumShape.DORIC_TRIGLYPH,
                        EnumShape.DORIC_TRIGLYPH_CORNER,
                        EnumShape.DORIC_METOPE,
                        EnumShape.IONIC_CAPITAL,
                        EnumShape.CORINTHIAN_CAPITAL,
                        EnumShape.ARCHITRAVE,
                        EnumShape.ARCHITRAVE_CORNER,
                        EnumShape.CORNICE_LH,
                        EnumShape.CORNICE_RH,
                        EnumShape.CORNICE_END_LH,
                        EnumShape.CORNICE_END_RH,
                        EnumShape.CORNICE_RIDGE,
                        EnumShape.CORNICE_VALLEY,
                        EnumShape.CORNICE_BOTTOM),
                new ShapePage("Window",
                        EnumShape.WINDOW_FRAME,
                        EnumShape.WINDOW_CORNER,
                        EnumShape.WINDOW_MULLION),
                new ShapePage("Arches",
                        EnumShape.ARCH_D_1,
                        EnumShape.ARCH_D_2,
                        EnumShape.ARCH_D_3_A,
                        EnumShape.ARCH_D_3_B,
                        EnumShape.ARCH_D_3_C,
                        EnumShape.ARCH_D_4_A,
                        EnumShape.ARCH_D_4_B,
                        EnumShape.ARCH_D_4_C),
                new ShapePage("Railings",
                        EnumShape.BALUSTRADE_PLAIN,
                        EnumShape.BALUSTRADE_PLAIN_OUTER_CORNER,
                        EnumShape.BALUSTRADE_PLAIN_INNER_CORNER,
                        EnumShape.BALUSTRADE_PLAIN_WITH_NEWEL,
                        EnumShape.BALUSTRADE_PLAIN_END,
                        EnumShape.BANISTER_PLAIN_TOP,
                        EnumShape.BANISTER_PLAIN,
                        EnumShape.BANISTER_PLAIN_BOTTOM,
                        EnumShape.BANISTER_PLAIN_END,
                        EnumShape.BANISTER_PLAIN_INNER_CORNER,
                        EnumShape.BALUSTRADE_FANCY,
                        EnumShape.BALUSTRADE_FANCY_CORNER,
                        EnumShape.BALUSTRADE_FANCY_WITH_NEWEL,
                        EnumShape.BALUSTRADE_FANCY_NEWEL,
                        EnumShape.BANISTER_FANCY_TOP,
                        EnumShape.BANISTER_FANCY,
                        EnumShape.BANISTER_FANCY_BOTTOM,
                        EnumShape.BANISTER_FANCY_END,
                        EnumShape.BANISTER_FANCY_NEWEL_TALL),
                new ShapePage("Other",
                        EnumShape.CLADDING_SHEET,
                        EnumShape.SLAB,
                        EnumShape.STAIRS,
                        EnumShape.STAIRS_OUTER_CORNER,
                        EnumShape.STAIRS_INNER_CORNER,
                        GTQTShapes.SLOPE_TILE_A1,
                        GTQTShapes.SLOPE_TILE_A2,
                        GTQTShapes.SLOPE_TILE_B1,
                        GTQTShapes.SLOPE_TILE_B2,
                        GTQTShapes.SLOPE_TILE_B3,
                        GTQTShapes.SLOPE_TILE_C1,
                        GTQTShapes.SLOPE_TILE_C2,
                        GTQTShapes.SLOPE_TILE_C3,
                        GTQTShapes.SLOPE_TILE_C4) };
    }
}