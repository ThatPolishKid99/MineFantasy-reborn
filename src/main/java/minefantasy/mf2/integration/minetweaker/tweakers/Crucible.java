package minefantasy.mf2.integration.minetweaker.tweakers;

import java.util.ArrayList;
import java.util.List;

import minefantasy.mf2.api.refine.Alloy;
import minefantasy.mf2.api.refine.AlloyRecipes;
import minefantasy.mf2.integration.minetweaker.TweakedAlloyRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Crucible")
public class Crucible {

	@ZenMethod
	public static void addAlloy(IItemStack out, int level, int dupe, IIngredient[] ingred) {
		MineTweakerAPI.apply(new AlloyAction(out, level, ingred, dupe));
	}

	private static class AlloyAction extends OneWayAction {

		private IItemStack out;
		private int level;
		private List<IIngredient> ingreds;
		private int dupe;
		Alloy a;

		public AlloyAction(IItemStack out, int level, IIngredient[] ingreds, int dupe) {
			this.out = out;
			this.level = level;
			this.ingreds = new ArrayList<IIngredient>();
			this.dupe = dupe;
			for (IIngredient i : ingreds) {
				this.ingreds.add(i);
			}
			a = new TweakedAlloyRecipe(out, level, this.ingreds);
		}

		@Override
		public void apply() {
			AlloyRecipes.addAlloy(a);
		}

		@Override
		public String describe() {
			return "Adding Custom Alloy";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

	}

}