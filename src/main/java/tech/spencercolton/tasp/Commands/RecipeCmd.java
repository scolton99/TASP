package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.material.MaterialData;
import tech.spencercolton.tasp.Enums.IDs;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Message;

import java.util.*;

/**
 * @author Spencer Colton
 */
public class RecipeCmd extends TASPCommand {

    @Getter
    private final String syntax = "/recipe <item> [number]";

    @Getter
    public static final String name = "recipe";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.recipe";

    @Override
    public void execute(CommandSender sender, String[] argsg) {
        assert sender instanceof Player;

        List<String> args = Command.processQuotedArguments(argsg);

        if(args.size() != 1 && args.size() != 2) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player) sender;

        String name = args.get(0);
        IDs item = IDs.getByName(name);

        int start = 0;

        if(args.size() == 2) {
            try {
                start = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                Command.sendSyntaxError(sender, this);
                return;
            }
        }

        if(item == null) {
            Integer damage = null, id = null;
            String idDamage[] = args.get(0).split(":");
            try {
                if (idDamage.length == 2)
                    damage = Integer.parseInt(idDamage[1]);
                if (idDamage.length >= 1)
                    id = Integer.parseInt(idDamage[0]);

                if(id == null) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }

                if(damage == null)
                    damage = 0;

                item = IDs.getByIdDamage(id, damage);
                if(item == null) {
                    Message.Recipe.Error.sendItemNotFoundMessage(sender);
                    return;
                }

            } catch(NumberFormatException e) {
                Command.sendSyntaxError(sender, this);
                return;
            }
        }

        ItemStack is = new ItemStack(item.getMaterial(), 1, item.getDamage());

        List<Recipe> recipes = Bukkit.getRecipesFor(is);

        if(recipes.isEmpty()) {
            Message.Recipe.Error.sendNoRecipesMessage(sender);
            return;
        }

        if(start > recipes.size()) {
            // TODO Make this a custom error
            Command.sendSyntaxError(sender, this);
            return;
        }

        start--;

        Recipe r = recipes.get(start);

        Inventory i;
        if(r instanceof FurnaceRecipe) {
            i = Bukkit.createInventory(p, InventoryType.FURNACE);
        } else {
            i = Bukkit.createInventory(p, InventoryType.CRAFTING);
        }

        TASP.getOpenImmutableInventories().add(i);
        //TODO Finish this
        if(i.getType() == InventoryType.FURNACE) {
            assert r instanceof FurnaceRecipe;
            FurnaceRecipe z = (FurnaceRecipe) r;

            ItemStack smelting = z.getInput();
            ItemStack result = z.getResult();

            smelting.setAmount(1);

            i.setItem(0, smelting);
            i.setItem(2, result);
        } else if(i.getType() == InventoryType.CRAFTING) {
            CraftingInventory ci = (CraftingInventory) i;
            if (r instanceof ShapelessRecipe) {
                List<ItemStack> z = ((ShapelessRecipe) r).getIngredientList();
                ci.setMatrix(z.toArray(new ItemStack[z.size()]));
                ci.setResult(r.getResult());
            } else if (r instanceof ShapedRecipe) {
                ShapedRecipe sr = (ShapedRecipe) r;
                String[] shape = sr.getShape();
                List<ItemStack> finMatrix = new ArrayList<>();
                Map<Character, ItemStack> mci = sr.getIngredientMap();
                List<List<Character>> chars = new ArrayList<>();
                for (String s : shape) {
                    List<Character> cx = new ArrayList<>();
                    for (char c : s.toCharArray()) {
                        cx.add(c);
                    }
                    chars.add(cx);
                }

                for (List<Character> ch : chars) {
                    for (Character h : ch) {
                        finMatrix.add(mci.get(h));
                    }
                }
                Bukkit.getLogger().info(finMatrix.toString());
                ItemStack[] fin = finMatrix.toArray(new ItemStack[finMatrix.size()]);
                Bukkit.getLogger().info(Integer.toString(fin.length));
                ci.setMatrix(Arrays.copyOf(fin, 9));
            }
        }

        p.openInventory(i);
    }

}
