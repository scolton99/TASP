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
                start--;
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
                    Command.sendItemNotFoundMessage(sender);
                    return;
                }

            } catch(NumberFormatException e) {
                Command.sendItemNotFoundMessage(sender);
                return;
            }
        }

        ItemStack is = new ItemStack(item.getMaterial(), 1, item.getDamage());

        List<Recipe> recipes = Bukkit.getRecipesFor(is);
        Message.Recipe.sendCountMessage(sender, recipes.size());

        if(recipes.isEmpty()) {
            Message.Recipe.Error.sendNoRecipesMessage(sender);
            return;
        }

        if(start > recipes.size()) {
            Message.Recipe.Error.sendNoOOBError(sender, recipes.size());
            return;
        }

        Recipe r = recipes.get(start);

        Inventory i;
        if(r instanceof FurnaceRecipe) {
            i = Bukkit.createInventory(p, InventoryType.FURNACE);

            p.openInventory(i);
        } else {
            i = p.openWorkbench(null, true).getTopInventory();
        }

        TASP.getOpenImmutableInventories().add(i);

        if(i instanceof CraftingInventory) {
            CraftingInventory ci = (CraftingInventory) i;
            if (r instanceof ShapelessRecipe) {
                ItemStack[] z = ((ShapelessRecipe)r).getIngredientList().toArray(new ItemStack[((ShapelessRecipe)r).getIngredientList().size()]);
                for(ItemStack gh : z) {
                    if(gh.getDurability() == (short)32767)
                        gh.setDurability((short)0);
                }
                z = Arrays.copyOf(z, 9);
                ci.setMatrix(z);
                ci.setResult(r.getResult());
            } else if (r instanceof ShapedRecipe) {
                ShapedRecipe sr = (ShapedRecipe) r;
                String[] s = sr.getShape();

                Map<Character, ItemStack> mci = sr.getIngredientMap();

                Character[][] chrs = new Character[3][3];

                for (int j = 0; j < s.length; j++) {
                    for (int k = 0; k < s[j].length(); k++) {
                        chrs[j][k] = s[j].toCharArray()[k];
                    }
                }

                for (int j = 0; j < 3; j++) {
                    if(chrs[j] == null) {
                        Character[] temp = {' ', ' ', ' '};
                        chrs[j] = temp;
                        continue;
                    }
                    for (int k = 0; k < 3; k++) {
                        if(chrs[j][k] == null)
                            chrs[j][k] = ' ';
                    }
                }

                ItemStack[] its = new ItemStack[9];

                for(int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        if(chrs[j][k] == ' ') {
                            its[(3*j) + k] = new ItemStack(Material.AIR);
                            continue;
                        }
                        its[(3*j) + k] = mci.get(chrs[j][k]);
                    }
                }

                for(ItemStack gh : its) {
                    if(gh.getDurability() == (short)32767)
                        gh.setDurability((short)0);
                }

                ci.setMatrix(its);
                ci.setResult(sr.getResult());
            }
        } else {
            if (i.getType() == InventoryType.FURNACE) {
                assert r instanceof FurnaceRecipe;
                FurnaceRecipe z = (FurnaceRecipe) r;

                ItemStack smelting = z.getInput();
                ItemStack result = z.getResult();

                if(smelting.getDurability() == 32767)
                    smelting.setDurability((short)0);

                if(result.getDurability() == 32767)
                    result.setDurability((short)0);

                i.setItem(0, smelting);
                i.setItem(2, result);
            }
        }
    }

}
