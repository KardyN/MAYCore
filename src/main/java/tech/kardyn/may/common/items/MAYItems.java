/**
 *     MAYCore  Copyright (C) 2026  KardyN
 *     The following code is licensed under GNU General Public License v3.0
 *     Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;

public final class MAYItems {
    public static final DeferredRegister<Item> ITEMS;
    static{
        ITEMS = DeferredRegister.create(Registries.ITEM, "may");
    }
}
