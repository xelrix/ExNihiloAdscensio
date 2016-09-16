package exnihiloadscensio.items;

import java.util.ArrayList;
import java.util.List;

import exnihiloadscensio.ExNihiloAdscensio;
import exnihiloadscensio.blocks.ENBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemResource extends Item {

	public static final String STONES = "stones";
	public static final String PORCELAIN_CLAY = "porcelain_clay";
	public static final String SILKWORM = "silkworm";
	private static ArrayList<String> names = new ArrayList<String>();

	public ItemResource() {
		super();

		setUnlocalizedName("itemMaterial");
		setRegistryName("itemMaterial");
		setCreativeTab(ExNihiloAdscensio.tabExNihilo);
		setHasSubtypes(true);
		GameRegistry.<Item>register(this);

		names.add(0, STONES);
		names.add(1, PORCELAIN_CLAY);
		names.add(2, SILKWORM);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "." + names.get(stack.getItemDamage());
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
		for (int i = 0; i < names.size(); i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (stack.getItemDamage() == names.indexOf(SILKWORM)) {
			IBlockState state = world.getBlockState(pos);
			if (state != null && state.getBlock() != null && (state.getBlock() == Blocks.LEAVES || state.getBlock() == Blocks.LEAVES2)) {
				world.setBlockState(pos, ENBlocks.infestedLeaves.getDefaultState());
				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.PASS;
	}

	@SideOnly(Side.CLIENT)
	public void initModel()	{
		for (int i = 0 ; i < names.size() ; i ++) {
			String variant = "type="+names.get(i);
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation("exnihiloadscensio:itemMaterial", variant));
		}
	}

	public static ItemStack getResourceStack(String name) {
		return new ItemStack(ENItems.resources, 1, names.indexOf(name));
	}


}
