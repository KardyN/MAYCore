/*
 * MAYCore  Copyright (C) 2026 KardyN
 * The following code is licensed under GNU General Public License v3.0
 * Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.common.util;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationManager {
    public static ResourceLocation mayResLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("may", path);
    }

    public static ResourceLocation ieResLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("immersiveengineering", path);
    }

    public static ResourceLocation commonResLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

    public static ResourceLocation mcResLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }

    public static ResourceLocation neoResLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("neoforge", path);
    }
}
