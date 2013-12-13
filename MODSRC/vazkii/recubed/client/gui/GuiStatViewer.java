/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 5:47:04 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.client.renders.PieChartRender;
import vazkii.recubed.client.renders.PieChartRender.Entry;

public class GuiStatViewer extends GuiCategoryList {

	Object category;
	Entry hoveredEntry;
	
	@Override
	public void initGui() {
		super.initGui();
		
		buttonList.clear();
		buttonList.add(new GuiButton(0, x + 310, y + 170, 80, 20, StatCollector.translateToLocal("recubed.misc.back")));
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		hoveredEntry = null;
		PieChartRender pie = category instanceof Category ? PieChartRender.fromCategory((Category) category) : PieChartRender.fromPlayerData((PlayerCategoryData) category);
		if(pie == null)
			drawCenteredString(fontRenderer, StatCollector.translateToLocal("recubed.no_data"), x + 250, y + 95, 0xFF7777);
		else hoveredEntry = pie.renderChart(80, x + 250, y + 100, par1, par2);
		
		String displayString = StatCollector.translateToLocal(ReCubedAPI.clientData.get(ReCubedAPI.categories.get(selectedCategory)).name);
		if(category instanceof PlayerCategoryData)
			displayString = displayString + " - " + EnumChatFormatting.AQUA + ((PlayerCategoryData) category).name;
		drawCenteredString(fontRenderer, displayString, x + 250, y + 5, 0xFFFFFF);
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton.id == 0) {
			if(category instanceof PlayerCategoryData)
				category = ReCubedAPI.clientData.get(ReCubedAPI.categories.get(selectedCategory));
			else mc.displayGuiScreen(new GuiReCubedMenu()); 
		}
	}
	
	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		if(category instanceof Category && par3 == 0 && hoveredEntry != null && shouldVisitStats()) {
			Category category = (Category) this.category;
			this.category = category.playerData.get(hoveredEntry.name);
		}
		
		super.mouseClicked(par1, par2, par3);
	}
	
	boolean shouldVisitStats() {
		return true;
	}
	
	@Override
	public void selectCategory(int category) {
		super.selectCategory(category);
		this.category = ReCubedAPI.clientData.get(ReCubedAPI.categories.get(category));
	}
	
}
