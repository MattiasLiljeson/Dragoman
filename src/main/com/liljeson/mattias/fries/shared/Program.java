package com.liljeson.mattias.fries.shared;

import com.liljeson.mattias.fries.utils.DeluxeArray;

public class Program {
	public String m_name = "notSet";
	public DeluxeArray<Block> m_blocks = new DeluxeArray<>();

	public Block getMain() {
		return getBlock(-1);
	}

	public Block getBlock(int p_idx) {
		for (Block b : m_blocks.arr) {
			if (b.m_parent == p_idx) {
				return b;
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
