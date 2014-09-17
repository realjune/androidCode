package com.android.study.abc.examples.memory;

import com.android.internal.util.MemInfoReader;

public class MemoryUtils {


	MemInfoReader mMemInfoReader = new MemInfoReader();
	public long getTotalSize(){
		return mMemInfoReader.getTotalSize();
	}
	public long getFreeSize(){
		return mMemInfoReader.getFreeSize();
	}
	public long getCachedSize(){
		return mMemInfoReader.getCachedSize();
	}
}
