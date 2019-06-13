package com.lunaixsky.common;

public class TransportHeader {
	public int DestinationAddr;
	public int SourceAddr;
	public byte InfoTag;
	public long AccessMarker[] = new long[2];
	public byte[] DestinationMAC = new byte[6];
	public byte[] SourceMAC = new byte[6];
	
}

