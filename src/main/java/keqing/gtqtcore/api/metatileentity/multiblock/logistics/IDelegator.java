package keqing.gtqtcore.api.metatileentity.multiblock.logistics;

import net.minecraft.util.EnumFacing;

/**
 * Copyright (C) SymmetricDevs 2025
 * 由 MeowmelMuku 于 2025 修改。
 * 此文件遵循 GPL-3.0 许可证，详情请见项目根目录的 LICENSE 文件。
 */
public interface IDelegator {

    /**
     * @return the facing that the input facing in delegating
     */

    EnumFacing getDelegatingFacing(EnumFacing facing);
}