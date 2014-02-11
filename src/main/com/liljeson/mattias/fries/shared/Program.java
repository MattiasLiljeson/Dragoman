package com.liljeson.mattias.fries.shared;

import com.liljeson.mattias.fries.utils.DeluxeArray;

public class Program {
	public String m_name = "notSet";
	public DeluxeArray<Block> m_blocks = new DeluxeArray<>();

	public Block getMain() {
		return getBlock(0);
	}

	public Block getBlock(int p_blockId) {
		for (Block block : m_blocks.arr) {
			if (block.m_blockId == p_blockId) {
				return block;
			}
		}
		return null;
	}

	public void prepForUse() {
		for (Block b : m_blocks.arr) {
			b.prepForUse();
		}
	}
}
