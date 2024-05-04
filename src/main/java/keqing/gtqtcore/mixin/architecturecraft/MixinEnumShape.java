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
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * Allows accessing the protected static ID Map, which is used by Architecture Craft.
 *
 * @author IntegerLimit
 */
@Mixin(value = EnumShape.class, remap = false)
public interface MixinEnumShape {

    @Accessor(value = "idMap")
    static Map<Integer, EnumShape> getIDMap() {
        throw new NotImplementedException("ShapeAccessorMixin Failed to Apply!");
    }
}