package com.lunaixsky.r2r;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Enumeration;

import com.lunaixsky.common.EncryptionHeader;
import com.lunaixsky.common.SessionHeader;
import com.lunaixsky.common.TransportHeader;

public class Utils {
	
	static final int SIZE_TR_HEADER = 37;
	static final int SIZE_SE_HEADER = 17;
	static final int SIZE_EC_HEADER = 5;
	
	public static int InetAddr2Int32(InetAddress ipaddr)
	{
		byte[] addr = ipaddr.getAddress();
		ByteBuffer bf = ByteBuffer.allocate(4);
		bf.clear();
		bf.order(ByteOrder.BIG_ENDIAN);
		bf.put(addr);	
		bf.rewind();
		return bf.getInt();
	}
	
	public static byte[] GetMACAddress() throws SocketException
	{
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		if(nets.hasMoreElements())
			return nets.nextElement().getHardwareAddress();
		return null;
	}
	
	/*
	 * Transportation Header to Byte Array
	 */
	public static byte[] TR2StructBin(TransportHeader tr)
	{
		ByteBuffer bbf = ByteBuffer.allocate(SIZE_TR_HEADER);
		bbf.clear();
		bbf.order(ByteOrder.BIG_ENDIAN);
		bbf.put(tr.InfoTag);
		bbf.putInt(tr.SourceAddr);
		bbf.putInt(tr.DestinationAddr);
		bbf.put(tr.SourceMAC);
		bbf.put(tr.DestinationMAC);
		bbf.putLong(tr.AccessMarker[0]);
		bbf.putLong(tr.AccessMarker[1]);
		return bbf.array();
	}
	
	/*
	 * Session Header to Byte array
	 */
	public static byte[] SE2StructBin(SessionHeader se)
	{
		ByteBuffer bbf = ByteBuffer.allocate(SIZE_SE_HEADER);
		bbf.clear();
		bbf.put(se.NeedNegotiation);
		bbf.put(se.SessionKey);
		return bbf.array();
	}
	
	/*
	 * Encryption Header to Byte array
	 */
	public static byte[] EC2StructBin(EncryptionHeader ec)
	{
		ByteBuffer bbf = ByteBuffer.allocate(SIZE_EC_HEADER);
		bbf.clear();
		bbf.put(ec.EncryptionMethod);
		bbf.putInt(ec.fnv32CheckSum);
		return bbf.array();
	}
	
	public static void printByte(byte[] data, int col_size)
	{
		for(int i=0;i<data.length;i++)
		{
			System.out.printf("0x%02X ", data[i]);
			if((i+1)%col_size == 0)
			{
				System.out.println();
			}
		}
	}
}
