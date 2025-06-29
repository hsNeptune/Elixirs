package org.hsneptune.elixirs.materials;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.items.ElixirsItems;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ElixirsMaterial {

    public static RegistryEntry<ArmorMaterial> createMaterial(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable) {
        // Get the supported layers for the armor material
        List<ArmorMaterial.Layer> layers = List.of(
                // The ID of the texture layer, the suffix, and whether the layer is dyeable.
                // We can just pass the armor material ID as the texture layer ID.
                // We have no need for a suffix, so we'll pass an empty string.
                // We'll pass the dyeable boolean we received as the dyeable parameter.
                new ArmorMaterial.Layer(Identifier.of(Elixirs.MOD_ID, id), "", dyeable)
        );

        ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance);
        // Register the material within the ArmorMaterials registry.
        material = registerArmorMat(id, material);

        // The majority of the time, you'll want the RegistryEntry of the material - especially for the ArmorItem constructor.
        return RegistryEntry.of(material);
    }

    public static ArmorMaterial registerArmorMat(String path, ArmorMaterial material) {
        // For versions below 1.21, please replace ''Identifier.of'' with ''new Identifier''
        return Registry.register(Registries.ARMOR_MATERIAL, Identifier.of("elixirs", path), material);

    }
}
