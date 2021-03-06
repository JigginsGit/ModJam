package easyinventories;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import easyinventories.blocks.BlockDistributor;
import easyinventories.blocks.BlockSorting;
import easyinventories.blocks.TileEntityBlockSorting;
import easyinventories.blocks.TileEntityDistributor;
import easyinventories.config.ConfigHandler;
import easyinventories.gui.GuiHandler;
import easyinventories.network.PacketHandler;
import easyinventories.proxies.CommonProxy;


@Mod(modid = "EasyInventories", name = "Easy Inventories")
@NetworkMod(channels = {ModInfo.CHANNELS}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class EasyInventories {

	public static Block sorting;
	public static Block distributor;
	
	@Instance(ModInfo.ID)
	public static EasyInventories easyInventories;

	@SidedProxy(clientSide = "easyinventories.proxies.ClientProxy", serverSide = "easyinventories.proxies.CommonProxy")
	public static CommonProxy proxy;


	public static void registerBlocks() {
		sorting = new BlockSorting(ModInfo.SORTING_ID);
		distributor = new BlockDistributor(ModInfo.DISTRIBUTOR_ID);
		
		GameRegistry.registerBlock(sorting, ModInfo.BLOCKSORTING_KEY);
		GameRegistry.registerBlock(distributor, ModInfo.DISTRIBUTOR_KEY);
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityBlockSorting.class, ModInfo.SORTING_TE);
		GameRegistry.registerTileEntity(TileEntityDistributor.class, ModInfo.DISTRIBUTOR_TE);
	}
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		registerBlocks();
	}
	@EventHandler
	public void init(FMLInitializationEvent event) {
		LanguageRegistry.addName(sorting, "Sorting Block");
		registerTileEntities();
		
		new GuiHandler();
	}
}
