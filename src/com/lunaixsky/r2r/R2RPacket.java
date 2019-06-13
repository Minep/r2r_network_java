package com.lunaixsky.r2r;

import java.net.SocketException;

import com.lunaixsky.common.TransportHeader;

public class R2RPacket {
	static final byte INFOTAG_PKTTYPE_MASK = 0x20;
	static final byte INFOTAG_PRORITY_MASK = 0x18;
	static final byte INFOTAG_CHLTYPE_MASK = 0x07;
	
	static final byte ENCTAG_METHOD_MASK = 0x02;
	static final byte ENCTAG_KEYUSE_MASK = 0x01;

	static final byte PKTTYPE_FORWARD = 0x00;
	static final byte PKTTYPE_INCOMING = 0X01;
	
	static final byte PRORITY_LOW = 0x00;;
	static final byte PRORITY_GENERAL = 0x01;
	static final byte PRORITY_EMERGENCY = 0x02;

	static final byte CHLTYPE_BOARDCAST = 0x00;
	static final byte CHLTYPE_VERIF = 0x01;
	static final byte CHLTYPE_AUTH = 0x02;
	static final byte CHLTYPE_GENERAL = 0x03;
	static final byte CHLTYPE_CONFIRM = 0x04;

	static final byte ENCTAG_METHOD_AES = 0x00;
	static final byte ENCTAG_METHOD_DES = 0x01;
	static final byte ENCTAG_KEY_USED = 0x00;
	static final byte ENCTAG_KEY_UNUSED = 0x01;

	static final int NEED_NEGOTIATION = 0xff;
	static final byte NEGOTIATED = 0x00;

	static final byte SESSION_KEY_LEN = 28;

	static final int PKTTYPE_BITS = 5;
	static final int PRORITY_BITS = 3;
	static final int CHLTYPE_BITS = 0;
	
	public byte setTagComponent(byte tag, byte val,byte mask, int shift_bits)
	{
		return (byte)((tag & ~mask) | (val << shift_bits));
	}
	
	public byte getTagComponent(byte tag, byte mask, int shift_bits)
	{
		return (byte)((tag & mask) >> shift_bits);
	}
	
	public TransportHeader CreateTRheader(byte[] destMAC, int destAddr, int srcAddr, byte InfoTag) throws SocketException
	{
		TransportHeader tr = new TransportHeader();
		tr.DestinationMAC = destMAC;
		tr.SourceMAC = Utils.GetMACAddress();
		tr.DestinationAddr = destAddr;
		tr.SourceAddr = srcAddr;
		tr.InfoTag = InfoTag;
		return tr;
	}
	
}
